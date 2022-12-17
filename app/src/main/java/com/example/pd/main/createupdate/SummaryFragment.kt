package com.example.pd.main.createupdate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pd.MainApplication
import com.example.pd.R
import com.example.pd.databinding.FragmentSummaryBinding
import com.example.pd.main.PdViewModel
import com.example.pd.main.PdViewModelFactory

class SummaryFragment : Fragment() {

    private val pdViewModel: PdViewModel by activityViewModels {
        PdViewModelFactory(
            (activity?.application as MainApplication).database
                .signalDao()
        )
    }

    private val navigationArgs: SummaryFragmentArgs by navArgs()

    private var _binding: FragmentSummaryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.signalId

        binding.apply {
            viewModel = pdViewModel
            lifecycleOwner = viewLifecycleOwner
            hertz.text = pdViewModel.hertz.value.toString() + "Hz"
            when (pdViewModel.type.value) {
                "brain" -> {
                    typeSummary.setImageResource(R.drawable.eegxicon)
                }
                "muscle" -> {
                    typeSummary.setImageResource(R.drawable.emgxicon)
                }
                "eyes" -> {
                    typeSummary.setImageResource(R.drawable.eogxicon)
                }
            }
            when (pdViewModel.direction.value) {
                "right" -> {
                    directionSummary.setImageResource(R.drawable.rightx16)
                }
                "left" -> {
                    directionSummary.setImageResource(R.drawable.leftx16)
                }
                "backward" -> {
                    directionSummary.setImageResource(R.drawable.downx16)
                }
                "forward" -> {
                    directionSummary.setImageResource(R.drawable.upx16)
                }
            }
        }

        binding.backButton.setOnClickListener { findNavController().navigateUp() }
        binding.submitButton.setOnClickListener { createOrUpdateSignal(id) }
    }

    private fun createOrUpdateSignal(id: Int) {
        if (id != 0) {
            pdViewModel.updateExistingSignal(
                id = id,
                signal = pdViewModel.hertz.value!!,
                type = pdViewModel.type.value!!,
                direction = pdViewModel.direction.value!!
            )
        }
        else {
            pdViewModel.addNewSignal(
                signal = pdViewModel.hertz.value!!,
                type = pdViewModel.type.value!!,
                direction = pdViewModel.direction.value!!
            )
        }
        val action = SummaryFragmentDirections.actionSummaryFragmentToSignalFragment()
        findNavController().navigate(action)
    }
}