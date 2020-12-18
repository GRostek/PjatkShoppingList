package com.example.pjatkshoppinglist.firebasedb


import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.pjatkshoppinglist.Product
import com.google.firebase.database.*


class ProductDBFirebase(user: String, isShared: Boolean) {
    val allProducts = MutableLiveData<Map<String, Product>>()
    private val db = FirebaseDatabase.getInstance("https://pjatkshoppinglist-default-rtdb.europe-west1.firebasedatabase.app/")

    private var ref :DatabaseReference = db.getReference("users/$user/products")

    //private val refPriv = db.getReference("users/$user/products")
    private val revShared = db.getReference("products")

    init {

        if(isShared){
            ref = revShared
        }

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val updateProducts = HashMap<String, Product>()
                for (productSnapshot in snapshot.children) {

                    try{
                        val id = productSnapshot.key as String
                        val itemName = productSnapshot.child("itemName").value as String
                        val price = productSnapshot.child("price").value as String

                        val quantity = (productSnapshot.child("quantity").value as Long).toInt()
                        val isBought = productSnapshot.child("isBought").value as Boolean

                        val product = Product(
                            id = id,
                            itemName = itemName,
                            price = price,
                            quantity = quantity,
                            isBought = isBought
                        )
                        updateProducts[id] = product
                    } catch (e: Exception){
                        Log.e("UPDATE", "Update failed",e)
                    }
                }
                allProducts.postValue(updateProducts)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("OnCancelled", "Error in OnCancelled")
            }
        })
    }
    /*suspend fun getById(id: String): Product? {
        val rawProduct = ref.child(id)

        rawProduct.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(productSnapshot: DataSnapshot) {
                val id = productSnapshot.key as String
                val itemName = productSnapshot.child("itemName").value as String
                val price = productSnapshot.child("price").value as String

                val quantity = (productSnapshot.child("quantity").value as Long).toInt()
                val isBought = productSnapshot.child("isBought").value as Boolean
            }
        })




        val product = id?.let {
            Product(
                id = it,
                itemName = itemName,
                price = price,
                quantity = quantity,
                isBought = isBought
        )
        }

        return product
    }*/

    suspend fun insert(product: Product): String?  {
        val id = this.ref.push().key
        return if (id != null) {
            this.ref.child(id).setValue(product.toMap())
            id
        } else
            null

    }

    suspend fun delete(product: Product){
        val id = product.id
        if (id != null)
            this.ref.child(id).removeValue()
    }

    suspend fun update(product: Product){
        val id = product.id
        if (id != null)
            this.ref.child(id).updateChildren(product.toMap())
    }
}