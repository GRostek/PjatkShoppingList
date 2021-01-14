package com.example.pjatkshoppinglist.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pjatkshoppinglist.R
import com.example.pjatkshoppinglist.db.model.Shop
import com.example.pjatkshoppinglist.db.viewmodel.ShopViewModel
import kotlinx.android.synthetic.main.activity_add_shop.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddShopActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_shop)

        val shopViewModel = ShopViewModel(application)

        saveButton.setOnClickListener {
            if(editTextShopName.text.toString() == "" || editTextDescription.text.toString() == "" || editRadius.text.toString() == ""){
                Toast.makeText(this,getString(R.string.toast_add_error), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val latitude = intent.getDoubleExtra("latitude",0.0)
            val longitude = intent.getDoubleExtra("longitude",0.0)

            val shop = Shop(
                name = editTextShopName.text.toString(),
                description = editTextDescription.text.toString(),
                radius = editRadius.text.toString().toFloat(),
                latitude = latitude,
                longitude = longitude
            )

            var id = -1L
            CoroutineScope(Dispatchers.IO).launch {
                id = shopViewModel.insert(shop)
            }

            Toast.makeText(
                this,
                "shop was added",
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent()
            intent.putExtra("latitude", shop.latitude)
            intent.putExtra("longitude", shop.longitude)
            intent.putExtra("name", shop.name)
            intent.putExtra("description", shop.description)
            intent.putExtra("radius", shop.radius)
            intent.putExtra("id", id)
            setResult(RESULT_OK, intent)
            finish()
            }


        backButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}
