package com.example.pjatkshoppinglist.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.pjatkshoppinglist.db.model.Shop


@Dao
interface ShopDao {

    @Query("Select * from Shop")
    fun getShops(): LiveData<List<Shop>>

    @Insert
    suspend fun insert(shop: Shop): Long

    @Delete
    suspend fun delete(shop: Shop)

    @Update
    suspend fun update(shop: Shop)

    @Query("Select * from Shop where id = :id")
    suspend fun getById(id: String): Shop
}