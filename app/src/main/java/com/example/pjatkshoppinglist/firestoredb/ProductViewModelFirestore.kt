package com.example.pjatkshoppinglist.firestoredb

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pjatkshoppinglist.Product
import kotlinx.coroutines.*


@InternalCoroutinesApi
class ProductViewModelFirestore(application: Application) : AndroidViewModel(application) {

    lateinit var allProducts: MutableLiveData<List<Product>>


    init{
        CoroutineScope(Dispatchers.IO).launch {
            allProducts.value = ProductDBFirestore.getProducts()
        }
    }

    suspend fun insert(product: Product) = ProductDBFirestore.insert(product)

    suspend fun delete(product: Product) = ProductDBFirestore.delete(product)

    suspend fun update(product: Product) = ProductDBFirestore.update(product)

    suspend fun getById(id:String) = ProductDBFirestore.getById(id)

}