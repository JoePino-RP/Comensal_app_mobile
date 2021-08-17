package com.caanvi.comensal_app_mobile.Login.Activities

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.caanvi.comensal_app_mobile.Login.fragments.HomeFragment
import www.sanju.customtabbar.Fragments.*

internal class PagerViewAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                HomeFragment()
            }
            1 -> {
                //3SearchFragment()
                NotificationFragment()
            }
            2 -> {
                //4AddFragment()
                SearchFragment()
            }
            3 -> {
                //2NotificationFragment()
                AddFragment()
            }
            4 -> {
                ProfileFragment()
            }
            else -> HomeFragment()
        }
    }

    override fun getCount(): Int {

        return 5
    }

}

