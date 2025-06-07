package com.example.tianguisapp.view.onboarding

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tianguisapp.R
import com.example.tianguisapp.databinding.FragmentPersonalInfoBinding
import com.example.tianguisapp.utils.FragmentCommunicator
import com.example.tianguisapp.view.home.HomeActivity
import com.example.tianguisapp.viewModel.PersonalInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class PersonalInfoFragment : Fragment() {
    private var _binding: FragmentPersonalInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<PersonalInfoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this
        _binding = FragmentPersonalInfoBinding.inflate(inflater,container, false)
        communicator = requireActivity() as OnboardingActivity
        setupView()
        return inflater.inflate(R.layout.fragment_personal_info, container, false)
    }

    private fun setupView() {
        setupObservers()
        val userId = arguments?.let {
            PersonalInfoFragmentArgs.fromBundle(it).userId
        }
        binding.saveDataButton.setOnClickListener {
            Log.e("BOTON", "HA ENTRADO EN EL BOTON")
            if (userId != null) {
                viewModel.createUserInfo(userId,
                    binding.userFirstNameTiet.text.toString(),
                    binding.userLastNameTiet.text.toString(),
                    binding.userNameTiet.text.toString(),
                    Date())
            }
        }
    }

    private fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) {
            communicator.showLoader(it)
        }
        viewModel.operationSuccess.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                val intent = Intent(activity, HomeActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }
        }
    }
}