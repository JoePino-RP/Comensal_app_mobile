package com.caanvi.comensal_app_mobile.Login.Activities


import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.ActivityFiltroBusquedaBinding
import com.caanvi.comensal_app_mobile.databinding.BusquedaDialogBinding

class FiltroBusqueda : AppCompatActivity() {

    private lateinit var binding: ActivityFiltroBusquedaBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filtro_busqueda)

        binding = ActivityFiltroBusquedaBinding.inflate(layoutInflater)
        setContentView(binding.root)

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


        binding.imageViewClose.setOnClickListener(){
            dialog.dismiss()

            //Cambio de Pantalla
            val intent = Intent(applicationContext, PrincipalFragments::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        ///////////////////////////////////////////////
    }




}