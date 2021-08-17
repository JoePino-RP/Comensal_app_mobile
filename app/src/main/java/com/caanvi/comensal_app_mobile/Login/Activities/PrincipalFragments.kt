package com.caanvi.comensal_app_mobile.Login.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.caanvi.comensal_app_mobile.Login.Storage.usuarioData
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.ActivityPrincipalFragmentsBinding
import com.caanvi.comensal_app_mobile.databinding.FragmentsIniciarsesionDialogBinding


class PrincipalFragments : AppCompatActivity() {

    private lateinit var homeBtn: ImageButton
    private lateinit var addBtn: ImageButton
    private lateinit var notiBtn: ImageButton
    private lateinit var searchBtn: ImageButton
    private lateinit var profileBtn: ImageButton

    private lateinit var mViewPager: ViewPager
    private lateinit var mPagerViewAdapter: PagerViewAdapter

    private lateinit var binding: ActivityPrincipalFragmentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_fragments)


        // init views
        mViewPager = findViewById(R.id.mViewPager)
        homeBtn = findViewById(R.id.homeBtn)
        addBtn = findViewById(R.id.addBtn)
        profileBtn = findViewById(R.id.profileBtn)
        searchBtn = findViewById(R.id.searchBtn)
        notiBtn = findViewById(R.id.notiBtn)


        //onclick listner

        homeBtn.setOnClickListener {
            mViewPager.currentItem = 0

        }
        notiBtn.setOnClickListener {
            //mViewPager.currentItem = 1

            if (!verificar()){

                //Toast.makeText(applicationContext, "valio", Toast.LENGTH_LONG).show()
                alertDialog()
            }else {
                mViewPager.currentItem = 1
            }

        }

        searchBtn.setOnClickListener {

            mViewPager.currentItem = 2

        }

        addBtn.setOnClickListener {
            //mViewPager.currentItem = 3
            //mViewPager.currentItem = 3
            if (!verificar()){


                alertDialog()
            }else {
                mViewPager.currentItem = 3
            }

        }







        profileBtn.setOnClickListener {
            //mViewPager.currentItem = 4

            if (!verificar()){


                alertDialog()
            }else {
                mViewPager.currentItem = 4
            }

        }




        mPagerViewAdapter = PagerViewAdapter(supportFragmentManager)
        mViewPager.adapter = mPagerViewAdapter
        mViewPager.offscreenPageLimit = 5



        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                changeTabs(position)
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })




        mViewPager.currentItem = 0
        homeBtn.setImageResource(R.drawable.ic_home_pink)





    }

    private fun changeTabs(position: Int) {


        if (position == 0) {
            homeBtn.setImageResource(R.drawable.ic_home_pink)
            notiBtn.setImageResource(R.drawable.ic_notifications_blck)
            searchBtn.setImageResource(R.drawable.ic_search_black)
            addBtn.setImageResource(R.drawable.ic_add_black)

            profileBtn.setImageResource(R.drawable.ic_person_outline_)




        }

        if (position == 1) {
            homeBtn.setImageResource(R.drawable.ic_home_black_)
            notiBtn.setImageResource(R.drawable.ic_notifications_fill)
            searchBtn.setImageResource(R.drawable.ic_search_black)
            addBtn.setImageResource(R.drawable.ic_add_black)

            profileBtn.setImageResource(R.drawable.ic_person_outline_)

        }

        if (position == 2) {
            homeBtn.setImageResource(R.drawable.ic_home_black_)
            notiBtn.setImageResource(R.drawable.ic_notifications_blck)
            searchBtn.setImageResource(R.drawable.ic_search_pink)
            addBtn.setImageResource(R.drawable.ic_add_black)

            profileBtn.setImageResource(R.drawable.ic_person_outline_)




        }
        if (position == 3) {
            homeBtn.setImageResource(R.drawable.ic_home_black_)
            notiBtn.setImageResource(R.drawable.ic_notifications_blck)
            searchBtn.setImageResource(R.drawable.ic_search_black)
            addBtn.setImageResource(R.drawable.ic_add_pink)

            profileBtn.setImageResource(R.drawable.ic_person_outline_)

        }

        if (position == 4) {
            homeBtn.setImageResource(R.drawable.ic_home_black_)
            notiBtn.setImageResource(R.drawable.ic_notifications_blck)
            searchBtn.setImageResource(R.drawable.ic_search_black)
            addBtn.setImageResource(R.drawable.ic_add_black)

            profileBtn.setImageResource(R.drawable.ic_person_pink_fill)

        }



    }


    fun alertDialog (){
        ///////////////ALERT DIALOG////////////////////

        val view = View.inflate(this@PrincipalFragments, R.layout.fragments_iniciarsesion_dialog, null)
        val binding = FragmentsIniciarsesionDialogBinding.bind(view)


        val builder = AlertDialog.Builder(this@PrincipalFragments)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false) //Linea donde asi ahagas clicc afuera del alert dialog no se cerrara, solo con la equisx(x)


        binding.imageViewClose.setOnClickListener(){
            dialog.dismiss()

/*
            //Cambio de Pantalla
            val intent = Intent(applicationContext, PrincipalFragments::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
 */



        }


        binding.btnIniciarSesion.setOnClickListener(){
            dialog.dismiss()
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)

        }


        ///////////////////////////////////////////////
    }


    fun verificar () : Boolean {

        if (usuarioData.idGeneral == ""){
            return false
        }else {
            return true
        }
    }
}