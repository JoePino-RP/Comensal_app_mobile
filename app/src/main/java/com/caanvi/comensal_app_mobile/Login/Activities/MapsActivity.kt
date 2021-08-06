package com.caanvi.comensal_app_mobile.Login.Activities

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import com.caanvi.comensal_app_mobile.Login.Modals.GoogleMapDTO
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.Login.Modals.RestaurantResponse
import com.caanvi.comensal_app_mobile.R

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.caanvi.comensal_app_mobile.databinding.ActivityMapsBinding
import com.google.android.gms.common.wrappers.PackageManagerWrapper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private var TAG : String = "MapsActivity";
    private lateinit var mMap: GoogleMap
    private lateinit var restaurant:Restaurant

    private lateinit var fusedLocation : FusedLocationProviderClient

    //private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //binding = ActivityMapsBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        setContentView(R.layout.activity_maps)

        restaurant = intent.getSerializableExtra(EXTRA_RESTAURANTLIST) as Restaurant
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocation = LocationServices.getFusedLocationProviderClient(this)
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


        if(ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
                )!= PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1
            )
            return
        }

        mMap.isMyLocationEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        fusedLocation.lastLocation.addOnSuccessListener { location ->
            if(location != null){
                val userPlace = LatLng(location.latitude, location.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userPlace, 12f))

                var latitud : Double = restaurant.latitud_res//-1.1560104016248383//restaurant.latitud_res
                var longitud: Double =restaurant.longitud_res //-78.5851629985123//restaurant.latitud_res

                //Log.i(TAG, "$latitud")

                val restaurantPlace = LatLng(latitud, longitud)

                //createPolylines()

                mMap.addMarker(MarkerOptions().position(restaurantPlace).title(restaurant.nombre_res))
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(restaurantPlace, 15f))



                val URL = getDirectionURL(userPlace,restaurantPlace)
                //Log.d("GoogleMap", "URL : $URL")
                GetDirection(URL).execute()

            }
        }
    }
    


    fun getDirectionURL(origin:LatLng,dest:LatLng) : String{
        return "https://maps.googleapis.com/maps/api/directions/json?origin=${origin.latitude},${origin.longitude}&destination=${dest.latitude},${dest.longitude}&sensor=false&mode=driving&key=AIzaSyDXy8tIDeOORATubYg4xhfpaktCs4u5K_A"
    }


    private inner class GetDirection(val url : String) : AsyncTask<Void, Void, List<List<LatLng>>>(){
        override fun doInBackground(vararg params: Void?): List<List<LatLng>> {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val data = response.body()!!.string()
            Log.d("GoogleMap" , " data : $data")
            val result =  ArrayList<List<LatLng>>()
            try{
                val respObj = Gson().fromJson(data, GoogleMapDTO::class.java)

                val path =  ArrayList<LatLng>()

                for (i in 0..(respObj.routes[0].legs[0].steps.size-1)){
//                    val startLatLng = LatLng(respObj.routes[0].legs[0].steps[i].start_location.lat.toDouble()
//                            ,respObj.routes[0].legs[0].steps[i].start_location.lng.toDouble())
//                    path.add(startLatLng)
//                    val endLatLng = LatLng(respObj.routes[0].legs[0].steps[i].end_location.lat.toDouble()
//                            ,respObj.routes[0].legs[0].steps[i].end_location.lng.toDouble())
                    path.addAll(decodePolyline(respObj.routes[0].legs[0].steps[i].polyline.points))
                }
                result.add(path)
            }catch (e:Exception){
                e.printStackTrace()
            }
            return result
        }
        override fun onPostExecute(result: List<List<LatLng>>) {
            val lineoption = PolylineOptions()
            for (i in result.indices){
                lineoption.addAll(result[i])
                lineoption.width(10f)
                lineoption.color(Color.BLUE)
                lineoption.geodesic(true)
            }
            mMap.addPolyline(lineoption)
        }
    }


    public fun decodePolyline(encoded: String): List<LatLng> {

            val poly = ArrayList<LatLng>()
            var index = 0
            val len = encoded.length
            var lat = 0
            var lng = 0

            while (index < len) {
                var b: Int
                var shift = 0
                var result = 0
                do {
                    b = encoded[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlat = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lat += dlat

                shift = 0
                result = 0
                do {
                    b = encoded[index++].toInt() - 63
                    result = result or (b and 0x1f shl shift)
                    shift += 5
                } while (b >= 0x20)
                val dlng = if (result and 1 != 0) (result shr 1).inv() else result shr 1
                lng += dlng

                val latLng = LatLng((lat.toDouble() / 1E5),(lng.toDouble() / 1E5))
                poly.add(latLng)
            }

            return poly
    }

}