package com.example.pd.pd.crud

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
import com.example.pd.databinding.FragmentSignalBinding
import com.example.pd.pd.crud.adapter.SignalAdapter
import com.example.pd.pd.crudmo.PdViewModel
import com.example.pd.pd.crudmo.PdViewModelFactory
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
    ): View? {
        _binding = FragmentSignalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        val signalAdapter: SignalAdapter = SignalAdapter()
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
}