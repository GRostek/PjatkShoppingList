package com.example.pjatkshoppinglist

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
}