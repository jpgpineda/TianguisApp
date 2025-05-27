package com.example.tianguisapp.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.tianguisapp.databinding.ProductDetailFragmentBinding
import com.example.tianguisapp.model.Product
import com.example.tianguisapp.utils.FragmentCommunicator
import com.example.tianguisapp.view.home.viewModel.ProductDetailViewModel

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ProductDetailFragment : Fragment() {

    private var _binding: ProductDetailFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProductDetailViewModel>()
    private lateinit var communicator: FragmentCommunicator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = ProductDetailFragmentBinding.inflate(inflater, container, false)
        communicator = requireActivity() as HomeActivity
        setupView()
        return binding.root
    }

    fun setupView() {
        val productId = arguments?.let {
            ProductDetailFragmentArgs.fromBundle(it).productId
        }
        viewModel.getProductDetail(productId ?: "")
        setupObservers()
    }

    fun setupObservers() {
        viewModel.productInfo.observe(viewLifecycleOwner) { product ->
            showProductInfo(product)
        }
        viewModel.loaderState.observe(viewLifecycleOwner) { loaderState ->
            communicator.showLoader(loaderState)
        }
    }

    fun showProductInfo(product: Product) {
        binding.titleTextView.text = product.title
        binding.priceTextView.text = product.price.toString()
        binding.descriptionTextView.text = product.description
        binding.categoryTextView.text = product.category
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}