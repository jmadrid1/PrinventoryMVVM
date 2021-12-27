package com.example.prinventory_mvvm.ui.printer

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.FragmentPrinterDetailBinding
import com.example.prinventory_mvvm.databinding.FragmentPrinterEditBinding
import com.example.prinventory_mvvm.models.Printer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrinterEditFragment : Fragment(R.layout.fragment_printer_edit) {

    companion object {
        const val EDITTEXT_TAG_IP = "ip"
    }

    private var _binding: FragmentPrinterEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel : PrinterViewModel by viewModels()
    private val args : PrinterEditFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrinterEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val printer = args.printer

        initViews(printer)
    }

    private fun initViews(printer: Printer){
        onBackButtonPress()

        binding.fragmentPrinterEditImageviewClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fragmentPrinterEditButtonUpdate.setOnClickListener {
            updatePrinter(printer)
        }

        binding.fragmentPrinterEditEdittextIp.tag = EDITTEXT_TAG_IP

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

        binding.fragmentPrinterEditEdittextMake.setText(make)
        binding.fragmentPrinterEditEdittextModel.setText(model)
        binding.fragmentPrinterEditEdittextSerial.setText(serial)
        binding.fragmentPrinterEditEdittextDept.setText(dept)
        binding.fragmentPrinterEditEdittextOwner.setText(owner)
        binding.fragmentPrinterEditEdittextLocation.setText(location)
        binding.fragmentPrinterEditEdittextFloor.setText(floor)
        binding.fragmentPrinterEditEdittextIp.setText(ip)
    }

    @SuppressLint("ResourceType")
    private fun initSpinners(status: Int, color: Int){
        val statusAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_status_values, R.layout.spinner_dropdown_item)
        statusAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.fragmentPrinterEditSpinnerStatus.adapter = statusAdapter
        binding.fragmentPrinterEditSpinnerStatus.setSelection(status)

        val colorAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_color_values, R.layout.spinner_dropdown_item)
        colorAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.fragmentPrinterEditSpinnerColor.adapter = colorAdapter
        binding.fragmentPrinterEditSpinnerColor.setSelection(color)
    }

    private fun updatePrinter(printer: Printer){
        checkTextField(binding.fragmentPrinterEditEdittextMake)
        checkTextField(binding.fragmentPrinterEditEdittextModel)
        checkTextField(binding.fragmentPrinterEditEdittextSerial)
        checkTextField(binding.fragmentPrinterEditEdittextOwner)
        checkTextField(binding.fragmentPrinterEditEdittextDept)
        checkTextField(binding.fragmentPrinterEditEdittextLocation)
        checkTextField(binding.fragmentPrinterEditEdittextFloor)
        checkTextField(binding.fragmentPrinterEditEdittextIp)

        val make : String = binding.fragmentPrinterEditEdittextMake.text.toString()
        val model : String = binding.fragmentPrinterEditEdittextModel.text.toString()
        val serial : String = binding.fragmentPrinterEditEdittextSerial.text.toString()
        val status : Int  = binding.fragmentPrinterEditSpinnerStatus.selectedItemPosition
        val color : Int  = binding.fragmentPrinterEditSpinnerColor.selectedItemPosition
        val owner : String  = binding.fragmentPrinterEditEdittextOwner.text.toString()
        val dept : String  = binding.fragmentPrinterEditEdittextDept.text.toString()
        val location : String  = binding.fragmentPrinterEditEdittextLocation.text.toString()
        val floor : String  = binding.fragmentPrinterEditEdittextFloor.text.toString()
        val ip : String  = binding.fragmentPrinterEditEdittextIp.text.toString()

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

