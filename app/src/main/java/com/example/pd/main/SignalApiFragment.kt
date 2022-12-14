package com.example.pd.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.pd.databinding.FragmentSignalApiBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Description

class SignalApiFragment : Fragment() {

    private val viewModel: ApiViewModel by viewModels()

    private var _binding: FragmentSignalApiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignalApiBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this.viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.graphView.animateXY(2000, 2000, Easing.EaseInCubic)
        val desc = Description()
        desc.text = "Signal fetched!"
        binding.graphView.description = desc
    }
}