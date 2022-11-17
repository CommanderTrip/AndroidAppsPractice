package com.commandertrip.cupcakemock.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.commandertrip.cupcakemock.R
import com.commandertrip.cupcakemock.databinding.FragmentDateDetailsBinding
import com.commandertrip.cupcakemock.models.TravelViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [DateDetails.newInstance] factory method to
 * create an instance of this fragment.
 */
class DateDetailsFragment : Fragment() {

    // Save the binding reference for navigation
    private var binding: FragmentDateDetailsBinding? = null

    // Save the view model for saving data
    private val sharedViewModel: TravelViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentBinding = FragmentDateDetailsBinding.inflate(inflater, container, false)
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

        // Save this reference for navigation and data binding
        // This correlates with the XML data variables
        binding?.apply {
            viewModel = sharedViewModel
            lifecycleOwner = viewLifecycleOwner
            dateDetailsFragment = this@DateDetailsFragment
        }

        // TODO: Build out the date verification
        // Listen for changes on the TextEdit Field - returnDateEdit
        binding?.returnDateEdit?.doOnTextChanged { text, _, _, _ ->
            sharedViewModel.setReturnDate(text.toString())
        }

        // Listen for changes on the TextEdit Field - departureDateEdit
        binding?.departureDateEdit?.doOnTextChanged { text, _, _, _ ->
            sharedViewModel.setDepartureDate(text.toString())
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
     * Navigates the user to the Flight Details Screen
     */
    fun goToFlightDetails() {
        // navigate to next screen
        findNavController().navigate(R.id.action_dateDetailsFragment_to_flightDetailsFragment)
    }

    /**
     * Resets all the user's choices and navigates back home
     */
    fun cancelPlan() {
        sharedViewModel.resetPlan()
        findNavController().navigate(R.id.action_dateDetailsFragment_to_homeFragment)
    }
}