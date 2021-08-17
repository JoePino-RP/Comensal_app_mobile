package com.caanvi.comensal_app_mobile.Login.RecyclerView.GetFavorites

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.Login.RecyclerView.GetRestaurant.RestaurantAdapter
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.PrefabRestaurantBinding
import com.squareup.picasso.Picasso

class FavoriteAdapter (private val _restaurant:List<Restaurant>, val onClickListener: OnClickListener) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    interface OnClickListener{
        fun onItemClick(position : Int)
        fun onItemClick1(position : Int)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prefab_restaurant ,parent,false)
        return FavoriteViewHolder (view)
    }
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        var binding = PrefabRestaurantBinding.bind(holder.itemView)


        //Añadir el texto que identifica a la información
        binding.txtRestaurantName.text =  _restaurant[position].nombre_res
        binding.txtPrecio.text = "Precio: "+_restaurant[position].precio_res
        binding.txtDireccion.text ="Dirección: "+ _restaurant[position].direccion_res
        binding.txtTelefono.text ="Teléfono: "+ _restaurant[position].telefono_res
        Picasso.get().load(_restaurant[position].imagen_res).into(binding.imgRestaurant)

        //Boton para ir al mapa
        binding.btnIr.setOnClickListener{
            onClickListener.onItemClick(position)
        }

        //Boton para ir Reservar
        binding.btnReservar.setOnClickListener{
            onClickListener.onItemClick1(position)
        }

    }
    override fun getItemCount() : Int = _restaurant.size

    class FavoriteViewHolder(view: View): RecyclerView.ViewHolder(view)
}