package com.example.pixabayimages.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabayimages.MemoryDb
import com.example.pixabayimages.R
import com.example.pixabayimages.adapter.ExploreAdapter
import com.example.pixabayimages.networking.ApiReq
import com.example.pixabayimages.networking.Status
import kotlinx.android.synthetic.main.fragment_explore.*
import org.koin.android.ext.android.inject
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.get
import com.example.pixabayimages.PER_PAGE
import com.example.pixabayimages.TAG
import com.example.pixabayimages.model.ImageDetails
import com.example.pixabayimages.persistence.Preferences
import kotlinx.android.synthetic.main.item_explore.view.*


class FragmentExplore : Fragment() {
    private val memoryDb: MemoryDb by inject()
    private val preferences: Preferences by inject()
    private val apiReq: ApiReq by inject()
    private var page: Int = 1
    private var loading = false
    private var isTheEnd = false
    private val fragmentDetails: FragmentDetails by inject()
    private lateinit var exploreAdapter: ExploreAdapter

    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        exploreAdapter = ExploreAdapter(arrayListOf(), requireContext() as AppCompatActivity)
        exploreAdapter.onItemClick = fun(model, position) {
            val previewImage = list.findViewHolderForAdapterPosition(position)?.itemView?.image
            fragmentDetails.setData(model, previewImage!!.drawable)
            fragmentDetails.show(childFragmentManager, "Details")

        }
        list.adapter = exploreAdapter
        resetList()
        list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager?
                if (!loading && !isTheEnd) {
                    if (exploreAdapter.itemCount - 1 == linearLayoutManager?.findLastCompletelyVisibleItemPosition()) {
                        page++
                        loadData()
                    }
                }
            }
        })
        searchView.setOnQueryTextListener(object : OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                memoryDb.query.value = query
                return query.isNullOrEmpty()
            }
            override fun onQueryTextChange(newText: String?): Boolean{
                return newText.isNullOrEmpty()
            }
        })
        refresh.setOnRefreshListener {
            resetList()
            refresh.isRefreshing = false
        }



        exploreAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver(){
            override fun onChanged() {
                if(exploreAdapter.itemCount == 0){
                    noResultsFor.visibility = View.VISIBLE
                    resultsFor.visibility = View.GONE
                    list.visibility = View.GONE
                }else{
                    noResultsFor.visibility = View.GONE
                    resultsFor.visibility = View.VISIBLE
                    list.visibility = View.VISIBLE
                }
                query.text = memoryDb.query.value
                super.onChanged()
            }
        })
        memoryDb.query.observe(viewLifecycleOwner, {
            preferences.setQuery(searchView.query.toString())
            resetList()
            loadData()
        })


    }

    private fun resetList() {
        page = 1
        exploreAdapter.clear()
        loadData()

    }

    fun loadData() {
        loading = true
        exploreAdapter.setLoading()
        val searchQuery = memoryDb.query.value
        apiReq.getImages(page, searchQuery!!)
            .observe(viewLifecycleOwner, {
                exploreAdapter.removeLoading()
                if (it.status == Status.SUCCESS) {
                    exploreAdapter.addAll(it.response!!.hits)
                    isTheEnd = it.response.hits.size < PER_PAGE
                }
                loading = false
            }
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_explore, container, false)
    }


}