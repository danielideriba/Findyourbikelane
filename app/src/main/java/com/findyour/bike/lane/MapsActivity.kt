package com.findyour.bike.lane

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.findyour.bike.lane.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonLayer.GeoJsonOnFeatureClickListener
import org.json.JSONException
import java.io.IOException


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun retrieveFileFromResource(context: Context, mMap: GoogleMap) {
        try {
            val layer = GeoJsonLayer(mMap, R.raw.santiago_geo_json, context)
            addGeoJsonLayerToMap(layer)
        } catch (e: IOException) {
            Log.e("TAG", "GeoJSON file could not be read")
        } catch (e: JSONException) {
            Log.e("TAG", "GeoJSON file could not be converted to a JSONObject")
        }
    }

    private fun addGeoJsonLayerToMap(layer: GeoJsonLayer) {
        layer.addLayerToMap()
        var style = layer.defaultLineStringStyle
        style.color = Color.RED


//        layer.setOnFeatureClickListener(GeoJsonOnFeatureClickListener { feature ->
//            Toast.makeText(
//                this,
//                "Feature clicked: " + feature.getProperty("name"),
//                Toast.LENGTH_SHORT
//            ).show()
//        })
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
//        val santiago = LatLng(-33.4451371, -70.6578067)
        val santiagoCallao = LatLng(-33.41954008317103,-70.59893227007824)

        mMap.addMarker(MarkerOptions().position(santiagoCallao).title("Marker in Santiago - Chile"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(santiagoCallao, 15.0f))

        retrieveFileFromResource(this, mMap)
    }
}