package com.example.pjatkshoppinglist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("Select * from Product")
    fun getProducts(): LiveData<List<Product>>

    @Insert
    suspend fun insert(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Update
    suspend fun update(product: Product)

    @Query("Select * from Product where id = :id")
    suspend fun getById(id: String): Product
}