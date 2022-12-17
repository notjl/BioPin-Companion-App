package com.example.pd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.pd.databinding.ActivityMainBinding
import com.example.pd.databinding.ActivityNonavBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class NonavActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityNonavBinding = DataBindingUtil.setContentView(this, R.layout.activity_nonav)
        val navController = this.findNavController(R.id.nonavHostFragment)
        NavigationUI.setupWithNavController(binding.navView, navController)
    }
}