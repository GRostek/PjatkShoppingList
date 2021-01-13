package com.example.pjatkshoppinglist.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pjatkshoppinglist.R
import kotlinx.android.synthetic.main.fragment_map.*

class ShopListFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        backButtonMap.setOnClickListener {
            activity?.finish()
        }

        return inflater.inflate(
                R.layout.fragment_shop_list,
                container,
                false
        )



    }
}