package com.example.prinventory_mvvm.ui.printer

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.prinventory_mvvm.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_printer_detail.*
import kotlinx.android.synthetic.main.list_row_printer.view.*

@AndroidEntryPoint
class PrinterDetailFragment : Fragment(R.layout.fragment_printer_detail) {

    companion object {
        const val BUNDLE_KEY = "printer"
    }

    private val viewModel : PrinterViewModel by viewModels()
    private val args : PrinterDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val printer = args.printer
        onBackButtonPress()

        fragment_printer_detail_imageview_close.setOnClickListener {
            findNavController().popBackStack()
        }

        fragment_printer_detail_imageview_delete.setOnClickListener {
            viewModel.deletePrinter(printer)
            findNavController().popBackStack()
        }

        val make = printer.make
        val model = printer.model
        val serial = printer.serial
        val status: Int = printer.status!!
        val owner = printer.owner
        val dept = printer.dept
        val location = printer.location
        val floor = printer.floor
        val ip = printer.ip

        if(status == 0) {
            ViewCompat.setBackgroundTintList(
                    fragment_printer_detail_frame_status,
                    ContextCompat.getColorStateList(
                            view.context,
                            R.color.status_red
                    ))

            fragment_printer_detail_imageview_status.setImageResource(R.drawable.ic_close)
        }else{
            ViewCompat.setBackgroundTintList(
                    fragment_printer_detail_frame_status,
                    ContextCompat.getColorStateList(
                            view.context,
                            R.color.status_green
                    ))
            fragment_printer_detail_imageview_status.setImageResource(R.drawable.ic_status_active)
        }

        if(make.equals("Not Specified") || model.equals("Not Specified")){
            fragment_printer_detail_textview_make.text = printer.make
        }else{
            fragment_printer_detail_textview_make.text = printer.make + " " + printer.model
        }

        fragment_printer_detail_textview_serial.text = serial
        fragment_printer_detail_textview_owner.text = owner
        fragment_printer_detail_textview_dept.text =dept
        fragment_printer_detail_textview_location.text = location
        fragment_printer_detail_textview_floor.text = floor
        fragment_printer_detail_textview_ip.text = ip

        fragment_printer_detail_button_edit.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable(BUNDLE_KEY, printer)
            }
            findNavController().navigate(R.id.action_printerDetailFragment_to_printerEditFragment, bundle)
        }
    }

    private fun onBackButtonPress(){
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}