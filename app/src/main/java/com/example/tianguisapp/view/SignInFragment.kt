package com.example.tianguisapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.tianguisapp.R
import com.example.tianguisapp.databinding.SignInFragmentBinding
import com.example.tianguisapp.model.SignInViewModel
import com.example.tianguisapp.utils.FragmentCommunicator

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class SignInFragment : Fragment() {

    private var _binding: SignInFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<SignInViewModel>()
    var isValid: Boolean = false
    private lateinit var communicator: FragmentCommunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = SignInFragmentBinding.inflate(inflater, container, false)
        communicator = requireActivity() as OnboardingActivity
        setupView()
        return binding.root

    }

    private fun setupView() {
        binding.registerTextView.setOnClickListener {
            communicator.showLoader(true)
            findNavController().navigate(R.id.action_signInFragment2_to_SecondFragment)
        }
        binding.loginButton.setOnClickListener {
            if (isValid) {
                requestLogin()
            } else {
                Toast.makeText(activity, "Ingreso invalido", Toast.LENGTH_SHORT).show()
            }
        }
        binding.emailTIET.addTextChangedListener {
            if (binding.emailTIET.text.toString().isEmpty()) {
                binding.emailTIL.error = "Por favor introduce un correo"
                isValid = false
            } else {
                isValid = true
            }
        }
        binding.passwordTIET.addTextChangedListener {
            if (binding.passwordTIET.text.toString().isEmpty()) {
                binding.passwordTIL.error = "Por favor introduce una contraseña"
                isValid = false
            } else {
                isValid = true
            }
        }
    }

    private fun requestLogin() {
        viewModel.requestSignIn(binding.emailTIET.text.toString(),
            binding.passwordTIET.text.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}