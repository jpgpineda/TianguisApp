package com.example.tianguisapp.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tianguisapp.databinding.ProductsFragmentBinding
import com.example.tianguisapp.model.Product
import com.example.tianguisapp.utils.FragmentCommunicator
import com.example.tianguisapp.view.home.adapters.ProductAdapter
import com.example.tianguisapp.view.home.viewModel.ProductsViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProductsFragment : Fragment() {

    private var _binding: ProductsFragmentBinding? = null
    private val viewModel by viewModels<ProductsViewModel>()
    private lateinit var communicator: FragmentCommunicator
    private lateinit var productsAdapter: ProductAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ProductsFragmentBinding.inflate(inflater, container, false)
        communicator = requireActivity() as HomeActivity
        setupView()
        return binding.root
    }

    fun setupView() {
        setupObservers()
        productsAdapter = ProductAdapter(
            mutableListOf()
        )
        binding.productsRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = productsAdapter
        }
        viewModel.fetchAllProducts()
    }

    fun setupObservers() {
        viewModel.loaderState.observe(viewLifecycleOwner) {
            communicator.showLoader(it)
        }
        viewModel.productInfo.observe(viewLifecycleOwner) { products ->
            updateUI(products)
        }
    }

    fun updateUI(products: List<Product>) {
        productsAdapter.add(products)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}