package com.example.tianguisapp.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tianguisapp.R
import com.example.tianguisapp.databinding.FragmentProfileBinding
import com.example.tianguisapp.databinding.ProductsFragmentBinding
import com.example.tianguisapp.model.User
import com.example.tianguisapp.utils.FragmentCommunicator
import com.example.tianguisapp.view.home.viewModel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this
        _binding = FragmentProfileBinding.inflate(inflater,container, false)
        communicator = requireActivity() as HomeActivity
        setupView()
        return binding.root
    }

    private fun setupView() {
        setupObservers()
        viewModel.getUserInfo()
    }

    private fun setupObservers() {
        viewModel.userInfo.observe(viewLifecycleOwner) { user ->
            updateUI(user)
        }
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }
    }

    private fun updateUI(user: User) {
        binding?.apply {
            userNameLabel.text = user.name + " " + user.lastName
            userNameLabel.text = user.userName
            userBornDateLabel.text = user.bornDate.toString()
        }
    }

}