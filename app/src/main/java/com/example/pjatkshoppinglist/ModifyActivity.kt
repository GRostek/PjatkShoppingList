package com.example.pjatkshoppinglist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pjatkshoppinglist.roomdb.ProductViewModel
import com.example.pjatkshoppinglist.databinding.ActivityModifyBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModifyActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getLongExtra("id",0)


        val productViewModel = ProductViewModel(application)

        var product :Product = Product(
            itemName = "tmp",
            price=-1.0,
            quantity=-1,
            isBought=false
        )

        CoroutineScope(Dispatchers.IO).launch {
            product = productViewModel.getById(id.toString())
            if(product == null){
                finish()
            }
            binding.editTextProductName.setText(product.itemName)
            binding.editTextPrice.setText(product.price.toString())
            binding.editQuantity.setText(product.quantity.toString())
        }



        binding.saveButton.setOnClickListener {
            if(binding.editTextProductName.text.toString() == "" || binding.editTextPrice.text.toString() == "" || binding.editQuantity.text.toString() == ""){
                Toast.makeText(this,getString(R.string.toast_add_error),Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            product.itemName = binding.editTextProductName.text.toString()
            product.price = binding.editTextPrice.text.toString().toDouble()
            product.quantity = binding.editQuantity.text.toString().toInt()

            CoroutineScope(Dispatchers.IO).launch {
                productViewModel.update(product)
            }

            if(isTaskRoot){
                val intent1 = Intent(this, MainActivity::class.java)
                startActivity(intent1)
                finish()
            } else {
                finish()
            }
        }




        binding.backButton.setOnClickListener {
            if(isTaskRoot){
                val intent1 = Intent(this, MainActivity::class.java)
                startActivity(intent1)
                finish()
            } else {
                finish()
            }
        }


    }
}