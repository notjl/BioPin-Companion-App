package com.example.pd.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pd.MainApplication
import com.example.pd.database.models.Signal
import com.example.pd.databinding.FragmentSignalBinding
import com.example.pd.main.adapter.SignalAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class SignalFragment : Fragment() {
    private val viewModel: PdViewModel by activityViewModels {
        PdViewModelFactory(
            (activity?.application as MainApplication).database
                .signalDao()
        )
    }

    private var _binding: FragmentSignalBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val signalAdapter = SignalAdapter(
            {
                val action = SignalFragmentDirections.actionSignalFragmentToUpdateSignalFragment(it.id)
                this.findNavController().navigate(action)
            },
            {
                signal: Signal -> showConfirmationDialog(signal)
            }
        )
        recyclerView.adapter = signalAdapter

        lifecycle.coroutineScope.launch {
            viewModel.getAllSignal().collect() {
                signalAdapter.submitList(it)
            }
        }
        binding.createButton.setOnClickListener { goToCreateSignalFragment() }
    }

    private fun goToCreateSignalFragment() {
        val action = SignalFragmentDirections.actionSignalFragmentToCreateSignalFragment()
        findNavController().navigate(action)
    }

    private fun showConfirmationDialog(signal: Signal) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Are you sure you want to delete?")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { _, _ -> deleteSignal(signal) }
            .show()
    }

    private fun deleteSignal(signal: Signal) {
        viewModel.deleteSignal(signal)
    }
}