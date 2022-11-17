package com.commandertrip.cupcakemock.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.commandertrip.cupcakemock.R
import com.commandertrip.cupcakemock.databinding.FragmentHomeBinding
import com.commandertrip.cupcakemock.models.TravelViewModel
import java.util.*


/**
 * A simple [Fragment] subclass.
 * This is the first screen of the Cupcake Mock app.
 */
class HomeFragment : Fragment() {

    val TAG = javaClass.toString()

    // This binding exists only when the XML is made with data binding and the name of the class
    // will take after the name of this class
    private var binding: FragmentHomeBinding? = null

    // Create the shared viewModel and delegate it's properties
    private val sharedViewModel: TravelViewModel by activityViewModels()

    // Location service
    lateinit var locationByGps: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the view and save its reference
        val fragmentBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = fragmentBinding

        // LOCATION
        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        val gpsLocationListener: LocationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                locationByGps = location
            }

            @Deprecated("Deprecated in Java")
            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
        }

        if (hasGps) {
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    PackageManager.PERMISSION_GRANTED
                )
                getLoc(locationManager)
                return fragmentBinding.root
            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                5000,
                0F,
                gpsLocationListener
            )
        }
        getLoc(locationManager)
        return fragmentBinding.root

        /*
        The above could be done like:
        ```binding = FragmentHomeBinding.inflate(inflater, container, false)
        ```return binding!!.root
        But we want to reduce the amount of '!!' operators as possible to decrease the likelihood
        of crashes.
         */
    }

    @SuppressLint("MissingPermission")
    private fun getLoc(locationManager: LocationManager) {
        val lastKnownLocationByGps =
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        lastKnownLocationByGps?.let {
            locationByGps = lastKnownLocationByGps
        }


        val lat = lastKnownLocationByGps!!.latitude
        val lon = lastKnownLocationByGps.longitude
        val geo = Geocoder(activity, Locale.getDefault())
        val location = geo.getFromLocation(lat,lon,1)[0] as Address
        val departureLoc = location.locality + ", " + location.adminArea
        sharedViewModel.setDepartureLocation(departureLoc)
    }

    /**
     * Called immediately after [.onCreateView]
     * has returned, but before any saved state has been restored in to the view.
     * This gives subclasses a chance to initialize themselves once
     * they know their view hierarchy has been completely created.  The fragment's
     * view hierarchy is not however attached to its parent at this point.
     * @param view The View returned by [.onCreateView].
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Save this for navigation
        binding?.homeFragment = this
        binding?.viewModel = sharedViewModel
    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after [.onStop] and before [.onDetach].
     */
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    /**
     * This function is called when the user clicks one of the buttons to start the flight plan
     */
    fun startPlan(destination: String) {
        Log.d(TAG, "Starting plan with: $destination")

        // Set the destination in the view model
        sharedViewModel.setDestination(destination)

        // Navigate to the next screen
        findNavController().navigate(R.id.action_homeFragment_to_dateDetailsFragment)
    }

}