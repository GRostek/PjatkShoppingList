package com.example.pjatkshoppinglist

import android.app.AlertDialog
import android.content.Intent

import android.view.LayoutInflater

import android.view.ViewGroup

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pjatkshoppinglist.databinding.ProductListViewBinding


class ProductAdapter(private val viewModel: ProductViewModel, private val context: AppCompatActivity): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


    private var productList = emptyList<Product>()


    class ViewHolder(val binding: ProductListViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductListViewBinding.inflate(inflater)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = productList[position]
        holder.binding.textViewItemName.text = currentProduct.itemName.toString()
        holder.binding.textViewPrice.text = currentProduct.price.toString()
        holder.binding.textViewCount.text = currentProduct.quantity.toString()
        holder.binding.checkBoxIsBought.isChecked = currentProduct.isBought

        holder.binding.checkBoxIsBought.setOnCheckedChangeListener{ _, isChecked ->
            currentProduct.isBought = isChecked
            viewModel.update(currentProduct)
        }

        holder.binding.root.setOnLongClickListener{
            createDeletionConfirmationDialog(currentProduct)
            true
        }

        holder.binding.root.setOnClickListener{
            context as MainActivity
            context.bindOnClickListener(currentProduct)
        }
    }

    override fun getItemCount(): Int = productList.size


    fun createDeletionConfirmationDialog(product: Product){
        val builder = AlertDialog.Builder(context)
        builder.setMessage(R.string.delete_item_message)
        builder.setCancelable(true)

        builder.setPositiveButton(R.string.delete_item_message_confirmation) { dialog, _ ->
            viewModel.delete(product)
            Toast.makeText(context, context.getString(R.string.product_remove), Toast.LENGTH_SHORT).show()
            dialog.cancel()
        }

        builder.setNegativeButton(R.string.delete_item_message_denial) { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    fun setProducts(products: List<Product>){
        productList = products
        notifyDataSetChanged()
    }

}