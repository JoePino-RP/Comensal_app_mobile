package com.caanvi.comensal_app_mobile.Login.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.caanvi.comensal_app_mobile.Login.Activities.EXTRA_RESTAURANTLIST
import com.caanvi.comensal_app_mobile.Login.Activities.MapsActivity
import com.caanvi.comensal_app_mobile.Login.Activities.MenuActi
import com.caanvi.comensal_app_mobile.Login.Activities.ReservaRestaurante
import com.caanvi.comensal_app_mobile.Login.Api.RetrofitClient
import com.caanvi.comensal_app_mobile.Login.Modals.FavoriteResponse
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.Login.Modals.RestaurantResponse
import com.caanvi.comensal_app_mobile.Login.RecyclerView.GetRestaurant.RestaurantAdapter
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.BusquedaDialogBinding
import com.caanvi.comensal_app_mobile.databinding.FragmentHomeBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {
    // ASI SE UTILIZA EL BINDIN EN FRAGMENTS
    private  var _binding : FragmentHomeBinding? = null
    private val binding get()= _binding!!
    /////////////////////////////////////////

    //Posicion Usuario
    private lateinit var fusedLocation : FusedLocationProviderClient
    //Variables para Verificar GPS activo o no
    private lateinit var locationManager: LocationManager
    var intent1: Intent? = null
    var gpsStatus = false


    //Llenar nuestras listas
    private lateinit var adapter: RestaurantAdapter
    val restaurantList = mutableListOf<Restaurant>()


    //variables Busqueda
    val busquedaList = mutableListOf<String>()
    var carnes = "empty"
    var vegetales = "empty"
    var postres = "empty"
    var tacos = "empty"
    var comidaMar = "empty"
    var comidaRapida = "empty"

    @SuppressLint("ResourceType")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        fusedLocation = LocationServices.getFusedLocationProviderClient(requireActivity().applicationContext)

        initRecyclerRestaurant()
        startAll()

        binding.txtBusqueda.setOnQueryTextListener(this)

        //Cambiar de Vista de un activitie a otro

        binding.btnBusqueda.setOnClickListener(){
            alertDialog ()
        }

        busquedaList.add(carnes)
        busquedaList.add(vegetales)
        busquedaList.add(postres)
        busquedaList.add(tacos)
        busquedaList.add(comidaMar)
        busquedaList.add(comidaRapida)

        return binding.root
    }

    fun startAll(){
        if(ActivityCompat.checkSelfPermission(
                requireActivity().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )!= PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1
            )
            return
        }

        if(checkGpsStatus()){
            fusedLocation.lastLocation.addOnSuccessListener { location ->
                if(location != null){
                    val userPlace = LatLng(location.latitude, location.longitude)
                    getRestaurant(userPlace.latitude, userPlace.longitude)
                    initRecyclerRestaurant()
                }
            }
        }
        else{
            alertMessage()
        }
    }


    fun startAll2(keyword:String){
        if(ActivityCompat.checkSelfPermission(
                requireActivity().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )!= PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1
            )
            return
        }

        if(checkGpsStatus()){
            fusedLocation.lastLocation.addOnSuccessListener { location ->
                if(location != null){
                    val userPlace = LatLng(location.latitude, location.longitude)
                    getRestaurantKeyword(userPlace.latitude, userPlace.longitude, keyword)
                    initRecyclerRestaurant()
                }
            }
        }
        else{
            alertMessage()
        }
    }

    fun startAll3(listKeyword:List<String>){
        if(ActivityCompat.checkSelfPermission(
                requireActivity().applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )!= PackageManager.PERMISSION_GRANTED
        )
        {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1
            )
            return
        }

        if(checkGpsStatus()){
            fusedLocation.lastLocation.addOnSuccessListener { location ->
                if(location != null){
                    val userPlace = LatLng(location.latitude, location.longitude)
                    getRestaurantFilterKeyword(userPlace.latitude, userPlace.longitude, listKeyword)
                    initRecyclerRestaurant()
                }
            }
        }
        else{
            alertMessage()
        }
    }

    private fun checkGpsStatus() :Boolean {
        locationManager = requireActivity().applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        gpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        //if (gpsStatus)
        return gpsStatus
    }


    //Alert Message
    fun alertMessage(){
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("GPS")
        builder.setMessage("Para usar el App debes activar el GPS")
        builder.setCancelable(false)
        builder.setPositiveButton("Activar"){
                dialog, wich-> activateGPS()
        }
        builder.setNegativeButton("No Gracias"){
                dialog, wich-> returnBack()
        }
        builder.setNeutralButton("Ya lo hice"){
                dialog, wich-> review()
        }
        builder.show()

    }


    fun returnBack(){
        val intent = Intent( requireActivity().applicationContext, MenuActi::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    fun activateGPS() {
        intent1 = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent1);
    }

    fun review(){
        startAll()
    }

    //Get Restaurants
    fun initRecyclerRestaurant(){
        binding.recyclerRestaurant.layoutManager = LinearLayoutManager(requireActivity().applicationContext)
        adapter = RestaurantAdapter(restaurantList, object:RestaurantAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(requireActivity().applicationContext, MapsActivity::class.java)
                intent.putExtra(EXTRA_RESTAURANTLIST, restaurantList[position])
                startActivity(intent)
            }
            override fun onItemClick1(position: Int) {
                val intent = Intent(requireActivity().applicationContext, ReservaRestaurante::class.java)
                intent.putExtra(EXTRA_RESTAURANTLIST, restaurantList[position])
                startActivity(intent)
            }
            override fun onItemStar(position: Int) {
                addFavorites("1", restaurantList[position].id_res);
            }
        })
        binding.recyclerRestaurant.adapter = adapter
    }

    fun getRestaurant(latitude:Double, longitude:Double){
        RetrofitClient.instance.getRestaurant(latitude, longitude)
            .enqueue(object: Callback<RestaurantResponse> {

                override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                    Toast.makeText(requireActivity().applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<RestaurantResponse>, response: Response<RestaurantResponse>) {

                    if(response.body()?.conecto!!){

                        val restaurantGot : RestaurantResponse? = response.body()
                        val addRestaurant = restaurantGot?.restaurant?: emptyList()
                        restaurantList.clear()
                        restaurantList.addAll(addRestaurant)
                        adapter.notifyDataSetChanged()

                    }else{

                        //Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        Toast.makeText(requireActivity().applicationContext, "NO hay restaurantes", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }


    fun getRestaurantKeyword(latitude:Double, longitude:Double, keyword:String){
        RetrofitClient.instance.getRestaurantKeywrod(latitude, longitude, keyword)
            .enqueue(object: Callback<RestaurantResponse> {

                override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                    Toast.makeText(requireActivity().applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<RestaurantResponse>, response: Response<RestaurantResponse>) {

                    if(response.body()?.conecto!!){

                        val restaurantGot : RestaurantResponse? = response.body()
                        val addRestaurant = restaurantGot?.restaurant?: emptyList()
                        restaurantList.clear()
                        restaurantList.addAll(addRestaurant)
                        adapter.notifyDataSetChanged()

                    }else{

                        //Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        Toast.makeText(requireActivity().applicationContext, "NO hay restaurantes", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }


    fun getRestaurantFilterKeyword(latitude:Double, longitude:Double, listKeyword:List<String>){
        RetrofitClient.instance.searchFilterRestaurant(latitude, longitude, listKeyword[0],listKeyword[1],listKeyword[2],listKeyword[3],listKeyword[4],listKeyword[5])
            .enqueue(object: Callback<RestaurantResponse> {

                override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                    Toast.makeText(requireActivity().applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<RestaurantResponse>, response: Response<RestaurantResponse>) {

                    if(response.body()?.conecto!!){

                        val restaurantGot : RestaurantResponse? = response.body()
                        val addRestaurant = restaurantGot?.restaurant?: emptyList()
                        restaurantList.clear()
                        restaurantList.addAll(addRestaurant)
                        adapter.notifyDataSetChanged()

                    }else
                    {

                        //Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        Toast.makeText(requireActivity().applicationContext, "NO hay restaurantes", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }


    fun addFavorites(idComensal:String , idRestaurante:String){
        RetrofitClient.instance.addFavorite(idComensal, idRestaurante)
            .enqueue(object: Callback<FavoriteResponse> {
                override fun onFailure(call: Call<FavoriteResponse>, t: Throwable) {
                    Toast.makeText(requireActivity().applicationContext, t.message, Toast.LENGTH_LONG).show()
                }
                override fun onResponse(call: Call<FavoriteResponse>, response: Response<FavoriteResponse>) {
                    if(response.body()?.conecto!!){
                        Toast.makeText(requireActivity().applicationContext, "Añadido a Favoritos", Toast.LENGTH_SHORT).show()
                    }else{
                        //Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        Toast.makeText(requireActivity().applicationContext, "Algo salió mal", Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            startAll2(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }


    fun alertDialog (){
        ///////////////ALERT DIALOG////////////////////
        emptyAll()
        val view = View.inflate(requireActivity().applicationContext, R.layout.busqueda_dialog, null)
        //val binding = FiltroBusquedaBinding.bind(view)
        val binding = BusquedaDialogBinding.bind(view)
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(view)
        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        //dialog.setCancelable(false)
        binding.checkBoxCarnes.setOnCheckedChangeListener { buttonView, isChecked ->
            if( binding.checkBoxCarnes.isChecked){
                busquedaList[0] = "Carnes"
            }
            else{
                busquedaList[0] = "empty"
            }
        }
        binding.checkBoxVegetales.setOnCheckedChangeListener { buttonView, isChecked ->
            if( binding.checkBoxVegetales.isChecked){
                busquedaList[1] = "Vegetales"
            }
            else{
                busquedaList[1] = "empty"
            }
        }
        binding.checkBoxPostres.setOnCheckedChangeListener { buttonView, isChecked ->
            if( binding.checkBoxPostres.isChecked){
                busquedaList[2] = "Postres"
            }
            else{
                busquedaList[2] = "empty"
            }
        }
        binding.checkBoxTacos.setOnCheckedChangeListener { buttonView, isChecked ->
            if( binding.checkBoxTacos.isChecked){
                busquedaList[3] = "Tacos"
            }
            else{
                busquedaList[3] = "empty"
            }
        }
        binding.checkBoxComidaMar.setOnCheckedChangeListener { buttonView, isChecked ->
            if( binding.checkBoxComidaMar.isChecked){
                busquedaList[4] = "Comida de Mar"
            }
            else{
                busquedaList[4] = "empty"
            }
        }
        binding.checkBoxComidaRapida.setOnCheckedChangeListener { buttonView, isChecked ->
            if( binding.checkBoxComidaRapida.isChecked){
                busquedaList[5] = "Comida Rápida"
            }
            else{
                busquedaList[5] = "empty"
            }
        }
        binding.imageViewClose.setOnClickListener(){
        }
        binding.btnAplicarFiltro.setOnClickListener(){
            dialog.dismiss()
            startAll3( busquedaList)
        }
        ///////////////////////////////////////////////
    }
    fun emptyAll(){
        carnes = "empty"
        vegetales = "empty"
        postres = "empty"
        tacos = "empty"
        comidaMar = "empty"
        comidaRapida = "empty"
        busquedaList[0] =carnes
        busquedaList[1] =vegetales
        busquedaList[2] =postres
        busquedaList[3] =tacos
        busquedaList[4] =comidaMar
        busquedaList[5] =comidaRapida
    }
}