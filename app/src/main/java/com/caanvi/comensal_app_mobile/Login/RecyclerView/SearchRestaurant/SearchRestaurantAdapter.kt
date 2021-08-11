package com.caanvi.comensal_app_mobile.Login.RecyclerView.SearchRestaurant

//import com.caanvi.comensal_app_mobile.Login.RecyclerView.GetRestaurant.RestaurantViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.PrefabRestaurantBinding
import com.squareup.picasso.Picasso

class SearchRestaurantAdapter (private val _restaurant:List<Restaurant>, val onClickListener: OnClickListener) : RecyclerView.Adapter<SearchRestaurantAdapter.SearchRestaurantViewHolder>() {

    interface OnClickListener{
        fun onItemClick(position : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.prefab_restaurant ,parent,false)
        return SearchRestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchRestaurantViewHolder, position: Int) {
        var binding = PrefabRestaurantBinding.bind(holder.itemView)

        binding.txtNombre.text = _restaurant[position].nombre_res
        binding.txtPrecio.text = "Precio: "+_restaurant[position].precio_res
        binding.txtDireccion.text ="Dirección: "+ _restaurant[position].direccion_res
        binding.txtTelefono.text ="Teléfono: "+ _restaurant[position].telefono_res
        Picasso.get().load(_restaurant[position].imagen_res).into(binding.imgRestaurant)

        binding.btnIr.setOnClickListener{
            //Toast.makeText(view.context, "Este es su id : " + _restaurant.id_res, Toast.LENGTH_LONG).show()
            onClickListener.onItemClick(position)
        }

    }

    override fun getItemCount() : Int = _restaurant.size


    class SearchRestaurantViewHolder (view: View): RecyclerView.ViewHolder(view)
}