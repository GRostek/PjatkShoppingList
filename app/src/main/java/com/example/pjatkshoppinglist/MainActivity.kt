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

    var color: Int = 0
    var font: Float = 0f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //sharedPreferences = getPreferences(Context.MODE_PRIVATE)
        sharedPreferences = getSharedPreferences("ShoppingApp", Context.MODE_PRIVATE)


        initiateProductListLayoutManager(binding)

        setAddButtonListeners()

        setOptionsButtonListeners()






    }

    override fun onStart() {
        super.onStart()

        val colorArray = resources.obtainTypedArray(R.array.colors)
        val defaultColor =  colorArray.getColor(0,0)
        color = sharedPreferences.getInt("ActualColor",defaultColor)
        font = sharedPreferences.getFloat("ActualSize", 25.0f)
        println(sharedPreferences.contains("ActualColor"))
        println(sharedPreferences.contains("ActualSize"))


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
        val colorList = listOf<Int>(color)
        val fontList = listOf<Float>(font)
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