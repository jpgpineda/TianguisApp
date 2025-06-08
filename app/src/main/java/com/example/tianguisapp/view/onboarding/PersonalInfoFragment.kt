package com.example.tianguisapp.view.onboarding

import android.app.DatePickerDialog
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
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class PersonalInfoFragment : Fragment() {
    private var _binding: FragmentPersonalInfoBinding? = null
    private val binding get() = _binding!!
    private lateinit var communicator: FragmentCommunicator
    private val viewModel by viewModels<PersonalInfoViewModel>()
    val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this
        _binding = FragmentPersonalInfoBinding.inflate(inflater,container, false)
        communicator = requireActivity() as OnboardingActivity
        setupView()
        return binding.root
    }

    private fun setupView() {
        val userId = arguments?.let {
            PersonalInfoFragmentArgs.fromBundle(it).userId
        }
        binding.bornDateTiet.apply {
            isFocusable = false
            isClickable = true
        }
        binding.saveDataButton.setOnClickListener {
            Log.e("BOTON", "HA ENTRADO EN EL BOTON")
            if (userId != null) {
                viewModel.createUserInfo(userId,
                    binding.userFirstNameTiet.text.toString(),
                    binding.userLastNameTiet.text.toString(),
                    binding.userNameTiet.text.toString(),
                    format.parse(binding.bornDateTiet.text.toString()) ?: Date())
            }
        }
        binding.bornDateTiet.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, year, month, dayOfMonth ->
                // Ajusta el mes (+1 porque empieza en 0)
                val fechaSeleccionada = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year)
                binding.bornDateTiet.setText(fechaSeleccionada)
            }, year, month, day)

            datePicker.show()
        }
        setupObservers()
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