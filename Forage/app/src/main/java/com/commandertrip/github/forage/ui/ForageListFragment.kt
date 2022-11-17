package com.commandertrip.github.forage.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.commandertrip.github.forage.R
import com.commandertrip.github.forage.ui.viewmodel.ForageViewModel

class ForageListFragment : Fragment() {

    companion object {
        fun newInstance() = ForageListFragment()
    }

    private lateinit var viewModel: ForageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forage_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ForageViewModel::class.java)
        // TODO: Use the ViewModel
    }

}