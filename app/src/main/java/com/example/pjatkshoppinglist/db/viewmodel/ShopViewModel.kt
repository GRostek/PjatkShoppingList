package com.example.pjatkshoppinglist.db.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.pjatkshoppinglist.db.ProductDB
import com.example.pjatkshoppinglist.db.dao.ShopDao
import com.example.pjatkshoppinglist.db.model.Shop
import com.example.pjatkshoppinglist.db.repo.ShopRepo

class ShopViewModel(application: Application): AndroidViewModel(application) {

    private val repo: ShopRepo
    val allShops: LiveData<List<Shop>>

    init {
        val shopDao: ShopDao = ProductDB.getDatabase(application).shopDao()
        repo = ShopRepo(shopDao)
        allShops = repo.allShops
    }

    suspend fun getShopsAsync() = repo.getShopsAsync()

    suspend fun insert(shop: Shop) = repo.insert(shop)

    suspend fun delete(shop: Shop) = repo.delete(shop)

    suspend fun update(shop: Shop) = repo.update(shop)

    suspend fun getById(id:String) = repo.getById(id)
}