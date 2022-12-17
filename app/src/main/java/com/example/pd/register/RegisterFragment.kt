package com.example.pd.register

import  android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.pd.MainApplication
import com.example.pd.R
import com.example.pd.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private val viewModel: RegisterViewModel by activityViewModels {
        RegisterViewModelFactory(
            (activity?.application as MainApplication).database
                .userDao()
        )
    }

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.registerButton.setOnClickListener {
            addNewUser()
        }
        binding.cancelButton.setOnClickListener { goToLoginFragment() }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            username = binding.username.text.toString(),
            password = binding.password.text.toString(),
            confirmPassword = binding.confirmPassword.text.toString(),
            firstName = binding.firstname.text.toString(),
            surname = binding.surname.text.toString()
        )
    }

    private fun isPasswordSimilar(): Boolean {
        return viewModel.isPasswordSimilar(
            password = binding.password.text.toString(),
            confirmPassword = binding.confirmPassword.text.toString()
        )
    }

    private fun addNewUser() {

        if (isEntryValid())
            if (isPasswordSimilar()) {
                viewModel.addNewUser(
                    username = binding.username.text.toString(),
                    password = binding.password.text.toString(),
                    firstName = binding.firstname.text.toString(),
                    surname = binding.surname.text.toString()
                )
                binding.confirmpasswordlayout.error= null
                Toast.makeText(requireActivity(), "Added ${binding.username.text}", Toast.LENGTH_LONG).show()
                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                findNavController().navigate(action)
            }
            else {
                binding.confirmpasswordlayout.error = getString(R.string.password_unidentical)
                binding.passwordlayout.error= null
                binding.usernamelayout.error = null
            }
        else {
            var username = binding.username.text.toString()
            var password = binding.password.text.toString()
            var firstName = binding.firstname.text.toString()
            var surname = binding.surname.text.toString()

            if(firstName.isEmpty()){
                binding.firstnameLayout.error = getString(R.string.missing_info)
                binding.surnameLayout.error = null
                binding.usernamelayout.error = null
                binding.passwordlayout.error= null
                binding.confirmpasswordlayout.error = null
            } else if (surname.isEmpty()){
                binding.surnameLayout.error = getString(R.string.missing_info)
                binding.firstnameLayout.error = null
                binding.passwordlayout.error= null
                binding.usernamelayout.error = null
                binding.confirmpasswordlayout.error = null
            } else if(username.isEmpty()) {
                binding.usernamelayout.error = getString(R.string.missing_info)
                binding.firstnameLayout.error = null
                binding.surnameLayout.error= null
                binding.passwordlayout.error= null
                binding.confirmpasswordlayout.error = null
            } else if (password.isEmpty()) {
                binding.passwordlayout.error = getString(R.string.missing_info)
                binding.firstnameLayout.error = null
                binding.surnameLayout.error= null
                binding.usernamelayout.error = null
                binding.confirmpasswordlayout.error= null
            } else {
                binding.confirmpasswordlayout.error = getString(R.string.missing_info)
                binding.firstnameLayout.error = null
                binding.surnameLayout.error= null
                binding.passwordlayout.error= null
                binding.usernamelayout.error = null
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
                || super.onOptionsItemSelected(item)
    }

    private fun goToLoginFragment() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}