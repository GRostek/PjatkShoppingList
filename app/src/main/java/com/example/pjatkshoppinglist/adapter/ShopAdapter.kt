package com.example.pjatkshoppinglist.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pjatkshoppinglist.R
import com.example.pjatkshoppinglist.databinding.ShopListViewBinding
import com.example.pjatkshoppinglist.db.model.Shop
import com.example.pjatkshoppinglist.db.viewmodel.ShopViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShopAdapter(private val viewModel: ShopViewModel,
                  private val context: AppCompatActivity): RecyclerView.Adapter<ShopAdapter.ViewHolder>() {


    private var shopList = emptyList<Shop>()

    private lateinit var sharedPreferences: SharedPreferences


    class ViewHolder(val binding: ShopListViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ShopListViewBinding.inflate(inflater)

        //sharedPreferences = context.getPreferences(Context.MODE_PRIVATE)
        sharedPreferences = context.getSharedPreferences("ShoppingApp", Context.MODE_PRIVATE)


        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = shopList.size


    private fun createDeletionConfirmationDialog(shop: Shop) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(R.string.delete_item_message)
        builder.setCancelable(true)

        builder.setPositiveButton(R.string.delete_item_message_confirmation) { dialog, _ ->
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.delete(shop)
            }
            Toast.makeText(context, context.getString(R.string.shop_removed), Toast.LENGTH_SHORT).show()
            dialog.cancel()
        }

        builder.setNegativeButton(R.string.delete_item_message_denial) { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    fun setShops(shops: List<Shop>) {
        shopList = shops
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ShopAdapter.ViewHolder, position: Int) {
        val currentShop = shopList[position]



        holder.binding.textViewShopName.text = currentShop.name
        holder.binding.textViewDescription.text = currentShop.description
        holder.binding.textViewRadius.text = currentShop.radius.toString()
        holder.binding.textViewlatitude.text = currentShop.latitude.toString()
        holder.binding.textViewlongitude.text = currentShop.longitude.toString()



        holder.binding.root.setOnLongClickListener {
            createDeletionConfirmationDialog(currentShop)
            true
        }

    }


}