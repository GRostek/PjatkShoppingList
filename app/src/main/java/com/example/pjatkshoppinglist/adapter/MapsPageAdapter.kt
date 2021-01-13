package com.example.pjatkshoppinglist.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.ListFragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pjatkshoppinglist.activity.MapsFragment

class MapsPageAdapter(fragmentActivity: FragmentActivity, private val tab_count: Int) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return tab_count
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return MapsFragment()
            1 -> return ListFragment()
        }

        return MapsFragment()
    }
}