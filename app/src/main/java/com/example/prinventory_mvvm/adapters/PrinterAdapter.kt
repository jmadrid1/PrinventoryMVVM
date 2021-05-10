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
import com.example.prinventory_mvvm.models.Printer
import kotlinx.android.synthetic.main.list_row_printer.view.*

class PrinterAdapter : RecyclerView.Adapter<PrinterAdapter.PrinterViewHolder>() {

    inner class PrinterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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
        return PrinterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_row_printer, parent, false))
    }

    private var onItemClickListener : ((Printer) -> Unit)? = null

    override fun onBindViewHolder(holder: PrinterViewHolder, position: Int) {
        val printer = differ.currentList[position]

        val make = printer.make
        val model = printer.model
        val status = printer.status
        val color = printer.color

        if(make.equals("Not Specified") || model.equals("Not Specified")){
            holder.itemView.row_printer_make.text = printer.make
        }else{
            holder.itemView.row_printer_make.text = printer.make + " " + printer.model
        }

        holder.itemView.row_printer_dept.text = "Department: " + printer.dept
        holder.itemView.row_printer_ip.text = "IP: " + printer.ip

        if(status == 0) {
            ViewCompat.setBackgroundTintList(
                    holder.itemView.row_printer_frame_status,
                    ContextCompat.getColorStateList(
                            holder.itemView.row_printer_frame_status.context,
                            R.color.status_red
                    ))

            holder.itemView.row_printer_imageview_status.setImageResource(R.drawable.ic_close)
        }else{
            ViewCompat.setBackgroundTintList(
                    holder.itemView.row_printer_frame_status,
                    ContextCompat.getColorStateList(
                            holder.itemView.row_printer_frame_status.context,
                            R.color.status_green
                    ))
            holder.itemView.row_printer_imageview_status.setImageResource(R.drawable.ic_status_active)
        }

        if(color == 0) {
            holder.itemView.row_printer_color.setImageResource(R.drawable.ic_toner_bw)
        }else{
            holder.itemView.row_printer_color.setImageResource(R.drawable.ic_toner_color)
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