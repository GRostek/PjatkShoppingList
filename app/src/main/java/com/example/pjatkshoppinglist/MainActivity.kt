package com.example.pjatkshoppinglist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        initiateProductListLayoutManager()

        setAddButtonListeners()

        setOptionsButtonListeners()



    }

    override fun onStart() {
        super.onStart()
        //TODO tutaj wczytanie opcji z shared preferences
    }

    override fun onStop() {
        super.onStop()
        //TODO zapis do shared preferences

        editor.apply()
    }

    fun setAddButtonListeners(){
        addButton.setOnClickListener{
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }
    }

    fun setOptionsButtonListeners(){
        optionsButton.setOnClickListener{
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }
    }

    fun initiateProductListLayoutManager(){
        productList.layoutManager = LinearLayoutManager(this)
        productList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        val dane = arrayListOf("element1","element2","element3","element4","element5","element6")
        val adapter = ProductAdapter(dane, this)
        productList.adapter = adapter
    }
}