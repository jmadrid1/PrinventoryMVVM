package com.example.prinventory_mvvm.ui.vendor

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.FragmentVendorCreateBinding
import com.example.prinventory_mvvm.databinding.FragmentVendorDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VendorDetailFragment : Fragment(R.layout.fragment_vendor_detail) {

    companion object {
        const val BUNDLE_KEY = "vendor"
    }

    private var _binding: FragmentVendorDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel : VendorViewModel by viewModels()
    private val args : VendorDetailFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVendorDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackButtonPress()
        val vendor = args.vendor

        binding.fragmentVendorDetailImageviewClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fragmentVendorDetailImageviewDelete.setOnClickListener {
            viewModel.deleteVendor(vendor)
            findNavController().popBackStack()
        }

        binding.fragmentVendorDetailButtonEdit.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable(BUNDLE_KEY, vendor)
            }
            findNavController().navigate(R.id.action_vendorDetailFragment_to_vendorEditFragment, bundle)
        }

        binding.fragmentVendorDetailImageviewPhone.setOnClickListener {
            val phoneIntent = Intent(Intent.ACTION_DIAL)
            phoneIntent.data = Uri.parse("tel:" + vendor.phone)
            try {
                startActivity(phoneIntent)
            } catch (e: Error) {
                val dialog = AlertDialog.Builder(view.context)
                dialog
                        .setTitle(R.string.dialog_title_call_vendor)
                        .setMessage(R.string.dialog_message_call_vendor)
                        .setPositiveButton(R.string.btn_close, null)
                        .create()
                        .show()
            }
        }

        binding.fragmentVendorDetailImageviewMessage.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:" + vendor.email)
            try {
                startActivity(emailIntent)
            } catch (e: ActivityNotFoundException) {
                val dialog = AlertDialog.Builder(view.context)
                dialog
                        .setTitle(R.string.dialog_title_email_vendor)
                        .setMessage(R.string.dialog_message_email_vendor)
                        .setPositiveButton(R.string.btn_close, null)
                        .create()
                        .show()
            }
        }

        val name = vendor.name
        val phone = vendor.phone
        val email = vendor.email
        val street = vendor.street
        val state = vendor.state
        val zip = vendor.zipcode

        binding.fragmentVendorDetailTextviewName.text = name
        binding.fragmentVendorDetailTextviewPhone.text = phone
        binding.fragmentVendorDetailTextviewEmail.text = email
        binding.fragmentVendorDetailTextviewStreet.text = street
        binding.fragmentVendorDetailTextviewState.text = state
        binding.fragmentVendorDetailTextviewZip.text = zip
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