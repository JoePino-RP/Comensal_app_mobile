package com.caanvi.comensal_app_mobile.Login.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.caanvi.comensal_app_mobile.Login.Api.RetrofitClient
import com.caanvi.comensal_app_mobile.Login.Modals.loginResponse
import com.caanvi.comensal_app_mobile.Login.SQLite.DatabaseHelper
import com.caanvi.comensal_app_mobile.Login.Storage.usuarioData
import com.caanvi.comensal_app_mobile.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var handler: DatabaseHelper

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = DatabaseHelper(this)

        buscarSQLite()


        binding.buttonLogin.setOnClickListener {
            var email: String = binding.emailTxt.text.toString()
            var password:String = binding.passwordTxt.text.toString()

            login(email.trim(), password)
        }

        binding.buttonForgot.setOnClickListener {
            val intent = Intent(applicationContext, forgotPassword::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.goRegistro.setOnClickListener {
            val intent = Intent(applicationContext, Registrarse::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.btnInvitado.setOnClickListener {
            val intent = Intent(applicationContext, PrincipalFragments::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }


    }



    fun login(email: String, password:String){

        RetrofitClient.instance.userLogin(email, password)
            .enqueue(object: retrofit2.Callback<loginResponse> {

                override fun onFailure(call: retrofit2.Call<loginResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: retrofit2.Call<loginResponse>, response: retrofit2.Response<loginResponse>) {
                    if(response.body()?.conecto!!){
                        usuarioData.idGeneral = response.body()?.user?.id!!
                        usuarioData.emailGeneral = response.body()?.user?.email!!

                        //SQLite lo recordamos
                        insertarSQLite(usuarioData.idGeneral ,usuarioData.emailGeneral)

                        //Mensaje de Inicio de Session Correcto
                        Toast.makeText(applicationContext, "Log In Correcto", Toast.LENGTH_LONG).show()

                        //Cambio de Pantalla
                        /*
                        val intent = Intent(applicationContext, ProfileActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                         */

                        //Cambiar de Vista de un activitie a otro
                            val intent = Intent(applicationContext, PrincipalFragments::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()

                    }else{

                        //Toast.makeText(applicationContext, response.body()?.message, Toast.LENGTH_LONG).show()
                        Toast.makeText(applicationContext, "Log In InCorrecto", Toast.LENGTH_LONG).show()
                    }

                }
            })
    }



    //Funciones para SQLite
    fun buscarSQLite(){
        if (handler.selectDB()){
            Toast.makeText(applicationContext,  "Usuario Recordado", Toast.LENGTH_LONG).show()

            //val intent = Intent(applicationContext, ProfileActivity::class.java)
            val intent = Intent(applicationContext, PrincipalFragments ::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }else{
            //Toast.makeText(applicationContext, "No recuerdo a ningun usuario", Toast.LENGTH_LONG).show()
        }
    }

    fun insertarSQLite(id:String, email: String){
        handler.insertDB(id, email)
    }

    //Funcion para regresar a la actividad anterior y que no se cierre la app presionando el boton hacia atras
    override fun onBackPressed() {
        super.onBackPressed()
        //codigo adicional

    }
}