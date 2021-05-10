package com.example.prinventory_mvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.models.Vendor
import kotlinx.android.synthetic.main.list_row_vendor.view.*

class VendorAdapter : RecyclerView.Adapter<VendorAdapter.VendorViewHolder>() {

    inner class VendorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<Vendor>() {
        override fun areItemsTheSame(oldItem: Vendor, newItem: Vendor): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Vendor, newItem: Vendor): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorViewHolder {
        return VendorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_row_vendor, parent, false))
    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {
        val vendor = differ.currentList[position]

        holder.itemView.row_vendor_name.text = vendor.name
        holder.itemView.row_vendor_phone.text = "Phone: " + vendor.phone
        holder.itemView.row_vendor_email.text = "Email: " + vendor.email

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(vendor)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener : ((Vendor) -> Unit)? = null

    fun setOnItemClickListener(listener : (Vendor) -> Unit){
        onItemClickListener = listener
    }

}