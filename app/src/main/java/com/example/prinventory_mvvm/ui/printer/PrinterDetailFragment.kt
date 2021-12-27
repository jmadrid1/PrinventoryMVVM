package com.example.prinventory_mvvm.ui.printer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.FragmentPrinterDetailBinding
import com.example.prinventory_mvvm.databinding.FragmentTonerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrinterDetailFragment : Fragment(R.layout.fragment_printer_detail) {

    companion object {
        const val BUNDLE_KEY = "printer"
    }

    private var _binding: FragmentPrinterDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel : PrinterViewModel by viewModels()
    private val args : PrinterDetailFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrinterDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val printer = args.printer
        onBackButtonPress()

        binding.fragmentPrinterDetailImageviewClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fragmentPrinterDetailImageviewDelete.setOnClickListener {
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
                    binding.fragmentPrinterDetailFrameStatus,
                    ContextCompat.getColorStateList(
                            view.context,
                            R.color.status_red
                    ))

            binding.fragmentPrinterDetailImageviewStatus.setImageResource(R.drawable.ic_close)
        }else{
            ViewCompat.setBackgroundTintList(
                binding.fragmentPrinterDetailFrameStatus,
                    ContextCompat.getColorStateList(
                            view.context,
                            R.color.status_green
                    ))
            binding.fragmentPrinterDetailImageviewStatus.setImageResource(R.drawable.ic_status_active)
        }

        if(make.equals("Not Specified") || model.equals("Not Specified")){
            binding.fragmentPrinterDetailTextviewMake.text = printer.make
        }else{
            binding.fragmentPrinterDetailTextviewMake.text = printer.make + " " + printer.model
        }

        binding.fragmentPrinterDetailTextviewSerial.text = serial
        binding.fragmentPrinterDetailTextviewOwner.text = owner
        binding.fragmentPrinterDetailTextviewDept.text =dept
        binding.fragmentPrinterDetailTextviewLocation.text = location
        binding.fragmentPrinterDetailTextviewFloor.text = floor
        binding.fragmentPrinterDetailTextviewIp.text = ip

        binding.fragmentPrinterDetailButtonEdit.setOnClickListener {
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