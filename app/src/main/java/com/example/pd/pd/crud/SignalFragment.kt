package com.example.pd.pd.crud

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pd.databinding.FragmentSignalBinding

class SignalFragment : Fragment() {

    private var _binding: FragmentSignalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.createButton.setOnClickListener { goToCreateSignalFragment() }
    }

    private fun goToCreateSignalFragment() {
        val action = SignalFragmentDirections.actionSignalFragmentToCreateSignalFragment()
        findNavController().navigate(action)
    }
}