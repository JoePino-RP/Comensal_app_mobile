package com.caanvi.comensal_app_mobile.Login.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.caanvi.comensal_app_mobile.Login.fragments.DashboardFragment
import com.caanvi.comensal_app_mobile.Login.fragments.LoginFragment
import com.caanvi.comensal_app_mobile.Login.fragments.SettingsFragment
import com.caanvi.comensal_app_mobile.R
import com.caanvi.comensal_app_mobile.databinding.ActivityPrincipalFragmentsBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class PrincipalFragments : AppCompatActivity() {

    private val dashboardFragment = DashboardFragment()
    private val loginFragment = LoginFragment()
    private val settingsFragment = SettingsFragment()

    private lateinit var binding: ActivityPrincipalFragmentsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal_fragments)

        val bottom_nav : BottomNavigationView = findViewById(R.id.bottom_navigation)



        replaceFragment(dashboardFragment)



        bottom_nav.setOnNavigationItemReselectedListener {
            when (it.itemId){
                R.id.ic_dashboard -> replaceFragment(dashboardFragment)
                R.id.ic_settings -> replaceFragment(settingsFragment)
                R.id.ic_info -> replaceFragment(loginFragment)

            }
            true
        }





    }


    private fun replaceFragment(fragment: Fragment){
        if (fragment !=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}