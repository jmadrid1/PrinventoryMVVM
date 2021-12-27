package com.example.prinventory_mvvm.ui.vendor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.adapters.VendorAdapter
import com.example.prinventory_mvvm.databinding.FragmentVendorBinding
import com.example.prinventory_mvvm.databinding.FragmentVendorDetailBinding
import com.example.prinventory_mvvm.models.Vendor

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VendorFragment : Fragment(R.layout.fragment_vendor) {

    companion object {
        const val BUNDLE_KEY = "vendor"
    }

    private var _binding: FragmentVendorBinding? = null
    private val binding get() = _binding!!

    private val viewModel : VendorViewModel by viewModels()
    private lateinit var vendorAdapter : VendorAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVendorBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    private fun setupRecyclerView() = binding.fragmentVendorRecyclerview.apply{
        vendorAdapter = VendorAdapter()
        adapter = vendorAdapter
        layoutManager = LinearLayoutManager(context)
    }

    private fun hideEmptyInventoryPrompts(vendors: List<Vendor>){
        if(vendors.isEmpty()){
            binding.fragmentVendorTextviewEmptyMsg1.visibility = View.VISIBLE
            binding.fragmentVendorTextviewEmptyMsg1.visibility = View.VISIBLE
            binding.fragmentVendorRecyclerview.visibility = View.GONE
        }else {
            binding.fragmentVendorTextviewEmptyMsg1.visibility = View.GONE
            binding.fragmentVendorTextviewEmptyMsg2.visibility = View.GONE
            binding.fragmentVendorRecyclerview.visibility = View.VISIBLE
        }
    }

}