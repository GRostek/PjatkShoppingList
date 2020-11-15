package com.example.pjatkshoppinglist

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        addButton.setOnClickListener{
            //TODO przejscie do ekranu dodania elementu do listy
        }

        optionsButton.setOnClickListener{
            //TODO przejscie do ekranu opcji, w opcjach zaimplementuj zmiane jezyka aplikacji i koloru czcionki
        }


    }

    override fun onStart() {
        super.onStart()
        //TODO tutaj wczytanie opcji z shared preferences
    }

    override fun onStop() {
        super.onStop()
        //TODO zapis do shared preferences, pamietaj o editor.apply
    }
}