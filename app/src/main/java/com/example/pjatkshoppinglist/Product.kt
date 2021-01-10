package com.example.pjatkshoppinglist


import androidx.room.Entity
import com.google.firebase.database.Exclude
import com.google.firebase.database.PropertyName
import com.google.firebase.firestore.DocumentId
import java.io.Serializable


data class Product(
    //@PrimaryKey(autoGenerate = true)
        @Exclude var id: String = "",
        @set:PropertyName("itemName")
        @get:PropertyName("itemName")
        var itemName: String,
        @set:PropertyName("price")
        @get:PropertyName("price")
        var price: String,
        @set:PropertyName("quantity")
        @get:PropertyName("quantity")
        var quantity: Int,
        @set:PropertyName("isBought")
        @get:PropertyName("isBought")
        var isBought: Boolean
    ): Serializable{

    constructor() : this("","","",0,false)

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "itemName" to itemName,
            "price" to price,
            "quantity" to quantity,
            "isBought" to isBought
        )
    }
}

