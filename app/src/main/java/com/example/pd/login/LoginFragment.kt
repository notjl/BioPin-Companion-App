package com.example.pd.login

import android.os.Bundle
import android.text.Editable
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.pd.MainApplication
import com.example.pd.R
import com.example.pd.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by activityViewModels {
        LoginViewModelFactory(
            (activity?.application as MainApplication).database
                .userDao()
        )
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener { login() }
        binding.registerButton.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            username = binding.username.text.toString(),
            password = binding.password.text.toString()
        )
    }

    private fun userExist(): Boolean {
        return viewModel.userExist(
            username = binding.username.text.toString(),
            password = binding.password.text.toString()
        )
    }



    private fun login() {
        if (isEntryValid())
            if (userExist()) {
                Toast.makeText(
                    requireActivity(),
                    "Login Success! Welcome ${binding.username.text}",
                    Toast.LENGTH_LONG
                ).show()
                val action = LoginFragmentDirections.actionLoginFragmentToMainPdFragment()
                findNavController().navigate(action)
            } else {
                binding.usernamelayout.error = null
                binding.passwordlayout.error = getString(R.string.error_password)
            }
        else {
            var username = binding.username.text.toString()
            if (username.isEmpty()) {
                binding.usernamelayout.error = getString(R.string.missing_info)
                binding.passwordlayout.error = null
            } else {
                binding.passwordlayout.error = getString(R.string.missing_info)
                binding.usernamelayout.error = null
            }
        }
    }
}