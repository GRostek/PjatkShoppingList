package com.example.pjatkshoppinglist.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pjatkshoppinglist.R
import com.example.pjatkshoppinglist.adapter.ShopAdapter
import com.example.pjatkshoppinglist.db.viewmodel.ShopViewModel
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.android.synthetic.main.fragment_map.backButtonMap
import kotlinx.android.synthetic.main.fragment_shop_list.*

class ShopListFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)


        return inflater.inflate(
                R.layout.fragment_shop_list,
                container,
                false
        )



    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        backButtonMap.setOnClickListener {
            activity?.finish()
        }


        shopList.layoutManager = LinearLayoutManager(activity)
        shopList.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))

        val shopViewModel = ShopViewModel(activity!!.application)
        val adapter = ShopAdapter(shopViewModel, activity as AppCompatActivity)

        shopViewModel.allShops.observe(this, {shops ->
            shops?.let{
                adapter.setShops(it)
            }
        })

        shopList.adapter = adapter


    }







}