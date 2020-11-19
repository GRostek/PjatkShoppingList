package com.example.pjatkshoppinglist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pjatkshoppinglist.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        initiateProductListLayoutManager(binding)

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

    fun initiateProductListLayoutManager(binding: ActivityMainBinding){
        binding.productList.layoutManager = LinearLayoutManager(this)
        binding.productList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


        val productViewModel = ProductViewModel(application)
        val adapter = ProductAdapter(productViewModel, this)
        productViewModel.allProducts.observe(this, {products ->
            products?.let{
                adapter.setProducts(it)
            }
        })

        productList.adapter = adapter
    }

    fun bindOnClickListener(currentProduct: Product){
        val intent = Intent(this, ModifyActivity::class.java)
        intent.putExtra("id",currentProduct.id.toLong())
        startActivity(intent)
    }
}