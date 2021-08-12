package com.caanvi.comensal_app_mobile.Login.RecyclerView.GetPlatos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caanvi.comensal_app_mobile.Login.Modals.Platos
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.PlatosBinding


import com.squareup.picasso.Picasso

class PlatosAdapter (private val _restaurant:List<Platos>, val onClickListener: OnClickListener):  RecyclerView.Adapter<PlatosAdapter.PlatosViewHolder>() {


    interface OnClickListener{
        fun onItemClick(position : Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatosViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.platos ,parent,false)
        return PlatosViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlatosViewHolder, position: Int) {
        //var binding = PlatosBinding.bind(holder.itemView)
        var binding = PlatosBinding.bind(holder.itemView)



            //Añadi el texto que identifica a la información
        binding.txtNombrePlato.text =  _restaurant[position].nombre_plato
        binding.txtPrecioPlato.text = "Precio: "+_restaurant[position].precio_plato
        binding.txtDescripcionPlato.text ="Descripcion: "+ _restaurant[position].descripcion_plato
       // binding.txtTelefono.text ="Teléfono: "+ _restaurant[position].
        Picasso.get().load(_restaurant[position].imagen_plato).into(binding.imgRestaurant)


    }

    override fun getItemCount(): Int = _restaurant.size


    class PlatosViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    }

}