package com.example.pjatkshoppinglist.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pjatkshoppinglist.activity.MapsFragment
import com.example.pjatkshoppinglist.activity.ShopListFragment

class MapsPageAdapter(fragmentActivity: FragmentActivity, private val tab_count: Int) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return tab_count
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return MapsFragment()
            1 -> return ShopListFragment()
        }

        return MapsFragment()
    }
}