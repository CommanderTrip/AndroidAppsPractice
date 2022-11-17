package com.commandertrip.cupcakemock.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.commandertrip.cupcakemock.R
import com.commandertrip.cupcakemock.databinding.FragmentFlightDetailsBinding
import com.commandertrip.cupcakemock.models.TravelViewModel


/**
 * Provides a list of options for the user to choose what flight they want. From
 * most expensive to cheap.
 * See [fragment_flight_details.xml]
 */
class FlightDetailsFragment : Fragment() {

    // Saving a binding reference
    private var binding: FragmentFlightDetailsBinding? = null

    // Get the shared viewModel
    private val sharedViewModel: TravelViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragmentBinding = FragmentFlightDetailsBinding.inflate(inflater, container, false)
        binding = fragmentBinding
        return fragmentBinding.root
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

        // Attach our bindings
        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            flightDetailsFragment = this@FlightDetailsFragment
        }
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
     * Navigates user to the Flight summary screen
     */
    fun goToFlightSummary() {
        findNavController().navigate(R.id.action_flightDetailsFragment_to_summaryFragment)
    }

    /**
     * Resets all the user's choices and navigates back home
     */
    fun cancelPlan() {
        sharedViewModel.resetPlan()
        findNavController().navigate(R.id.action_flightDetailsFragment_to_homeFragment)
    }
}