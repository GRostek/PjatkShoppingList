package com.example.pjatkshoppinglist.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pjatkshoppinglist.db.model.Product
import com.example.pjatkshoppinglist.db.dao.ProductDao
import com.example.pjatkshoppinglist.db.dao.ShopDao
import com.example.pjatkshoppinglist.db.model.Shop


@Database(entities = [Product::class, Shop::class], version = 2)
abstract class ProductDB : RoomDatabase() {

    abstract fun productDao(): ProductDao
    abstract fun shopDao(): ShopDao

    companion object{
        private var instance: ProductDB? = null

        fun getDatabase(context: Context): ProductDB {
            if(instance != null)
                return instance as ProductDB
            instance = Room.databaseBuilder(
                context.applicationContext,
                ProductDB::class.java,
                "ProductDatabase"
            ).fallbackToDestructiveMigration() // TODO add migration without deleting data
                .build()
            return instance as ProductDB
        }

    }

}