package com.example.pd.main.createupdate

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.pd.MainApplication
import com.example.pd.database.models.Signal
import com.example.pd.databinding.FragmentTypeBinding
import com.example.pd.main.PdViewModel
import com.example.pd.main.PdViewModelFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TypeFragment : Fragment() {
    private val pdViewModel: PdViewModel by activityViewModels {
        PdViewModelFactory(
            (activity?.application as MainApplication).database
                .signalDao()
        )
    }

    private val navigationArgs: TypeFragmentArgs by navArgs()

    private var _binding: FragmentTypeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTypeBinding.inflate(inflater, container, false)
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
                            signalOption.check(when(it.type) {
                                "brain" -> binding.brainOption.id
                                "muscle" -> binding.muscleOption.id
                                else -> binding.eyesOption.id
                            })
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
        pdViewModel.setType(when(binding.signalOption.checkedRadioButtonId) {
            binding.brainOption.id -> "brain"
            binding.muscleOption.id -> "muscle"
            binding.eyesOption.id -> "eyes"
            else -> ""
        })
        Log.d("TypeFragment", "Hertz: ${pdViewModel.hertz.value.toString()}, Type: ${pdViewModel.type.value}")
        if (id != 0) {
            val action = TypeFragmentDirections.actionTypeFragmentToDirectionFragment(id)
            findNavController().navigate(action)
        }
        else {
            val action = TypeFragmentDirections.actionTypeFragmentToDirectionFragment()
            findNavController().navigate(action)
        }
    }

    private fun getSignal(id: Int): Flow<Signal> = pdViewModel.getSignal(id)
}