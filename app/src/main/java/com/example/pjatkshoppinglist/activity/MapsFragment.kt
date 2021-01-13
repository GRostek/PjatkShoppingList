package com.example.pjatkshoppinglist.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pjatkshoppinglist.R
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*



class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)

        val perms = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if(activity != null)
        if (ActivityCompat.checkSelfPermission(
                        activity!!.applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        activity!!.applicationContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(perms, 0)
        }
        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)



        backButtonMap.setOnClickListener{
            activity?.finish()
        }


        addButtonMap.setOnClickListener {
            if(activity != null)
            LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)
                    .lastLocation
                    .addOnCompleteListener {
                        val intent = Intent(activity!!.applicationContext, AddShopActivity::class.java)
                        intent.putExtra("latitude", it.result.latitude)
                        intent.putExtra("longitude", it.result.longitude)
                        startActivity(intent)
                    }
        }


        return inflater.inflate(
                R.layout.fragment_map,
                container,
                false
        )
    }


    //Dodanie markera po powrocie z addshopactivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(activity != null) {
                val name = activity!!.intent.getStringExtra("name")
                val description = activity!!.intent.getStringExtra("description")

                val latitude = activity!!.intent.getDoubleExtra("latitude", 0.0)
                val longitude= activity!!.intent.getDoubleExtra("longitude", 0.0)
                val radius = activity!!.intent.getDoubleExtra("radius", 0.0)

                val shopMarker = LatLng(latitude, longitude)

                mMap.addMarker(
                    MarkerOptions()
                        .position(shopMarker)
                        .title(name)
                        .snippet(description))
            }
        }
    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        //mMap.isMyLocationEnabled = true

        //TODO Tutaj ladowanie markerow z bazy danych w petli
        addMarkers()
    }


    /*override fun onAttach(context: Context) {
        super.onAttach(context)

    }*/

    fun addMarkers(){

    }
}