package com.example.pixabayimages.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val mFragmentList: MutableList<Fragment> = ArrayList()
    fun clear() {
        mFragmentList.clear()
        notifyDataSetChanged()
    }

    fun add(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        notifyDataSetChanged()
    }

    fun replaceAll(fragments: ArrayList<Fragment>) {
        mFragmentList.clear()
        mFragmentList.addAll(fragments)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }

    fun toggle(order: Int): Boolean {
        return true
    }
}