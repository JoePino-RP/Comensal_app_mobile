package com.caanvi.comensal_app_mobile.Login.RecyclerView.GetRestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.R

class RestaurantAdapter (private val _restaurant:List<Restaurant>) : RecyclerView.Adapter<RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RestaurantViewHolder(layoutInflater.inflate(R.layout.prefab_restaurant ,parent,false))
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(_restaurant[position])
    }

    override fun getItemCount() : Int = _restaurant.size
}