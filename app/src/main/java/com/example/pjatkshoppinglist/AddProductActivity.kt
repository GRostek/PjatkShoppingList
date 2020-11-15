package com.example.pjatkshoppinglist

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add.*


class AddProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        saveButton.setOnClickListener{
            //TODO
        }

        backButton.setOnClickListener{
            finish()
        }
    }

}