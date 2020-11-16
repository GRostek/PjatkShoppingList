package com.example.pjatkshoppinglist

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ProductAdapter(private val dane: ArrayList<String>,private val context: AppCompatActivity): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {




    class ViewHolder(val view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val linearView = inflater.inflate(R.layout.product_list_text_view, parent, false) // Text view is probably temporary

        return ViewHolder(linearView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view as TextView
        holder.view.text = dane[position]
        holder.view.setOnLongClickListener{
            //dane.removeAt(position)
            createDeletionConfirmationDialog(position)
            true
        }
    }

    override fun getItemCount(): Int = dane.size


    fun createDeletionConfirmationDialog(position: Int){
        val builder = AlertDialog.Builder(context)
        builder.setMessage(R.string.delete_item_message)
        builder.setCancelable(true)

        builder.setPositiveButton(R.string.delete_item_message_confirmation) { dialog, _ ->
            dane.removeAt(position)
            notifyDataSetChanged()
            dialog.cancel()
        }

        builder.setNegativeButton(R.string.delete_item_message_denial) { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

}