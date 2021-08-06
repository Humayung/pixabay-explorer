package com.example.pixabayimages.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.pixabayimages.MemoryDb
import com.example.pixabayimages.R
import com.example.pixabayimages.TAG
import com.example.pixabayimages.adapter.FavouriteAdapter
import com.example.pixabayimages.networking.ApiReq
import com.example.pixabayimages.persistence.Preferences
import kotlinx.android.synthetic.main.fragment_explore.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.item_explore.view.*
import org.koin.android.ext.android.inject


class FragmentFavorite : Fragment() {
    private val memoryDb: MemoryDb by inject()
    private val preferences: Preferences by inject()
    private val apiReq: ApiReq by inject()
    private var page: Int = 1
    private var loading = false
    private var isTheEnd = false
    private val fragmentDetails: FragmentDetails by inject()
    private lateinit var favouriteAdapter: FavouriteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun onResume() {
        val currentUser = memoryDb.currentUser.value
        favouriteAdapter.setData(currentUser!!.favoriteList)
        favouriteAdapter.notifyDataSetChanged()
        super.onResume()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = memoryDb.currentUser.value
        favouriteAdapter = FavouriteAdapter(currentUser!!.favoriteList, requireContext() as AppCompatActivity)
        favouriteAdapter.onItemClick = fun(model, position) {
            val previewImage = listfavourite.findViewHolderForAdapterPosition(position)?.itemView?.image
            Log.d(TAG, "im clicked: sd")
            fragmentDetails.setData(model, previewImage!!.drawable)
            fragmentDetails.show(childFragmentManager, "Details")
        }
        listfavourite.adapter = favouriteAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }


}