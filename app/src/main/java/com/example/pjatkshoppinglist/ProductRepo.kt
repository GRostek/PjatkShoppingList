package com.example.pjatkshoppinglist

import androidx.lifecycle.LiveData

class ProductRepo(private val productDao: ProductDao) {

    val allProducts = productDao.getProducts()

    fun insert(product: Product){
        productDao.insert(product)
    }

    fun delete(product: Product){
        productDao.delete(product)
    }

    fun update(product: Product){
        productDao.update(product)
    }

    fun getById(id: String): Product {
        return productDao.getById(id)
    }
}