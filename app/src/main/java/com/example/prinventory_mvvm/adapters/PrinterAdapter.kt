package com.example.prinventory_mvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.ListRowPrinterBinding
import com.example.prinventory_mvvm.models.Printer

class PrinterAdapter : RecyclerView.Adapter<PrinterAdapter.PrinterViewHolder>() {

    inner class PrinterViewHolder(val binding: ListRowPrinterBinding) : RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Printer>() {
        override fun areItemsTheSame(oldItem: Printer, newItem: Printer): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Printer, newItem: Printer): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrinterViewHolder {
        val binding = ListRowPrinterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PrinterViewHolder(binding)
    }

    private var onItemClickListener : ((Printer) -> Unit)? = null

    override fun onBindViewHolder(holder: PrinterViewHolder, position: Int) {
        val printer = differ.currentList[position]

        val make = printer.make
        val model = printer.model
        val status = printer.status
        val color = printer.color

        if(make.equals("Not Specified") || model.equals("Not Specified")){
            holder.binding.rowPrinterMake.text = printer.make
        }else{
            holder.binding.rowPrinterMake.text = printer.make + " " + printer.model
        }

        holder.binding.rowPrinterDept.text = "Department: " + printer.dept
        holder.binding.rowPrinterIp.text = "IP: " + printer.ip

        if(status == 0) {
            ViewCompat.setBackgroundTintList(
                    holder.binding.rowPrinterFrameStatus,
                    ContextCompat.getColorStateList(
                        holder.binding.rowPrinterFrameStatus.context,
                            R.color.status_red
                    ))

            holder.binding.rowPrinterImageviewStatus.setImageResource(R.drawable.ic_close)
        }else{
            ViewCompat.setBackgroundTintList(
                    holder.binding.rowPrinterFrameStatus,
                    ContextCompat.getColorStateList(
                            holder.binding.rowPrinterFrameStatus.context,
                            R.color.status_green
                    ))
            holder.binding.rowPrinterImageviewStatus.setImageResource(R.drawable.ic_status_active)
        }

        if(color == 0) {
            holder.binding.rowPrinterColor.setImageResource(R.drawable.ic_toner_bw)
        }else{
            holder.binding.rowPrinterColor.setImageResource(R.drawable.ic_toner_color)
        }

        holder.itemView.setOnClickListener {
            onItemClickListener?.let {
               it(printer)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun setOnItemClickListener(listener : (Printer) -> Unit){
        onItemClickListener = listener
    }

}