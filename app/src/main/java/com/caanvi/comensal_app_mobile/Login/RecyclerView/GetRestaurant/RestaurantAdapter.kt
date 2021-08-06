package com.caanvi.comensal_app_mobile.Login.RecyclerView.GetRestaurant

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.ActivityMainBinding
import com.caanvi.comensal_app_mobile.databinding.PrefabRestaurantBinding
import com.squareup.picasso.Picasso

class RestaurantAdapter (private val _restaurant:List<Restaurant>, val onClickListener: OnClickListener) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {


    interface OnClickListener{
        fun onItemClick(position : Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prefab_restaurant ,parent,false)
        return RestaurantViewHolder (view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        var binding = PrefabRestaurantBinding.bind(holder.itemView)

        binding.txtNombre.text = _restaurant[position].nombre_res
        binding.txtPrecio.text = _restaurant[position].precio_res
        binding.txtDireccion.text = _restaurant[position].direccion_res
        binding.txtTelefono.text = _restaurant[position].telefono_res
        Picasso.get().load(_restaurant[position].imagen_res).into(binding.imgRestaurant)

        binding.prefabRes.setOnClickListener{
            onClickListener.onItemClick(position)
        }

    }

    override fun getItemCount() : Int = _restaurant.size

    class RestaurantViewHolder(view: View):RecyclerView.ViewHolder(view)
}