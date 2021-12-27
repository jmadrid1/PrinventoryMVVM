package com.example.prinventory_mvvm.ui.printer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.adapters.PrinterAdapter
import com.example.prinventory_mvvm.databinding.FragmentPrinterBinding
import com.example.prinventory_mvvm.models.Printer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PrinterFragment : Fragment(R.layout.fragment_printer) {

    companion object {
        const val BUNDLE_KEY = "printer"
    }

    private var _binding: FragmentPrinterBinding? = null
    private val binding get() = _binding!!

    private val viewModel : PrinterViewModel by viewModels()
    private lateinit var printerAdapter : PrinterAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPrinterBinding.inflate(inflater, container, false)
        return binding.root
    }

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
        binding.fragmentPrinterRecyclerview.apply{
            adapter = printerAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun hideEmptyInventoryPrompts(printers: List<Printer>){
        if(printers.isEmpty()){
            binding.fragmentPrinterTextviewEmptyMsg1.visibility = View.VISIBLE
            binding.fragmentPrinterTextviewEmptyMsg2.visibility = View.VISIBLE
            binding.fragmentPrinterRecyclerview.visibility = View.GONE
        }else {
            binding.fragmentPrinterTextviewEmptyMsg1.visibility = View.GONE
            binding.fragmentPrinterTextviewEmptyMsg2.visibility = View.GONE
            binding.fragmentPrinterRecyclerview.visibility = View.VISIBLE
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