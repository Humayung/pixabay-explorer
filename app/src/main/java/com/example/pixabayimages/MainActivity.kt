package com.example.pixabayimages

import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.pixabayimages.adapter.ViewPagerAdapter
import com.example.pixabayimages.fragment.FragmentAccount
import com.example.pixabayimages.fragment.FragmentExplore
import com.example.pixabayimages.fragment.FragmentFavorite
import com.example.pixabayimages.networking.ApiReq
import com.example.pixabayimages.persistence.Preferences
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val apiReq: ApiReq by inject()
    private val fragmentAccount: FragmentAccount by inject()
    private val fragmentExplore: FragmentExplore by inject()
    private val favoriteFragment: FragmentFavorite by inject()
    private val memoryDb: MemoryDb by inject()
    private val preferences: Preferences by inject()
    private var pagerAdapter: ViewPagerAdapter? = null

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
        memoryDb.currentUser.observe(this,{
            preferences.setCurrentUser(it)
            if(it.username == ""){
                doLogin()
            }
        })

        doLogin()
    }

    private fun doLogin() {
        val currentUser = preferences.getCurrentUser()
        if (currentUser?.username == ""){

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
            startActivityIfNeeded(intent, 0)
        }
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