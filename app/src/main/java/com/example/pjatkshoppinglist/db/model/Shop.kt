package com.example.pjatkshoppinglist.db.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Shop(
        @PrimaryKey(autoGenerate = true) var id: Long = 0,
        var name: String,
        var description: String,
        var latitude: Double,
        var longitude: Double,
        var radius: Double
) {
}