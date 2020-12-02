package com.example.pjatkshoppinglist

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.pjatkshoppinglist.databinding.ActivityAddBinding
import com.example.pjatkshoppinglist.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat


class AddProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val productViewModel = ProductViewModel(application)

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

                productViewModel.insert(product)

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


