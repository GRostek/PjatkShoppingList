package com.example.pjatkshoppinglist

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.pjatkshoppinglist.databinding.ActivityOptionsBinding
import kotlinx.android.synthetic.main.activity_add.backButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_options.*

class OptionsActivity: AppCompatActivity() {


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    private var hasChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        sharedPreferences = getSharedPreferences("ShoppingApp", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()



        saveOptionsButton.setOnClickListener{
            if(editFontSize.text.toString() == ""){
                Toast.makeText(this,getString(R.string.empty_font_size_toast),Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val size = editFontSize.text.toString().toFloat()

            if(size in 10.0..64.0){
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)

                val colorArray = resources.obtainTypedArray(R.array.colors)
                val pos = spinner.selectedItemPosition
                val color = colorArray.getColor(pos,0)

                textView.setTextColor(color)
                textView2.setTextColor(color)
                hasChanged = true
                finish()
            } else {
                Toast.makeText(this,getString(R.string.text_size_toast),Toast.LENGTH_SHORT).show()
            }
        }


        backButton.setOnClickListener {
            finish()
        }
    }

    /*private fun setFontListener(){
        editFontSize.doAfterTextChanged {
            if(it.isNullOrBlank()){}
            else {
                val size = it.toString().toFloat()
                if (size in 1.0..64.0) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
                } else {
                    editFontSize.setText("")
                }
            }
        }
    }*/


    /*private fun setColorListener(){
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val colorArray = resources.obtainTypedArray(R.array.colors)
                val pos = spinner.selectedItemPosition
                val color = colorArray.getColor(pos,0)

                textView.setTextColor(color)
                textView2.setTextColor(color)
            }

        }
    }*/

    override fun onStart() {
        super.onStart()

        val colorArray = resources.obtainTypedArray(R.array.colors)
        val defaultColor =  colorArray.getColor(0,0)

        val color = sharedPreferences.getInt("ActualColor",defaultColor)

        textView.setTextColor(color)
        textView2.setTextColor(color)

        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, sharedPreferences.getFloat("ActualSize", 25.0f))
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, sharedPreferences.getFloat("ActualSize", 25.0f))

        println("Options")
        println(sharedPreferences.contains("ActualColor"))
        println(sharedPreferences.contains("ActualSize"))

    }

    override fun onStop() {
        super.onStop()

        if(!hasChanged) return


        val colorArray = resources.obtainTypedArray(R.array.colors)
        val pos = spinner.selectedItemPosition
        val color = colorArray.getColor(pos, 0)

            editor.putInt("ActualColor", color)

        if(editFontSize.text.toString() != "") {
            val size = editFontSize.text.toString().toFloat()
            editor.putFloat("ActualSize", size)
        }

        editor.apply()
    }
}

