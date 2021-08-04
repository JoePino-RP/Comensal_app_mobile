package com.caanvi.comensal_app_mobile.Login.RecyclerView.SearchRestaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.Login.RecyclerView.GetRestaurant.RestaurantViewHolder
import com.caanvi.comensal_app_mobile.R

class SearchRestaurantAdapter (private val _restaurant:List<Restaurant>) : RecyclerView.Adapter<SearchRestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRestaurantViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return SearchRestaurantViewHolder(layoutInflater.inflate(R.layout.prefab_restaurant ,parent,false))
    }

    override fun onBindViewHolder(holder: SearchRestaurantViewHolder, position: Int) {
        holder.bind(_restaurant[position])
    }

    override fun getItemCount() : Int = _restaurant.size

}