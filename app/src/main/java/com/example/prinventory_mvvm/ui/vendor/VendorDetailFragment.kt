package com.example.prinventory_mvvm.ui.vendor

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.prinventory_mvvm.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_vendor_detail.*

@AndroidEntryPoint
class VendorDetailFragment : Fragment(R.layout.fragment_vendor_detail) {

    companion object {
        const val BUNDLE_KEY = "vendor"
    }

    private val viewModel : VendorViewModel by viewModels()
    private val args : VendorDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackButtonPress()
        val vendor = args.vendor

        fragment_vendor_detail_imageview_close.setOnClickListener {
            findNavController().popBackStack()
        }

        fragment_vendor_detail_imageview_delete.setOnClickListener {
            viewModel.deleteVendor(vendor)
            findNavController().popBackStack()
        }

        fragment_vendor_detail_button_edit.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable(BUNDLE_KEY, vendor)
            }
            findNavController().navigate(R.id.action_vendorDetailFragment_to_vendorEditFragment, bundle)
        }

        fragment_vendor_detail_imageview_phone.setOnClickListener {
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

        fragment_vendor_detail_imageview_message.setOnClickListener {
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

        fragment_vendor_detail_textview_name.text = name
        fragment_vendor_detail_textview_phone.text = phone
        fragment_vendor_detail_textview_email.text = email
        fragment_vendor_detail_textview_street.text = street
        fragment_vendor_detail_textview_state.text = state
        fragment_vendor_detail_textview_zip.text = zip
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