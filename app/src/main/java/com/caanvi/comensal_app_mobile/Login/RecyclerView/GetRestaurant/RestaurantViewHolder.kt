package com.caanvi.comensal_app_mobile.Login.RecyclerView.GetRestaurant

import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.databinding.PrefabRestaurantBinding
import com.squareup.picasso.Picasso

class RestaurantViewHolder(val view: View):RecyclerView.ViewHolder(view) {

    private val binding = PrefabRestaurantBinding.bind(view)
    fun bind(_restaurant: Restaurant)
    {
        binding.txtNombre.text = _restaurant.nombre_res
        binding.txtPrecio.text = "Precio: "+_restaurant.precio_res
        binding.txtDireccion.text = "Dirección: "+ _restaurant.direccion_res
        binding.txtTelefono.text = "Teléfono: "+ _restaurant.telefono_res
        Picasso.get().load(_restaurant.imagen_res).into(binding.imgRestaurant)

        binding.prefabRes.setOnClickListener{
            Toast.makeText(view.context, "Este es su id : " + _restaurant.id_res, Toast.LENGTH_LONG).show()
        }
    }
}