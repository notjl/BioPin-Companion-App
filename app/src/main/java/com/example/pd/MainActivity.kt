package com.example.pd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.pd.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        drawerLayout = binding.drawerLayout

        val navView : NavigationView = findViewById(R.id.navView)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(binding.navView, navController)

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.signalFragment -> navController.navigate(R.id.signalFragment)
                R.id.signalApiFragment -> navController.navigate(R.id.signalApiFragment)
                R.id.mainPdFragment -> navController.navigate(R.id.mainPdFragment)
                R.id.faqFragment -> navController.navigate(R.id.faqFragment)
                R.id.aboutFragment -> navController.navigate(R.id.aboutFragment)
                R.id.logout -> finish()
            }
            true
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation_view)

        bottomNavigationView.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.menu_signal -> navController.navigate(R.id.signalFragment)
                R.id.menu_api -> navController.navigate(R.id.signalApiFragment)
                R.id.menu_pd -> navController.navigate(R.id.mainPdFragment)
                R.id.menu_faq -> navController.navigate(R.id.faqFragment)
                R.id.menu_about -> navController.navigate(R.id.aboutFragment)
            }
            true
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return NavigationUI.navigateUp(navController, drawerLayout)
    }

}