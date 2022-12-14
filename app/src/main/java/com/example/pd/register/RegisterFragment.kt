package com.example.pd.register

import  android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
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
//        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(
            object: MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.options_menu, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return when (menuItem.itemId) {
                        R.id.settingsFragment -> {
                            val action = RegisterFragmentDirections.actionRegisterFragmentToSettingsFragment()
                            findNavController().navigate(action)
                            true
                        }
                        R.id.aboutFragment -> {
                            val action = RegisterFragmentDirections.actionRegisterFragmentToAboutFragment()
                            findNavController().navigate(action)
                            true
                        }
                        else -> false
                    }
                }
            }, viewLifecycleOwner, Lifecycle.State.RESUMED
        )

        binding.registerButton.setOnClickListener {
            addNewUser()
        }
        binding.cancelButton.setOnClickListener { goToLoginFragment() }
    }

    private fun isEntryValid(): Boolean {
        return viewModel.isEntryValid(
            username = binding.username.text.toString(),
            password = binding.password.text.toString(),
            confirmPassword = binding.confirmPassword.text.toString()
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
                    password = binding.password.text.toString()
                )
                Toast.makeText(requireActivity(), "Added ${binding.username.text}", Toast.LENGTH_LONG).show()
                val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
                findNavController().navigate(action)
            }
            else
                Toast.makeText(requireActivity(), "Password is not the same", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(requireActivity(), "Missing information", Toast.LENGTH_LONG).show()
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.options_menu, menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return NavigationUI.onNavDestinationSelected(item, requireView().findNavController())
//                || super.onOptionsItemSelected(item)
//    }

    private fun goToLoginFragment() {
        val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
        findNavController().navigate(action)
    }
}