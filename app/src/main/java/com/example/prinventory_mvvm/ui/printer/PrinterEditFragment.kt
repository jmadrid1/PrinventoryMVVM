package com.example.prinventory_mvvm.ui.printer

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.models.Printer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_printer_edit.*

@AndroidEntryPoint
class PrinterEditFragment : Fragment(R.layout.fragment_printer_edit) {

    companion object {
        const val EDITTEXT_TAG_IP = "ip"
    }

    private val viewModel : PrinterViewModel by viewModels()
    private val args : PrinterEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val printer = args.printer

        initViews(printer)
    }

    private fun initViews(printer: Printer){
        onBackButtonPress()

        fragment_printer_edit_imageview_close.setOnClickListener {
            findNavController().popBackStack()
        }

        fragment_printer_edit_button_update.setOnClickListener {
            updatePrinter(printer)
        }

        fragment_printer_edit_edittext_ip.tag = EDITTEXT_TAG_IP

        val make = printer.make
        val model = printer.model
        val serial = printer.serial
        val status: Int = printer.status!!
        val color: Int = printer.color!!
        val owner = printer.owner
        val dept = printer.dept
        val location = printer.location
        val floor = printer.floor
        val ip = printer.ip

        initSpinners(status, color)

        fragment_printer_edit_edittext_make.setText(make)
        fragment_printer_edit_edittext_model.setText(model)
        fragment_printer_edit_edittext_serial.setText(serial)
        fragment_printer_edit_edittext_dept.setText(dept)
        fragment_printer_edit_edittext_owner.setText(owner)
        fragment_printer_edit_edittext_location.setText(location)
        fragment_printer_edit_edittext_floor.setText(floor)
        fragment_printer_edit_edittext_ip.setText(ip)
    }

    @SuppressLint("ResourceType")
    private fun initSpinners(status: Int, color: Int){
        val statusAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_status_values, R.layout.spinner_dropdown_item)
        statusAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        fragment_printer_edit_spinner_status.adapter = statusAdapter
        fragment_printer_edit_spinner_status.setSelection(status)

        val colorAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_color_values, R.layout.spinner_dropdown_item)
        colorAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        fragment_printer_edit_spinner_color.adapter = colorAdapter
        fragment_printer_edit_spinner_color.setSelection(color)
    }

    private fun updatePrinter(printer: Printer){
        checkTextField(fragment_printer_edit_edittext_make)
        checkTextField(fragment_printer_edit_edittext_model)
        checkTextField(fragment_printer_edit_edittext_serial)
        checkTextField(fragment_printer_edit_edittext_owner)
        checkTextField(fragment_printer_edit_edittext_dept)
        checkTextField(fragment_printer_edit_edittext_location)
        checkTextField(fragment_printer_edit_edittext_floor)
        checkTextField(fragment_printer_edit_edittext_ip)

        val make : String = fragment_printer_edit_edittext_make.text.toString()
        val model : String = fragment_printer_edit_edittext_model.text.toString()
        val serial : String = fragment_printer_edit_edittext_serial.text.toString()
        val status : Int  = fragment_printer_edit_spinner_status.selectedItemPosition
        val color : Int  = fragment_printer_edit_spinner_color.selectedItemPosition
        val owner : String  = fragment_printer_edit_edittext_owner.text.toString()
        val dept : String  = fragment_printer_edit_edittext_dept.text.toString()
        val location : String  = fragment_printer_edit_edittext_location.text.toString()
        val floor : String  = fragment_printer_edit_edittext_floor.text.toString()
        val ip : String  = fragment_printer_edit_edittext_ip.text.toString()

        printer.make = make
        printer.model = model
        printer.serial = serial
        printer.status = status
        printer.color = color
        printer.owner = owner
        printer.dept = dept
        printer.location = location
        printer.floor = floor
        printer.ip = ip

        viewModel.insertPrinter(printer)
        findNavController().navigate(R.id.printerFragment)
    }

    private fun checkTextField(editText: EditText){
        val text: String = editText.text.toString()

        if(TextUtils.isEmpty(text)){
            if(editText.tag != EDITTEXT_TAG_IP){
                editText.setText("Not Specified")
            }else{
                editText.setText("0")
            }
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

