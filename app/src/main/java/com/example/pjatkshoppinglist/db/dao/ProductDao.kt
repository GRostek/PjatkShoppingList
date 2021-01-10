package com.example.pjatkshoppinglist.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pjatkshoppinglist.db.model.Product

@Dao
interface ProductDao {

    @Query("Select * from Product")
    fun getProducts(): LiveData<List<Product>>

    @Insert
    suspend fun insert(product: Product): Long

    @Delete
    suspend fun delete(product: Product)

    @Update
    suspend fun update(product: Product)

    @Query("Select * from Product where id = :id")
    suspend fun getById(id: String): Product
}