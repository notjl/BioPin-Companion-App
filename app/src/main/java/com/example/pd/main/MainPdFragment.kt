package com.example.pd.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.pd.databinding.FragmentMainPdBinding

class MainPdFragment : Fragment() {

    private var _binding: FragmentMainPdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainPdBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signalButton.setOnClickListener { goToSignalFragment() }
        binding.apiButton.setOnClickListener { goToApiFragment() }
    }

    private fun goToSignalFragment() {
        val action = MainPdFragmentDirections.actionMainPdFragmentToSignalFragment()
        findNavController().navigate(action)
        Toast.makeText(requireActivity(), "Go to Signal TODO", Toast.LENGTH_LONG).show()
    }

    private fun goToApiFragment() {
        Toast.makeText(requireActivity(), "Go to API TODO", Toast.LENGTH_LONG).show()
    }
}