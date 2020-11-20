package com.example.pjatkshoppinglist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pjatkshoppinglist.databinding.ActivityAddBinding
import com.example.pjatkshoppinglist.databinding.ActivityModifyBinding

class ModifyActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getLongExtra("id",0)

        val productViewModel = ProductViewModel(application)


        val product = productViewModel.getById(id.toString())

        binding.editTextProductName.setText(product.itemName)
        binding.editTextPrice.setText(product.price.toString())
        binding.editQuantity.setText(product.quantity.toString())

        binding.saveButton.setOnClickListener {
            if(binding.editTextProductName.text.toString() == "" || binding.editTextPrice.text.toString() == "" || binding.editQuantity.text.toString() == ""){
                Toast.makeText(this,getString(R.string.toast_add_error),Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            product.itemName = binding.editTextProductName.text.toString()
            product.price = binding.editTextPrice.text.toString().toDouble()
            product.quantity = binding.editQuantity.text.toString().toInt()

            productViewModel.update(product)

            finish()
        }




        binding.backButton.setOnClickListener {
            finish()
        }


    }
}