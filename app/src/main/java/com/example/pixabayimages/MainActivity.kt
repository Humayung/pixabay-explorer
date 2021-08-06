package com.example.pixabayimages

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.pixabayimages.adapter.ViewPagerAdapter
import com.example.pixabayimages.fragment.FragmentAccount
import com.example.pixabayimages.fragment.FragmentExplore
import com.example.pixabayimages.fragment.FragmentFavorite
import com.example.pixabayimages.networking.ApiReq
import com.example.pixabayimages.networking.Status
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val apiReq: ApiReq by inject()
    private val fragmentAccount: FragmentAccount by inject()
    private val fragmentExplore: FragmentExplore by inject()
    private val favoriteFragment: FragmentFavorite by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager.adapter = loadFragments()
        pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                when(position){
                    0 -> bottomNavigationView.selectedItemId = R.id.explore
                    1 -> bottomNavigationView.selectedItemId = R.id.favorite
                    2 -> bottomNavigationView.selectedItemId = R.id.account
                }
                super.onPageSelected(position)
            }
        })

        bottomNavigationView.setOnNavigationItemSelectedListener { it ->
            when (it.itemId){
                R.id.explore -> pager.setCurrentItem(0, true)
                R.id.favorite -> pager.setCurrentItem(1, true)
                R.id.account -> pager.setCurrentItem(2, true)
            }
            true
        }

        apiReq.getImages(1, "beach").observe(this, {
            if (it.status == Status.SUCCESS){
                Log.d(TAG, "Success!")
                Log.d(TAG, it.response?.hits!![0].largeImageURL)
            }

        })




    }

    private fun loadFragments(): ViewPagerAdapter {
        val viewPagerAdapter = ViewPagerAdapter(this)
        viewPagerAdapter.replaceAll(
                arrayListOf(fragmentExplore, favoriteFragment, fragmentAccount)
        )
        return viewPagerAdapter
    }

//    fun reload(v: View?){
//        apiReq.ping((Math.random()*100).toInt()).observe(this, {
//            status.text = it.status.toString()
//            if (it.status == Status.SUCCESS) {
//                status.text = ""
//                Log.d(TAG, it.response?.hits.toString())
//                val url = it.response?.hits?.get(0)?.webformatURL
//                Glide.with(context).load(url).centerCrop()
//                        .into(image)
//            }
//        })
//    }
}