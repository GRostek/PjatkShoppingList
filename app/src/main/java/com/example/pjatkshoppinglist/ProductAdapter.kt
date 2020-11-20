package com.example.pjatkshoppinglist

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Resources
import android.graphics.Color
import android.util.TypedValue

import android.view.LayoutInflater

import android.view.ViewGroup

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pjatkshoppinglist.databinding.ProductListViewBinding
import kotlinx.android.synthetic.main.activity_options.*


class ProductAdapter(private val viewModel: ProductViewModel,
                     private val context: AppCompatActivity): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


    private var productList = emptyList<Product>()

    private lateinit var sharedPreferences: SharedPreferences


    class ViewHolder(val binding: ProductListViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProductListViewBinding.inflate(inflater)

        //sharedPreferences = context.getPreferences(Context.MODE_PRIVATE)
        sharedPreferences = context.getSharedPreferences("ShoppingApp", Context.MODE_PRIVATE)


        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentProduct = productList[position]


        val colorArray = context.resources.obtainTypedArray(R.array.colors)
        val defaultColor = colorArray.getColor(0, 0)
        val color = sharedPreferences.getInt("ActualColor", defaultColor)
        val font = sharedPreferences.getFloat("ActualSize", 25.0f)


        if (color != 0) {
            holder.binding.textViewItemName.setTextColor(color)
            holder.binding.textViewPrice.setTextColor(color)
            holder.binding.textViewCount.setTextColor(color)
            //holder.binding.checkBoxIsBought.setTextColor(color)

        }

        if (font != 0f) {
            holder.binding.textViewItemName.setTextSize(TypedValue.COMPLEX_UNIT_SP, font)
            holder.binding.textViewPrice.setTextSize(TypedValue.COMPLEX_UNIT_SP, font)
            holder.binding.textViewCount.setTextSize(TypedValue.COMPLEX_UNIT_SP, font)
            //holder.binding.checkBoxIsBought.setTextSize(TypedValue.COMPLEX_UNIT_SP,font)
        }


        holder.binding.textViewItemName.text = currentProduct.itemName.toString()
        holder.binding.textViewPrice.text = currentProduct.price.toString() + "zł"
        holder.binding.textViewCount.text = currentProduct.quantity.toString()
        holder.binding.checkBoxIsBought.isChecked = currentProduct.isBought



        holder.binding.checkBoxIsBought.setOnCheckedChangeListener { _, isChecked ->
            currentProduct.isBought = isChecked
            viewModel.update(currentProduct)
        }

        holder.binding.root.setOnLongClickListener {
            createDeletionConfirmationDialog(currentProduct)
            true
        }

        holder.binding.root.setOnClickListener {
            context as MainActivity
            context.bindOnClickListener(currentProduct)
        }



    }

    override fun getItemCount(): Int = productList.size


    private fun createDeletionConfirmationDialog(product: Product) {
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

    fun setProducts(products: List<Product>) {
        productList = products
        notifyDataSetChanged()
    }


}



