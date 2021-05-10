package com.example.prinventory_mvvm.ui.vendor

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.adapters.VendorAdapter
import com.example.prinventory_mvvm.models.Vendor

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_vendor.*

@AndroidEntryPoint
class VendorFragment : Fragment(R.layout.fragment_vendor) {

    companion object {
        const val BUNDLE_KEY = "vendor"
    }

    private val viewModel : VendorViewModel by viewModels()
    private lateinit var vendorAdapter : VendorAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.getVendors.observe(viewLifecycleOwner, {
            hideEmptyInventoryPrompts(it)
            vendorAdapter.differ.submitList(it)
        })

        vendorAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable(BUNDLE_KEY, it)
            }
            findNavController().navigate(R.id.action_vendorFragment_to_vendorDetailFragment, bundle)
        }
    }

    private fun setupRecyclerView() = fragment_vendor_recyclerview.apply{
        vendorAdapter = VendorAdapter()
        adapter = vendorAdapter
        layoutManager = LinearLayoutManager(context)
    }

    private fun hideEmptyInventoryPrompts(vendors: List<Vendor>){
        if(vendors.isEmpty()){
            fragment_vendor_textview_empty_msg_1.visibility = View.VISIBLE
            fragment_vendor_textview_empty_msg_2.visibility = View.VISIBLE
            fragment_vendor_recyclerview.visibility = View.GONE
        }else {
            fragment_vendor_textview_empty_msg_1.visibility = View.GONE
            fragment_vendor_textview_empty_msg_2.visibility = View.GONE
            fragment_vendor_recyclerview.visibility = View.VISIBLE
        }
    }

}