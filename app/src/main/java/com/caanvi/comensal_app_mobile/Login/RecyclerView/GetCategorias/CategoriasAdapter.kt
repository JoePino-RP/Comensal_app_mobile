package com.caanvi.comensal_app_mobile.Login.RecyclerView.GetCategorias

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caanvi.comensal_app_mobile.Login.Modals.Categorias
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.CategoriaComidaBinding
import com.squareup.picasso.Picasso


class CategoriasAdapter(private val _restaurant:List<Categorias>, val onClickListener: OnClickListener):  RecyclerView.Adapter<CategoriasAdapter.CategoriasViewHolder>()  {


    interface OnClickListener{
        fun onItemClick(position : Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriasViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.categoria_comida ,parent,false)
        return CategoriasViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriasViewHolder, position: Int) {

        var binding = CategoriaComidaBinding.bind(holder.itemView)




        //Añadi el texto que identifica a la información
        binding.txtNombreCategoria.text =  _restaurant[position].nombre_categoria
        Picasso.get().load(_restaurant[position].imagen_categoria).into(binding.imgRestaurant)

    }

    override fun getItemCount(): Int = _restaurant.size

        class CategoriasViewHolder (view: View) : RecyclerView.ViewHolder(view)

}