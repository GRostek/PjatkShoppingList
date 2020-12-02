package com.example.pjatkshoppinglist

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

class ProductViewModel(application: Application): AndroidViewModel(application) {

    private val repo: ProductRepo
    val allProducts: LiveData<List<Product>>

    init {
        val productDao: ProductDao = ProductDB.getDatabase(application).productDao()
        repo = ProductRepo(productDao)
        allProducts = repo.allProducts
    }


    suspend fun insert(product: Product) = repo.insert(product)

    suspend fun delete(product: Product) = repo.delete(product)

    suspend fun update(product: Product) = repo.update(product)

    suspend fun getById(id:String) = repo.getById(id)
}