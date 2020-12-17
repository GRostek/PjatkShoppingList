package com.example.pjatkshoppinglist

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.pjatkshoppinglist.roomdb.ProductViewModel
import com.example.pjatkshoppinglist.databinding.ActivityAddBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productViewModel = ProductViewModel(application)

        val intent = Intent("com.example.productbroadcast.addproduct")
        intent.component = ComponentName(
                "com.example.productbroadcast",
                "com.example.productbroadcast.ProductReceiver"
        )
        intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)

        binding.saveButton.setOnClickListener{
            if(binding.editTextProductName.text.toString() == "" || binding.editTextPrice.text.toString() == "" || binding.editQuantity.text.toString() == ""){
                Toast.makeText(this,getString(R.string.toast_add_error),Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product = Product(
                    itemName = binding.editTextProductName.text.toString(),
                    price = binding.editTextPrice.text.toString().toDouble(),
                    quantity = binding.editQuantity.text.toString().toInt(),
                    isBought = false
            )
            CoroutineScope(Dispatchers.IO).launch {

                val productId = productViewModel.insert(product)
                intent.putExtra("ProductID", productId)
                intent.putExtra("ProductName", product.itemName)

                sendBroadcast(intent, "com.example.pjatkshoppinglist.NOTIFICATION_PERMISSION")
            }
            Toast.makeText(
                    this,
                    getString(R.string.product) + product.itemName + getString(R.string.add_activity_added),
                    Toast.LENGTH_SHORT
            ).show()
            finish()

        }




        binding.backButton.setOnClickListener{
            finish()
        }


    }

}


