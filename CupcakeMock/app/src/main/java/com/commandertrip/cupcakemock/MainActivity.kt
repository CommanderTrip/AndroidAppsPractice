package com.commandertrip.cupcakemock

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController

/**
 * Entry way into the application.
 * Using the [nav_graph.xml] for navigation.
 */
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    // See @navigation/nav_graph.xml for navigation
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up the nav controller
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Show a title in the app bar based off of the destination's label,
        // and display the Up button whenever you're not on a top-level destination.
        setupActionBarWithNavController(navController)
    }

    /**
     * This method is called whenever the user chooses to navigate Up within your application's
     * activity hierarchy from the action bar.
     *
     * @return true if Up navigation completed successfully and this Activity was finished,
     * false otherwise.
     */
    override fun onSupportNavigateUp(): Boolean {
        return  navController.navigateUp() ||super.onSupportNavigateUp()
    }
}