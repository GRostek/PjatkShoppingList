package com.example.pjatkshoppinglist


import androidx.room.Entity
import com.google.firebase.firestore.DocumentId
import java.io.Serializable


data class Product(
    //@PrimaryKey(autoGenerate = true)
    var id: String = "",
    var itemName: String,
    var price: String,
    var quantity: Int,
    var isBought: Boolean
    ): Serializable{

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "itemName" to itemName,
            "price" to price,
            "quantity" to quantity,
            "isBought" to isBought
        )
    }
}

