package com.example.pjatkshoppinglist.firebasedb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData

import com.example.pjatkshoppinglist.Product


class ProductViewModelFirebase(application: Application, user: String) : AndroidViewModel(application) {

    private val repo: ProductDBFirebase = ProductDBFirebase(user)
    val allProducts: LiveData<Map<String, Product>>

    init {
        allProducts = repo.allProducts
    }

    suspend fun insert(product: Product) = repo.insert(product)

    suspend fun delete(product: Product) = repo.delete(product)

    suspend fun update(product: Product) = repo.update(product)

    //suspend fun getById(id:String) = repo.getById(id)

}