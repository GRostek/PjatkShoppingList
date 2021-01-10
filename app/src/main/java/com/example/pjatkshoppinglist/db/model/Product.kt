package com.example.pjatkshoppinglist.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Product(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var itemName: String,
    var price: Double,
    var quantity: Int,
    var isBought: Boolean
    )