package com.example.pjatkshoppinglist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pjatkshoppinglist.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        sharedPreferences = getSharedPreferences("ShoppingApp", Context.MODE_PRIVATE)


        initiateProductListLayoutManager()

        setAddButtonListeners()

        setOptionsButtonListeners()




    }


    override fun onResume() {
        super.onResume()

        if(productList.adapter != null) {
            redrawAdapter()
        }
    }





    private fun setAddButtonListeners(){
        addButton.setOnClickListener{
            val intent = Intent(this, AddProductActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setOptionsButtonListeners(){
        optionsButton.setOnClickListener{
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initiateProductListLayoutManager(){
        productList.layoutManager = LinearLayoutManager(this)
        productList.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


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

    private fun redrawAdapter(){

        val adapter = productList.adapter as ProductAdapter
        productList.adapter = null
        productList.adapter = adapter



    }
}