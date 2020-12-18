package com.example.pjatkshoppinglist

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.pjatkshoppinglist.databinding.ActivityAddBinding
import com.example.pjatkshoppinglist.firebasedb.ProductViewModelFirebase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch



class AddProductActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var isShared = false

    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        isShared = intent.getBooleanExtra("isShared", false)

        val user = auth.currentUser?.uid
        var productViewModel: ProductViewModelFirebase

        productViewModel = if(user != null){
            ProductViewModelFirebase(application, user, isShared)
        } else{
            ProductViewModelFirebase(application, "", isShared)
        }


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
                    price = binding.editTextPrice.text.toString(),
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


