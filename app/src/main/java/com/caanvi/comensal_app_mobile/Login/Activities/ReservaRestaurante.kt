package com.caanvi.comensal_app_mobile.Login.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.caanvi.comensal_app_mobile.Login.Api.RetrofitClient
import com.caanvi.comensal_app_mobile.Login.Modals.Platos
import com.caanvi.comensal_app_mobile.Login.Modals.PlatosResponse
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.Login.RecyclerView.GetPlatos.PlatosAdapter
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.ActivityReservaRestauranteBinding
import com.caanvi.comensal_app_mobile.databinding.ReservaDialogBinding
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReservaRestaurante : AppCompatActivity() {

    private lateinit var restaurante : Restaurant
    private lateinit var binding: ActivityReservaRestauranteBinding

    private lateinit var adapter1: PlatosAdapter
    val platosList = mutableListOf<Platos>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReservaRestauranteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        restaurante = intent.getSerializableExtra(EXTRA_RESTAURANTLIST) as Restaurant

         binding.txtNombreRestaurante.setText(restaurante.nombre_res) //Trae el nombre del restaurante

        Picasso.get().load(restaurante.imagen_res).into(binding.mViewPager)

        getPlatos(restaurante.id_res) //restaurante.id//
        //Toast.makeText(applicationContext, restaurante.id_res, Toast.LENGTH_LONG).show()
        initRecyclerPlatos()

        binding.BtnReserva.setOnClickListener {
            val intent = Intent(applicationContext, ReservaRestaurante::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            alertDialog()


        }


    }

    fun initRecyclerPlatos(){
        binding.recyclerRestaurant.layoutManager = LinearLayoutManager(applicationContext)
        adapter1 = PlatosAdapter(platosList, object: PlatosAdapter.OnClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(applicationContext, MapsActivity::class.java)
                intent.putExtra(EXTRA_RESTAURANTLIST, platosList[position])
                startActivity(intent)
            }
        })
        binding.recyclerRestaurant.adapter = adapter1
    }


    fun getPlatos(id: String){ //id : String


        RetrofitClient.instance.getPlatos(id) //id
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

    fun alertDialog (){
        ///////////////ALERT DIALOG////////////////////

        val view = View.inflate(this@ReservaRestaurante, R.layout.reserva_dialog, null)
        val binding = ReservaDialogBinding.bind(view)

        val builder = AlertDialog.Builder(this@ReservaRestaurante)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false) //Linea donde asi ahagas clicc afuera del alert dialog no se cerrara, solo con la equisx(x)

        binding.imageViewClose.setOnClickListener(){
            dialog.dismiss()

            //Cambio de Pantalla
            //val intent = Intent(applicationContext, ProfileActivity::class.java)
            val intent = Intent(applicationContext, PrincipalFragments::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        ///////////////////////////////////////////////
    }



}