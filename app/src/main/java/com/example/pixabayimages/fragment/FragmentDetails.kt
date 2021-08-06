package com.example.pixabayimages.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.DownloadManager
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.database.Cursor
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.example.pixabayimages.MemoryDb
import com.example.pixabayimages.R
import com.example.pixabayimages.TAG
import com.example.pixabayimages.model.PixabayResponse
import com.example.pixabayimages.model.UserData
import com.example.pixabayimages.persistence.Preferences
import com.example.pixabayimages.toMb
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import java.io.File



class FragmentDetails : BottomSheetDialogFragment() {
    private val memoryDb: MemoryDb by inject()
    private val preferences: Preferences by inject()
    var a : Int = 0
    var onDismissed: (data: String) -> Unit = { _ -> }
    var photo : PixabayResponse.Photo? = null
    var preview : Drawable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    fun setData(photo : PixabayResponse.Photo, preview : Drawable){
        this.photo = photo
        this.preview = preview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setDetails()
        setAs.setOnClickListener {
            setAs()
        }
        favorite.setOnClickListener {
            setLikes()
        }
        super.onViewCreated(view, savedInstanceState)
    }
    
    private fun setDetails(){
        photo?.let {
            val res = getString(R.string.resolution_val, it.imageWidth, it.imageHeight)
            val fileSize = toMb(it.imageSize.toInt())
            val formattedFileSize = getString(R.string.size_val, fileSize)

            userName.text = it.user
            likes.text = it.likes
            tags.text = it.tags
            size.text = formattedFileSize
            resolution.text = res
            views.text = it.views
            downloads.text = it.downloads
            comments.text = it.comments

            Glide.with(requireContext())
                .load(it.userImageURL)
                .placeholder(R.drawable.ic_person_foreground)
                .into(pfp)
            Glide.with(requireContext())
                .load(it.largeImageURL)
                .placeholder(preview)
                .into(largeImage)
        }
    }


    private fun setLikes(){
        val currentUser = memoryDb.currentUser.value
        val favoriteList = currentUser?.favoriteList
        favoriteList?.add(photo!!)
        memoryDb.currentUser.value = currentUser
        updateUserData(currentUser)
    }

    private fun updateUserData(userData : UserData?) : Boolean {
        val userList = memoryDb.userList.value
        val length = userList?.size?.minus(1)
        (0..length!!).forEach {
            val data = userList[it]
            if(data.username == userData?.username){
                userList[it] = userData
                memoryDb.userList.value = userList
                return true
            }
        }

        return false
    }

    private fun setAs(){
        val url = photo!!.largeImageURL
        val context = requireContext()
        val rm = Glide.with(this).downloadOnly().load(url).submit()
        GlobalScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT) {
            val defFile: Deferred<File> = async(Dispatchers.IO) { rm.get() }
            val file = defFile.await()
            val intent = Intent(Intent.ACTION_ATTACH_DATA)
            val uri = FileProvider.getUriForFile(
                context,
                context.packageName + ".provider",
                file
            )
            intent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.setDataAndType(uri, "image/jpeg")
            intent.putExtra("mimeType", "image/jpeg")
            startActivity(Intent.createChooser(intent, "Set as:"))

        }
    }


    // TODO: fix this
    private fun beginDownload() {
        val url = "http://speedtest.ftp.otenet.gr/files/test10Mb.db"
        var fileName = url.substring(url.lastIndexOf('/') + 1)
        fileName = fileName.substring(0, 1).toUpperCase() + fileName.substring(1)
//        val file: File = Util.DocumentFile(fileName, context)
        val request = DownloadManager.Request(Uri.parse(url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE) // Visibility of the download Notification
//            .setDestinationUri(Uri.fromFile(file)) // Uri of the destination file
            .setTitle(fileName) // Title of the Download Notification
            .setDescription("Downloading") // Description of the Download Notification
            .setRequiresCharging(false) // Set if charging is required to begin the download
            .setAllowedOverMetered(true) // Set if download is allowed on Mobile network
            .setAllowedOverRoaming(true) // Set if download is allowed on roaming network
        val downloadManager = activity?. getSystemService(DOWNLOAD_SERVICE) as DownloadManager?
        val downloadID =
            downloadManager!!.enqueue(request) // enqueue puts the download request in the queue.

        // using query method
        var finishDownload = false
        var progress: Int
        while (!finishDownload) {
            val cursor: Cursor =
                downloadManager.query(DownloadManager.Query().setFilterById(downloadID))
            if (cursor.moveToFirst()) {
                val status: Int =
                    cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                when (status) {
                    DownloadManager.STATUS_FAILED -> {
                        finishDownload = true
                    }
                    DownloadManager.STATUS_PAUSED -> {
                    }
                    DownloadManager.STATUS_PENDING -> {
                    }
                    DownloadManager.STATUS_RUNNING -> {
                        val total: Long =
                            cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))
                        if (total >= 0) {
                            val downloaded: Long =
                                cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                            progress = (downloaded * 100L / total).toInt()
                            // if you use downloadmanger in async task, here you can use like this to display progress.
                            // Don't forget to do the division in long to get more digits rather than double.
                            //  publishProgress((int) ((downloaded * 100L) / total));
                        }
                    }
                    DownloadManager.STATUS_SUCCESSFUL -> {
                        progress = 100
                        // if you use aysnc task
                        // publishProgress(100);
                        finishDownload = true
                        Toast.makeText(activity, "Download Completed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }


    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
    }


}