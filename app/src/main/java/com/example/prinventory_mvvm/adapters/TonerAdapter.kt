package com.example.prinventory_mvvm.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.ListRowTonerBinding
import com.example.prinventory_mvvm.models.Toner

class TonerAdapter : RecyclerView.Adapter<TonerAdapter.TonerViewHolder>() {

    inner class TonerViewHolder(val binding: ListRowTonerBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Toner>() {
        override fun areItemsTheSame(oldItem: Toner, newItem: Toner): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Toner, newItem: Toner): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TonerViewHolder {
        val binding = ListRowTonerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TonerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TonerViewHolder, position: Int) {
        val toner = differ.currentList[position]

        val make = toner.make
        val model = toner.model
        val tonerModel = toner.tonerModel
        val color = toner.color

        if(make.equals("Not Specified") || model.equals("Not Specified")){
            holder.binding.rowTonerMake.text = toner.make
        }else{
            holder.binding.rowTonerMake.text = toner.make + " " + toner.model
        }

        if(color == 0) {
            holder.binding.rowTonerColor.setImageResource(R.drawable.ic_toner_bw)
        }else{
            holder.binding.rowTonerColor.setImageResource(R.drawable.ic_toner_color)
        }

        holder.binding.rowTonerTmodel.text = "Toner Model: " + tonerModel

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
                it(toner)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener : ((Toner) -> Unit)? = null

    fun setOnItemClickListener(listener : (Toner) -> Unit){
        onItemClickListener = listener
    }

}