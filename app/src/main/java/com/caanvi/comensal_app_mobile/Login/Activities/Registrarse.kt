package com.caanvi.comensal_app_mobile.Login.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.caanvi.comensal_app_mobile.Login.Api.RetrofitClient
import com.caanvi.comensal_app_mobile.Login.Modals.registro
import com.caanvi.comensal_app_mobile.Login.SQLite.DatabaseHelper
import com.caanvi.comensal_app_mobile.Login.Storage.usuarioData
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.ActivityRegistrarseBinding
import com.caanvi.comensal_app_mobile.databinding.RegistroDialogBinding


class Registrarse : AppCompatActivity() {

    lateinit var handler: DatabaseHelper
    private lateinit var binding: ActivityRegistrarseBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrarse)



        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = DatabaseHelper(this)

        //Cambiar de Vista de un activitie a otro
        binding.goLogin.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        binding.btnRegistro.setOnClickListener {
            //Obtener los datos de editText
            var nombre: String = binding.nombreRe.text.toString()
            var apellido: String = binding.apellidoRe.text.toString()
            var telefono: String = binding.telefonoRe.text.toString()
            var email: String = binding.emailRe.text.toString()
            var password: String = binding.passwordRe.text.toString()

            registrarse(nombre,apellido,telefono,email.trim(), password)
        }
    }


    fun alertDialog (){
        ///////////////ALERT DIALOG////////////////////

        val view = View.inflate(this@Registrarse, R.layout.registro_dialog, null)
        val binding = RegistroDialogBinding.bind(view)

        val builder = AlertDialog.Builder(this@Registrarse)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false) //Linea donde asi ahagas clicc afuera del alert dialog no se cerrara, solo con la equisx(x)

        binding.imageViewClose.setOnClickListener(){
            dialog.dismiss()

            //Cambio de Pantalla
            val intent = Intent(applicationContext, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }


        ///////////////////////////////////////////////
    }





    fun registrarse (nombre: String, apellido: String, telefono: String, email: String, password:String){

        RetrofitClient.instance.userRegistro(nombre,apellido,telefono,email,password)
            .enqueue(object: retrofit2.Callback<registro> {

                override fun onFailure(call: retrofit2.Call<registro>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: retrofit2.Call<registro>, response: retrofit2.Response<registro>) {

                    if(response.body()?.conecto!!){
                        usuarioData.idGeneral = response.body()?.user?.id!!
                        usuarioData.emailGeneral = response.body()?.user?.email!!

                        //SQLite lo recordamos
                        insertarSQLite(usuarioData.idGeneral , usuarioData.emailGeneral)

                        //Muestra Alert Dialog de confirmacion de Registro
                        alertDialog()


                        //Mensaje de Inicio de Session Correcto
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        /*
                        //Cambio de Pantalla
                        val intent = Intent(applicationContext, ProfileActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                         */

                    }else{

                        //Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                    }
                }
            })
    }


    //Sqlite recordar usuario
    fun insertarSQLite(id:String, email: String){
        handler.insertDB(id, email)
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