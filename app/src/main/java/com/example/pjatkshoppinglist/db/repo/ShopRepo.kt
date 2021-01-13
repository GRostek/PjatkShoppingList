package com.example.pjatkshoppinglist.db.repo

import com.example.pjatkshoppinglist.db.dao.ShopDao
import com.example.pjatkshoppinglist.db.model.Shop

class ShopRepo(private val shopDao: ShopDao) {

    val allShops = shopDao.getShops()

    suspend fun  getShopsAsync(): List<Shop>{
        return shopDao.getShopsAsync()
    }

    suspend fun insert(shop: Shop): Long{
        return shopDao.insert(shop)
    }

    suspend fun delete(shop: Shop){
        shopDao.delete(shop)
    }

    suspend fun update(shop: Shop){
        shopDao.update(shop)
    }

    suspend fun getById(id: String): Shop {
        return shopDao.getById(id)
    }
}