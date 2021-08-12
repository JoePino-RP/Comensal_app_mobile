package com.caanvi.comensal_app_mobile.Login.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.caanvi.comensal_app_mobile.Login.Api.RetrofitClient
import com.caanvi.comensal_app_mobile.Login.Modals.Platos
import com.caanvi.comensal_app_mobile.Login.Modals.PlatosResponse
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.Login.Modals.RestaurantResponse
import com.caanvi.comensal_app_mobile.Login.RecyclerView.GetPlatos.PlatosAdapter
import com.caanvi.comensal_app_mobile.Login.RecyclerView.GetRestaurant.RestaurantAdapter
import com.caanvi.comensal_app_mobile.databinding.ActivityVerRestaurantesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val EXTRA_RESTAURANTLIST = "EXTRA_RESTAURANTLIST"
class VerRestaurantes : AppCompatActivity() {



    private lateinit var binding : ActivityVerRestaurantesBinding
    private lateinit var adapter: RestaurantAdapter
    val restaurantList = mutableListOf<Restaurant>()
    private lateinit var adapter1: PlatosAdapter
    val platosList = mutableListOf<Platos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVerRestaurantesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //getRestaurant()
        //initRecyclerRestaurant()

        //getPlatos()
        //initRecyclerPlatos()

    }

    //Funcion para regresar a la actividad anterior y que no se cierre la app presionando el boton hacia atras
    override fun onBackPressed() {
        super.onBackPressed()
        //codigo adicional
        val intent = Intent(applicationContext, MenuActi::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun initRecyclerRestaurant(){
         binding.recyclerRestaurant.layoutManager = LinearLayoutManager(applicationContext)
         adapter = RestaurantAdapter(restaurantList, object:RestaurantAdapter.OnClickListener{
             override fun onItemClick(position: Int) {
                 val intent = Intent(applicationContext, MapsActivity::class.java)
                 intent.putExtra(EXTRA_RESTAURANTLIST, restaurantList[position])
                 startActivity(intent)
             }
         })
         binding.recyclerRestaurant.adapter = adapter
    }

    fun verUbicacionRestaurant(){

    }

    fun getRestaurant(){
        RetrofitClient.instance.getRestaurant()
            .enqueue(object: Callback<RestaurantResponse> {

                override fun onFailure(call: Call<RestaurantResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
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
                        Toast.makeText(applicationContext, "NO hay restaurantes", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }





    fun initRecyclerPlatos(){
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        adapter1 = PlatosAdapter(platosList, object:PlatosAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(applicationContext, MapsActivity::class.java)
                intent.putExtra(EXTRA_RESTAURANTLIST, platosList[position])
                startActivity(intent)
            }
        })
        binding.recyclerView.adapter = adapter1
    }


    fun getPlatos(){
        RetrofitClient.instance.getPlatos()
            .enqueue(object: Callback<PlatosResponse> {

                override fun onFailure(call: Call<PlatosResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<PlatosResponse>, response: Response<PlatosResponse>) {

                    if(response.body()?.conecto!!){

                        val platosGot : PlatosResponse? = response.body()
                        val addPlatos = platosGot?.platos?: emptyList()
                        platosList.clear()
                        platosList.addAll(addPlatos)
                        adapter1.notifyDataSetChanged()

                    }else{

                        //Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        Toast.makeText(applicationContext, "NO hay platos", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }



}