package com.caanvi.comensal_app_mobile.Login.Activities


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.caanvi.comensal_app_mobile.Login.Modals.Restaurant
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.ActivityFiltroBusquedaBinding
import com.caanvi.comensal_app_mobile.databinding.BusquedaDialogBinding


class FiltroBusqueda : AppCompatActivity() {

    private lateinit var binding: ActivityFiltroBusquedaBinding

    val busquedaList = mutableListOf<String>()
    var postres:String = ""
    var carnes:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtro_busqueda)

        binding = ActivityFiltroBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        busquedaList.add(postres)
        busquedaList.add(carnes)
        alertDialog()
    }


    fun alertDialog (){
        ///////////////ALERT DIALOG////////////////////

        val view = View.inflate(this@FiltroBusqueda, R.layout.busqueda_dialog, null)
        //val binding = FiltroBusquedaBinding.bind(view)
        val binding = BusquedaDialogBinding.bind(view)

        val builder = AlertDialog.Builder(this@FiltroBusqueda)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false) //Linea donde asi ahagas clicc afuera del alert dialog no se cerrara, solo con la equisx(x)


        binding.btnAplicarFiltro.setOnClickListener(){
            dialog.dismiss()


            //Cambio de Pantalla
            val intent = Intent(applicationContext, PrincipalFragments::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)



        }

        binding.checkBoxPostres.setOnCheckedChangeListener { buttonView, isChecked ->
            if( binding.checkBoxPostres.isChecked){
                busquedaList[0] = "Postres"
                Toast.makeText(applicationContext, busquedaList.toString(), Toast.LENGTH_SHORT).show()
            }
            else{
                busquedaList[0] = ""
                Toast.makeText(applicationContext, busquedaList.toString(), Toast.LENGTH_SHORT).show()
            }

        }

        binding.checkBoxCarnes.setOnCheckedChangeListener { buttonView, isChecked ->
            if( binding.checkBoxCarnes.isChecked){
                busquedaList[1] = "Carnes"
                Toast.makeText(applicationContext, busquedaList.toString(), Toast.LENGTH_SHORT).show()
            }
            else{
                busquedaList[1] = ""
                Toast.makeText(applicationContext, busquedaList.toString(), Toast.LENGTH_SHORT).show()
            }
        }

        binding.imageViewClose.setOnClickListener(){

            val intent = Intent(applicationContext, PrincipalFragments::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }

        ///////////////////////////////////////////////
    }
}