package com.caanvi.comensal_app_mobile.Login.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.caanvi.comensal_app_mobile.Login.SQLite.DatabaseHelper
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.ActivityMenuactiBinding


class MenuActi : AppCompatActivity() {

    lateinit var handler: DatabaseHelper
    private lateinit var binding: ActivityMenuactiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menuacti)

        binding = ActivityMenuactiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handler = DatabaseHelper(this)

        //Cambiar de Vista de un activitie a otro
        binding.btnRegistro.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        //Cambiar de Vista de un activitie a otro
        binding.btnRestaurantes.setOnClickListener {
            val intent = Intent(applicationContext, VerRestaurantes::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        //Cambiar de Vista Buscar Restaurantes
        binding.btnBuscar.setOnClickListener {
            val intent = Intent(applicationContext, searchRestaurant::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        //Cambiar de Vista de un activitie a otro
        binding.btnAjustes.setOnClickListener {
            val intent = Intent(applicationContext, Ajustes::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        //Cambiar de Vista de un activitie a otro
        binding.btnFragments.setOnClickListener {
            val intent = Intent(applicationContext, PrincipalFragments::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }


    }












}