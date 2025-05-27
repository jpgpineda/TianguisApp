package com.example.tianguisapp.view.home.adapters

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.tianguisapp.model.Product
import android.view.View
import android.view.ViewGroup
import com.example.tianguisapp.R
import android.content.Context
import android.util.Log
import com.bumptech.glide.Glide
import com.example.tianguisapp.databinding.ProductItemBinding

class ProductAdapter(
    private val products: MutableList<Product>,
    private val onItemClick: (String) -> Unit
): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private lateinit var context: Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = ProductItemBinding.bind(view)

        fun setUpUI(product: Product) {
            binding.productTitleTextView.text = product.title
            binding.productPriceTextView.text = product.price.toString()
            binding.productCategoryTextView.text = product.category
            binding.itemContainerView.setOnClickListener {
                onItemClick(product.id)
            }

            Glide.with(itemView.context)
                .load(product.image)
                .placeholder(R.drawable.downloading_ic)
                .error(R.drawable.product_error_ic)
                .into(binding.productImageView)
        }
    }

    fun add(productItems: List<Product>) {
        products.addAll(productItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return products.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setUpUI(products[position])
    }
}