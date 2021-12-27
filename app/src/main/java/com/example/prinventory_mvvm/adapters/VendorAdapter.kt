package com.example.prinventory_mvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.prinventory_mvvm.databinding.ListRowVendorBinding
import com.example.prinventory_mvvm.models.Vendor

class VendorAdapter : RecyclerView.Adapter<VendorAdapter.VendorViewHolder>() {

    inner class VendorViewHolder(val binding: ListRowVendorBinding) : RecyclerView.ViewHolder(binding.root)

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
        val binding = ListRowVendorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VendorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VendorViewHolder, position: Int) {
        val vendor = differ.currentList[position]

        holder.binding.rowVendorName.text = vendor.name
        holder.binding.rowVendorPhone.text = "Phone: " + vendor.phone
        holder.binding.rowVendorEmail.text = "Email: " + vendor.email

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