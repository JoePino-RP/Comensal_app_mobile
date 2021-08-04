package com.caanvi.comensal_app_mobile.Login.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.caanvi.comensal_app_mobile.Login.Api.RetrofitClient
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.Login.Modals.RestaurantResponse
import com.caanvi.comensal_app_mobile.Login.RecyclerView.GetRestaurant.RestaurantAdapter
import com.caanvi.comensal_app_mobile.Login.RecyclerView.SearchRestaurant.SearchRestaurantAdapter
import com.caanvi.comensal_app_mobile.databinding.ActivitySearchRestaurantBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class searchRestaurant : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivitySearchRestaurantBinding

    private lateinit var adapter: SearchRestaurantAdapter
    val restaurantList = mutableListOf<Restaurant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchRestaurantBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchWord.setOnQueryTextListener(this)
        //searchRestaurantByName()
        initRecyclerRestaurant()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        //codigo adicional
        val intent = Intent(applicationContext, MenuActi::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    fun initRecyclerRestaurant(){
        binding.recyclerSearchRestaurant.layoutManager = LinearLayoutManager(applicationContext)
        adapter = SearchRestaurantAdapter(restaurantList)
        binding.recyclerSearchRestaurant.adapter = adapter
    }

    fun searchRestaurantByName(query: String){
        //var sto:String = "Pampa"

        RetrofitClient.instance.searchRestaurant(query)
            .enqueue(object: retrofit2.Callback<RestaurantResponse> {

                override fun onFailure(call: retrofit2.Call<RestaurantResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: retrofit2.Call<RestaurantResponse>, response: retrofit2.Response<RestaurantResponse>) {

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


    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()){
            searchRestaurantByName(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}