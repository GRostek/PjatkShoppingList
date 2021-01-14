package com.example.pjatkshoppinglist.activity

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.Manifest
import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Camera
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pjatkshoppinglist.R
import com.example.pjatkshoppinglist.db.model.Shop
import com.example.pjatkshoppinglist.db.viewmodel.ShopViewModel
import com.example.pjatkshoppinglist.receiver.GeofenceReceiver
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_map.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    private lateinit var geofencingClient: GeofencingClient

    var isMapReady = false
    var isZoomed = false

    private var geoId = 0

    private val markerList = mutableListOf<Marker>()
    private val pendingIntentList = mutableListOf<PendingIntent>()



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)




        return inflater.inflate(
                R.layout.fragment_map,
                container,
                false
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val perms = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (ActivityCompat.checkSelfPermission(
                        activity!!.applicationContext,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        activity!!.applicationContext,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(perms, 0)
        }

        geofencingClient = LocationServices.getGeofencingClient(activity!!)

        val mapFragment = childFragmentManager
                .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)




        backButtonMap.setOnClickListener{
            activity?.finish()
        }


        addButtonMap.setOnClickListener {
            LocationServices.getFusedLocationProviderClient(activity!!.applicationContext)
                    .lastLocation
                    .addOnCompleteListener {
                        val intent = Intent(activity!!.applicationContext, AddShopActivity::class.java)
                        intent.putExtra("latitude", it.result.latitude)
                        intent.putExtra("longitude", it.result.longitude)
                        println("wysylam: " + it.result.latitude + " " + it.result.longitude)
                        startActivityForResult(intent, 111)
                    }
        }

    }


    //Dodanie markera po powrocie z addshopactivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if(resultCode == Activity.RESULT_OK){
            if(activity != null) {
                /*
                val name = data!!.getStringExtra("name")
                val description = data!!.getStringExtra("description")

                val latitude = data!!.getDoubleExtra("latitude", 0.0)
                val longitude= data!!.getDoubleExtra("longitude", 0.0)
                val radius = data!!.getDoubleExtra("radius", 0.0)

                val id = data!!.getLongExtra("id", -1)

                val shopMarker = LatLng(latitude, longitude)




                val marker = mMap.addMarker(
                        MarkerOptions()
                            .position(shopMarker)
                            .title(name)
                            .snippet(description))
                */

                redrawMarkers()



            }
        }
    }



    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.isMyLocationEnabled = true
        isMapReady = true





            addMarkers()

        val shopViewModel = ShopViewModel(activity!!.application)
        shopViewModel.allShops.observe(this, { _ ->
            redrawMarkers()
        })


    }




    /*override fun onAttach(context: Context) {
        super.onAttach(context)

    }*/

    private fun addMarkers(){
        val shopViewModel = ShopViewModel(activity!!.application)

        var markersList: List<Shop>
        CoroutineScope(Dispatchers.IO).launch {
            markersList = shopViewModel.getShopsAsync()


            CoroutineScope(Dispatchers.Main).launch{

                for(m in markersList.iterator()){

                    val shopMarker = LatLng(m.latitude, m.longitude)

                    val marker = mMap.addMarker(
                            MarkerOptions()
                                    .position(shopMarker)
                                    .title(m.name)
                                    .snippet(m.description))
                    markerList.add(marker)

                    addGeo(shopMarker, m.name, m.radius)
                }
                if(!isZoomed && markersList.size > 0) {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerList[markerList.size - 1].position, 10f))
                    isZoomed = true
                }
            }


        }



    }

    private fun removeMarkers(){
        mMap.clear()
        markerList.clear()
        for(p in pendingIntentList)
            geofencingClient.removeGeofences(p)
        pendingIntentList.clear()
    }

    private fun redrawMarkers(){
        removeMarkers()
        addMarkers()
    }


    override fun onResume() {
        super.onResume()
        if(isMapReady)
            redrawMarkers()
    }


    @SuppressLint("MissingPermission")
    private fun addGeo(shopMarker: LatLng, name: String, radius: Float){
        val geofence = Geofence.Builder()
                .setRequestId("Geo${geoId++}")
                .setCircularRegion(
                        shopMarker.latitude,
                        shopMarker.longitude,
                        radius)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()

        val geofenceRequest = GeofencingRequest.Builder()
                .addGeofence(geofence)
                .setInitialTrigger(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build()

        val intent = Intent(activity!!, GeofenceReceiver::class.java)
        intent.putExtra("name", name)

        val pendingIntent = PendingIntent.getBroadcast(
                activity!!,
                geoId,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        )

        geofencingClient.addGeofences(geofenceRequest, pendingIntent)

        pendingIntentList.add(pendingIntent)


    }



}