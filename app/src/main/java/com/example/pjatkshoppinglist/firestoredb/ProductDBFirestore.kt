package com.example.pjatkshoppinglist.firestoredb

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pjatkshoppinglist.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.NonCancellable.cancel
import kotlinx.coroutines.tasks.await
import java.lang.Exception

object ProductDBFirestore {

    suspend fun getById(id: String): Product?{
        val db = FirebaseFirestore.getInstance()

        return try{
            db.collection("products")
                    .document(id)
                    .get()
                    .await()
                    .toObject(Product::class.java)
        } catch (e: Exception){
            Log.e("Product", "Error while converting",e)
            return null
        }
    }

    @InternalCoroutinesApi
    suspend fun getProducts(): List<Product>?{
        val db = FirebaseFirestore.getInstance()

        var map: List<Product>? = null

        db.collection("products")
                .addSnapshotListener { querySnapshot: QuerySnapshot?, firebaseFirestoreException: FirebaseFirestoreException? ->
            if (firebaseFirestoreException != null) {
                cancel()
                return@addSnapshotListener
            }
            map = querySnapshot?.mapNotNull { it.toObject(Product::class.java) }

        }


        return map
    }

    suspend fun insert(product: Product): Long{
        val db = FirebaseFirestore.getInstance()
        var id = 0L

        db.collection("products")
                .add(product)
                .addOnSuccessListener { document ->
                    id = document.id.toLong()
                }
                .addOnFailureListener{
                    Log.e("insert", "Error while inserting", it)
                }

        return id
    }

    suspend fun  update(product: Product){
        val db = FirebaseFirestore.getInstance()

        db.collection("products")
                .document(product.id.toString())
                .update(mapOf(
                        "itemName" to product.itemName,
                        "price" to product.price,
                        "quantity" to product.quantity,
                        "isBought" to product.isBought
                )).addOnFailureListener{
                    Log.e("update", "Error while updating", it)
                }
    }


    suspend fun delete(product: Product){
        val db = FirebaseFirestore.getInstance()

        db.collection("products")
                .document(product.id.toString())
                .delete().addOnFailureListener{
                    Log.e("delete", "Error while deleting", it)
                }
    }
}