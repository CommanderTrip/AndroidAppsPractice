package com.commandertrip.cupcakemock.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.commandertrip.cupcakemock.R
import com.commandertrip.cupcakemock.databinding.FragmentSummaryBinding
import com.commandertrip.cupcakemock.models.TravelViewModel

/**
 * Final screen that shows all the details of the flight
 */
class SummaryFragment : Fragment() {

    // Save reference to the binding
    private var binding: FragmentSummaryBinding? = null

    // Get the shared viewModel
    private val sharedViewModel: TravelViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get the binding
        val fragmentBinding = FragmentSummaryBinding.inflate(inflater, container, false)
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

        // Set variables in the binding
        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            summaryFragment = this@SummaryFragment
        }
    }

    /**
     * Sends an intent to send all the data.
     */
    fun deliverDetails() {
        // Build the text of the message
        val details = getString(
            R.string.flight_details,
            sharedViewModel.departureLocation.value.toString(),
            sharedViewModel.arrivalLocation.value.toString(),
            sharedViewModel.departureDate.value.toString(),
            sharedViewModel.returnDate.value.toString(),
            sharedViewModel.flightChoice.value.toString()
            )

        // Build the intent
        val intent = Intent(Intent.ACTION_SEND)
            .setType("text/plain")
            .putExtra(Intent.EXTRA_SUBJECT, getString(R.string.flight_plan))
            .putExtra(Intent.EXTRA_TEXT, details)

        // Checks to see if there is even an app that could support our action
        if (activity?.packageManager?.resolveActivity(intent, 0) != null) {
            startActivity(intent)
        }
    }

    /**
     * Resets all the user's choices and navigates back home
     */
    fun cancelPlan() {
        sharedViewModel.resetPlan()
        findNavController().navigate(R.id.action_summaryFragment_to_homeFragment)
    }


}