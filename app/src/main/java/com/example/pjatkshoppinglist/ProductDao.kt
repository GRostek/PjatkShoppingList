package com.example.pjatkshoppinglist

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDao {

    @Query("Select * from Product")
    fun getProducts(): LiveData<List<Product>>

    @Insert
    fun insert(product: Product)

    @Delete
    fun delete(product: Product)

    @Update
    fun update(product: Product)
}