package com.commandertrip.cupcakemock.models

import android.service.autofill.DateTransformation
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.ActivityNavigator
import kotlin.contracts.Returns

/**
 * This class handles the logic of how data will be manipulated for the travel planner.
 */
class TravelViewModel : ViewModel() {

    private val TAG = javaClass.toString()

    // All the properties of the flight plan
    // Backing properties allow us to only allow this class to modify specific data while also
    // allowing other classes to view the data
    private var _departureLocation = MutableLiveData<String>()
    val departureLocation: LiveData<String> = _departureLocation

    private var _departureDate = MutableLiveData<String>()
    val departureDate: LiveData<String> = _departureDate

    private var _arrivalLocation = MutableLiveData<String>()
    val arrivalLocation: LiveData<String> = _arrivalLocation

    private var _returnDate = MutableLiveData<String>()
    val returnDate: LiveData<String> = _returnDate

    private var _flightChoice = MutableLiveData<String>()
    val flightChoice: LiveData<String> = _flightChoice

    // true - round trip, false - one way
    private var _flightType = MutableLiveData<String>()
    val flightType: LiveData<String> = _flightType

    init {
        resetPlan()
    }

    fun resetPlan() {
        _departureLocation.value = ""
        _departureDate.value = ""
        _returnDate.value = ""
        _arrivalLocation.value = ""
        _flightType.value = ""
        _flightChoice.value = ""
    }

    // Setters   ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    /**
     * Information passed from HomeFragment
     */
    fun setDestination(destination: String) {
        _arrivalLocation.value = destination
    }

    /**
     * Information passed from the DateDetailsFragment
     *
     * true - round trip
     * false - one way trip
     */
    fun setFlightType(type: Boolean) {
        if (type) _flightType.value = "Round Trip"
        else _flightType.value = "One Way"
    }

    /**
     * Information passed from DateDetailsFragment
     */
    fun setDepartureDate(departureDate: String) {
        _departureDate.value = departureDate
    }

    /**
     * Information passed from DateDetailsFragment
     */
    fun setReturnDate(returnDate: String) {
        _returnDate.value = returnDate
    }

    /**
     * Sets the user's choice of flight
     */
    fun setFlightChoice(choice: String) {
        _flightChoice.value = choice
    }

    fun setDepartureLocation(loc: String) {
        _departureLocation.value = loc
        Log.d(TAG, loc)
    }

}