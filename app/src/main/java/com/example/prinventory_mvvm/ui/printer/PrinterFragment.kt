package com.example.prinventory_mvvm.ui.printer

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.adapters.PrinterAdapter
import com.example.prinventory_mvvm.models.Printer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_printer.*

@AndroidEntryPoint
class PrinterFragment : Fragment(R.layout.fragment_printer) {

    companion object {
        const val BUNDLE_KEY = "printer"
    }

    private val viewModel : PrinterViewModel by viewModels()
    private lateinit var printerAdapter : PrinterAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        onBackButtonPress()

        printerAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(BUNDLE_KEY, it)
            }
            findNavController().navigate(R.id.action_printerFragment_to_printerDetailFragment, bundle)
        }

        viewModel.getPrinters.observe(viewLifecycleOwner) {
            hideEmptyInventoryPrompts(it)
            printerAdapter.differ.submitList(it)
        }
    }

    private fun setupRecyclerView(){
        printerAdapter = PrinterAdapter()
        fragment_printer_recyclerview.apply{
            adapter = printerAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun hideEmptyInventoryPrompts(printers: List<Printer>){
        if(printers.isEmpty()){
            fragment_printer_textview_empty_msg_1.visibility = View.VISIBLE
            fragment_printer_textview_empty_msg_2.visibility = View.VISIBLE
            fragment_printer_recyclerview.visibility = View.GONE
        }else {
            fragment_printer_textview_empty_msg_1.visibility = View.GONE
            fragment_printer_textview_empty_msg_2.visibility = View.GONE
            fragment_printer_recyclerview.visibility = View.VISIBLE
        }
    }

    private fun onBackButtonPress(){
        val callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                activity!!.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

}