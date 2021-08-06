package com.example.pixabayimages.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.pixabayimages.R
import com.example.pixabayimages.TAG
import com.example.pixabayimages.model.PixabayResponse
import com.example.pixabayimages.persistence.Preferences
import kotlinx.android.synthetic.main.item_explore.view.*
import okhttp3.ResponseBody
import org.koin.android.ext.android.inject
import org.koin.core.KoinComponent
import org.koin.core.inject
import kotlin.math.log

class FavouriteAdapter(private var data : ArrayList<PixabayResponse.Photo?>, var context: AppCompatActivity):RecyclerView.Adapter<FavouriteAdapter.Holder>(),KoinComponent {
    private val maxPlaceHolder = 8
    var onItemClick: (data: PixabayResponse.Photo, position: Int) -> Unit = { _, _ -> }
    var onLongClick: (data: PixabayResponse.Photo, position: Int) -> Unit = { _, _ -> }
    var lastPosition: Int = -1
    private val preferences: Preferences by inject()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteAdapter.Holder {
        return Holder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.item_explore, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun getData(): ArrayList<PixabayResponse.Photo?> {
        return data
    }

    fun replace(list: ArrayList<PixabayResponse.Photo?>) {
        this.data = list
        notifyDataSetChanged()
    }

    fun clear() {
        this.data.clear()
        notifyDataSetChanged()
    }

//    fun setLoading() {
//        (1..maxPlaceHolder).forEach { _ ->
//            this.data.add(PixabayResponse.Photo())
//        }
//        notifyDataSetChanged()
//    }
//
//    fun removeLoading() {
//        val loadingCount = this.data.count { it.webformatURL == "" }
//        (1..loadingCount).forEach { _ ->
//            val lastIndex = this.data.size - 1
//            if (this.data[lastIndex].webformatURL == "") {
//                this.data.removeAt(lastIndex)
//            }
//        }
//        notifyDataSetChanged()
//    }

//    fun addAll(list: ArrayList<PixabayResponse.Photo>) {
//        this.data.addAll(list)
//        notifyDataSetChanged()
//    }

    override fun onViewDetachedFromWindow(holder: FavouriteAdapter.Holder) {
        super.onViewDetachedFromWindow(holder)
        holder.itemView.clearAnimation()
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
//        if(position == 0){
//            Log.d(TAG, "Hi im first item from the explore list! ")
//            Log.d(TAG, "This is my imageURL: ${data[position].webformatURL}")
//        }


        val drw = CircularProgressDrawable(context)
        drw.strokeWidth = 5f
        drw.centerRadius = 30f
        drw.start()
        holder.itemView.image.setImageDrawable(null)
        Glide.with(context)
            .load(
                data[position]
                    ?.webformatURL

            )
            .placeholder(drw)
            .centerCrop()
            .into(holder.itemView.image)

//        holder.itemView.setOnClickListener {
//            onItemClick(data[position], position)
//        }

//        holder.itemView.setOnLongClickListener {
//            onLongClick(data[position], position)
//            true
//        }

        val animation: Animation = AnimationUtils.loadAnimation(context, if (position > lastPosition) R.anim.up_from_bottom else R.anim.down_from_top)
        holder.itemView.startAnimation(animation)
        lastPosition = position

    }
    fun setData(data : ArrayList<PixabayResponse.Photo?>){
        this.data = data
    }



    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }
}