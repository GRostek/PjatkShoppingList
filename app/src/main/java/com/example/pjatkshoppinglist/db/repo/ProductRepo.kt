package com.example.pjatkshoppinglist.db.repo

import com.example.pjatkshoppinglist.db.dao.ProductDao
import com.example.pjatkshoppinglist.db.model.Product

class ProductRepo(private val productDao: ProductDao) {

    val allProducts = productDao.getProducts()

    suspend fun insert(product: Product): Long{
        return productDao.insert(product)
    }

    suspend fun delete(product: Product){
        productDao.delete(product)
    }

    suspend fun update(product: Product){
        productDao.update(product)
    }

    suspend fun getById(id: String): Product {
        return productDao.getById(id)
    }
}