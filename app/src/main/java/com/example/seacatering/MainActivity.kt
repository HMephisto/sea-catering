package com.example.seacatering

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.seacatering.ui.ContactFragment
import com.example.seacatering.ui.menu.MenuFragment
import com.example.seacatering.ui.ProfileFragment
import com.example.seacatering.ui.SubscriptionFragment
import com.example.seacatering.ui.home.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit  var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(HomeFragment())
        bottomNav =findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener{
            when (it.itemId){
                R.id.menuBottomNavigationHome -> {
                    loadFragment(HomeFragment())
                    true
                }
                R.id.menuBottomNavigationMealPlan -> {
                    loadFragment(MenuFragment())
                    true
                }
                R.id.menuBottomNavigationSubscription -> {
                    loadFragment(SubscriptionFragment())
                    true
                }
                R.id.menuBottomNavigationContact -> {
                    loadFragment(ContactFragment())
                    true
                }
                R.id.menuBottomNavigationProfile -> {
                    loadFragment(ProfileFragment())
                    true
                }
                else -> false
            }
        }
    }


    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayoutMainActivity,fragment)
        transaction.commit()
    }
}