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
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.FragmentPrinterCreateBinding
import com.example.prinventory_mvvm.models.Printer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrinterCreateFragment : Fragment(R.layout.fragment_printer_create) {

    companion object {
        const val EDITTEXT_TAG_IP = "ip"
    }

    private var _binding: FragmentPrinterCreateBinding? = null
    private val binding get() = _binding!!

    private val viewModel : PrinterViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrinterCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackButtonPress()
        initViews()

        binding.fragmentPrinterCreateButtonSave.setOnClickListener {
            savePrinter()
        }
    }

    private fun initViews(){
        binding.fragmentPrinterCreateImageviewClose.setOnClickListener {
            findNavController().popBackStack()
        }
        initSpinners()
        binding.fragmentPrinterCreateEdittextIp.tag = EDITTEXT_TAG_IP
    }

    @SuppressLint("ResourceType")
    private fun initSpinners(){
        val statusAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_status_values, R.layout.spinner_dropdown_item)
        statusAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.fragmentPrinterCreateSpinnerStatus.adapter = statusAdapter
        binding.fragmentPrinterCreateSpinnerStatus.setSelection(0)

        val colorAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_color_values, R.layout.spinner_dropdown_item)
        colorAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.fragmentPrinterCreateSpinnerColor.adapter = colorAdapter
        binding.fragmentPrinterCreateSpinnerColor.setSelection(0)
    }

    private fun savePrinter(){
        checkTextField(binding.fragmentPrinterCreateEdittextMake)
        checkTextField(binding.fragmentPrinterCreateEdittextModel)
        checkTextField(binding.fragmentPrinterCreateEdittextSerial)
        checkTextField(binding.fragmentPrinterCreateEdittextOwner)
        checkTextField(binding.fragmentPrinterCreateEdittextDept)
        checkTextField(binding.fragmentPrinterCreateEdittextLocation)
        checkTextField(binding.fragmentPrinterCreateEdittextFloor)
        checkTextField(binding.fragmentPrinterCreateEdittextIp)

        val make : String = binding.fragmentPrinterCreateEdittextMake.text.toString()
        val model : String = binding.fragmentPrinterCreateEdittextModel.text.toString()
        val serial : String = binding.fragmentPrinterCreateEdittextSerial.text.toString()
        val status : Int  = binding.fragmentPrinterCreateSpinnerStatus.selectedItemPosition
        val color : Int  = binding.fragmentPrinterCreateSpinnerColor.selectedItemPosition
        val owner : String  = binding.fragmentPrinterCreateEdittextOwner.text.toString()
        val dept : String  = binding.fragmentPrinterCreateEdittextDept.text.toString()
        val location : String  = binding.fragmentPrinterCreateEdittextLocation.text.toString()
        val floor : String  = binding.fragmentPrinterCreateEdittextFloor.text.toString()
        val ip : String  = binding.fragmentPrinterCreateEdittextIp.text.toString()

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