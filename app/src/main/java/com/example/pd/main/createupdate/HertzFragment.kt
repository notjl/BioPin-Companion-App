package com.example.pd.main.createupdate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pd.MainApplication
import com.example.pd.R
import com.example.pd.database.models.Signal
import com.example.pd.databinding.FragmentHertzBinding
import com.example.pd.main.PdViewModel
import com.example.pd.main.PdViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class HertzFragment : Fragment() {
    private val pdViewModel: PdViewModel by activityViewModels {
        PdViewModelFactory(
            (activity?.application as MainApplication).database
                .signalDao()
        )
    }

    private val navigationArgs: HertzFragmentArgs by navArgs()

    private var _binding: FragmentHertzBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHertzBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var id: Int

        navigationArgs.let { args ->
            if (args.signalId != 0) {
                id = args.signalId
                lifecycle.coroutineScope.launch {
                    getSignal(args.signalId).collect {
                        binding.apply {
                            hertz.setText(it.signal.toString())
                        }
                    }
                }
            }
            else
                id = args.signalId
        }

        binding.backButton.setOnClickListener { findNavController().navigateUp() }
        binding.nextButton.setOnClickListener { nextPage(id) }
    }

    private fun nextPage(id: Int) {
        if (isEntryValid()) {
            pdViewModel.setHertz(binding.hertz.text.toString().toInt())
            Log.d("nextPageHertz", id.toString())
            if (id != 0) {
                val action = HertzFragmentDirections.actionHertzFragmentToTypeFragment(id)
                findNavController().navigate(action)
            }
            else {
                val action = HertzFragmentDirections.actionHertzFragmentToTypeFragment()
                findNavController().navigate(action)
            }
        }
        else {
            //Toast.makeText(requireActivity(), "Nope next", Toast.LENGTH_LONG).show()
            binding.hertzLayout.error = getString(R.string.missing_info)
        }
    }

    private fun isEntryValid(): Boolean {
        return pdViewModel.isEntryValid(
            signal = binding.hertz.text.toString()
        )
    }

    private fun getSignal(id: Int): Flow<Signal> = pdViewModel.getSignal(id)
}