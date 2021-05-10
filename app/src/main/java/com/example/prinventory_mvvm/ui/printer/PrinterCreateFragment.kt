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
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.models.Printer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_printer_create.*

@AndroidEntryPoint
class PrinterCreateFragment : Fragment(R.layout.fragment_printer_create) {

    companion object {
        const val EDITTEXT_TAG_IP = "ip"
    }

    private val viewModel : PrinterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackButtonPress()
        initViews()

        fragment_printer_create_button_save.setOnClickListener {
            savePrinter()
        }
    }

    private fun initViews(){
        fragment_printer_create_imageview_close.setOnClickListener {
            findNavController().popBackStack()
        }
        initSpinners()
        fragment_printer_create_edittext_ip.tag = EDITTEXT_TAG_IP
    }

    @SuppressLint("ResourceType")
    private fun initSpinners(){
        val statusAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_status_values, R.layout.spinner_dropdown_item)
        statusAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        fragment_printer_create_spinner_status.adapter = statusAdapter
        fragment_printer_create_spinner_status.setSelection(0)

        val colorAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_color_values, R.layout.spinner_dropdown_item)
        colorAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        fragment_printer_create_spinner_color.adapter = colorAdapter
        fragment_printer_create_spinner_color.setSelection(0)
    }

    private fun savePrinter(){
        checkTextField(fragment_printer_create_edittext_make)
        checkTextField(fragment_printer_create_edittext_model)
        checkTextField(fragment_printer_create_edittext_serial)
        checkTextField(fragment_printer_create_edittext_owner)
        checkTextField(fragment_printer_create_edittext_dept)
        checkTextField(fragment_printer_create_edittext_location)
        checkTextField(fragment_printer_create_edittext_floor)
        checkTextField(fragment_printer_create_edittext_ip)

        val make : String = fragment_printer_create_edittext_make.text.toString()
        val model : String = fragment_printer_create_edittext_model.text.toString()
        val serial : String = fragment_printer_create_edittext_serial.text.toString()
        val status : Int  = fragment_printer_create_spinner_color.selectedItemPosition
        val color : Int  = fragment_printer_create_spinner_color.selectedItemPosition
        val owner : String  = fragment_printer_create_edittext_owner.text.toString()
        val dept : String  = fragment_printer_create_edittext_dept.text.toString()
        val location : String  = fragment_printer_create_edittext_location.text.toString()
        val floor : String  = fragment_printer_create_edittext_floor.text.toString()
        val ip : String  = fragment_printer_create_edittext_ip.text.toString()

        val printer = Printer(make = make,
                model = model,
                serial = serial,
                status = status,
                color = color,
                owner = owner,
                dept = dept,
                location = location,
                floor = floor,
                ip = ip)

        viewModel.insertPrinter(printer)
        findNavController().popBackStack()
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