package com.example.pd.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pd.MainActivity
import com.example.pd.MainApplication
import com.example.pd.NonavActivity
import com.example.pd.databinding.FragmentMainPdBinding
import com.example.pd.login.LoginViewModel
import com.example.pd.login.LoginViewModelFactory

class MainPdFragment : Fragment() {

    private val sharedViewModel: LoginViewModel by activityViewModels()

    private var _binding: FragmentMainPdBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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

    }

    private fun goToApiFragment() {
        val action = MainPdFragmentDirections.actionMainPdFragmentToSignalApiFragment()
        findNavController().navigate(action)

    }


}