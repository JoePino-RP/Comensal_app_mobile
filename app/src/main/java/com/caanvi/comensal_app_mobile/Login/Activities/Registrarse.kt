package com.caanvi.comensal_app_mobile.Login.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.ActivityRegistrarseBinding


class Registrarse : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)

        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Cambiar de Vista de un activitie a otro
        binding.goLogin.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.btnRegistro.setOnClickListener {
            //Obtener los datos de editText
            var nombre: String = binding.nombreRe.toString()
            var apellido: String = binding.apellidoRe.toString()
            var telefono: String = binding.telefonoRe.toString()
            var email: String = binding.emailRe.toString()
            var password: String = binding.passwordRe.toString()

            registrarse(nombre.trim(),apellido.trim(),telefono.trim(),email.trim(), password.trim())
        }





    }





    fun registrarse (nombre: String,apellido: String,telefono: String,
                     email: String, password:String){





/*

        RetrofitClient.instance.useRegistro(nombre,apellido,telefono,email, password)
            .enqueue(object: retrofit2.Callback<registro> {

                override fun onFailure(call: retrofit2.Call<registro>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: retrofit2.Call<registro>, response: retrofit2.Response<registro>) {


                }


            })

            */

    }








    //Funcion para regresar a la actividad anterior y que no se cierre la app presionando el boton hacia atras
    override fun onBackPressed() {
        super.onBackPressed()
        //codigo adicional
        val intent = Intent(applicationContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}