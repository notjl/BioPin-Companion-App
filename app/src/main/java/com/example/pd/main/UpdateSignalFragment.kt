package com.example.pd.main

import android.os.Bundle
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
import com.example.pd.database.models.Signal
import com.example.pd.databinding.FragmentUpdateSignalBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UpdateSignalFragment : Fragment() {
    private val viewModel: PdViewModel by activityViewModels {
        PdViewModelFactory(
            (activity?.application as MainApplication).database
                .signalDao()
        )
    }

    private val navigationArgs: UpdateSignalFragmentArgs by navArgs()

    private var _binding: FragmentUpdateSignalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpdateSignalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.signalId

        lifecycle.coroutineScope.launch {
            getSignal(id).collect {
                binding.apply {
                    signalEdit.setText(it.signal.toString())
                    signalOption.check(when(it.type) {
                        "brain" -> binding.brainOption.id
                        "muscle" -> binding.muscleOption.id
                        else -> binding.eyesOption.id
                    })
                }
            }
        }

        binding.submitButton.setOnClickListener {
            updateSignal(id)
        }

        binding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun updateSignal(id: Int) {
        if (isEntryValid()) {
            viewModel.updateExistingSignal(
                id = id,
                signal = binding.signalEdit.text.toString().toInt(),
                type = when(binding.signalOption.checkedRadioButtonId) {
                    binding.brainOption.id -> "brain"
                    binding.muscleOption.id -> "muscle"
                    binding.eyesOption.id -> "eyes"
                    else -> ""
                }
            )
            findNavController().navigateUp()
        }
        else
            Toast.makeText(requireActivity(), "Missing information", Toast.LENGTH_LONG).show()
    }

    private fun getSignal(id: Int): Flow<Signal> = viewModel.getSignal(id)

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            signal = binding.signalEdit.text.toString()
        )
    }
}