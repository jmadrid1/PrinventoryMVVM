package com.example.prinventory_mvvm.ui.vendor

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
import androidx.navigation.fragment.navArgs
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.models.Vendor
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.android.synthetic.main.fragment_vendor_edit.*

@AndroidEntryPoint
class VendorEditFragment : Fragment(R.layout.fragment_vendor_edit) {

    companion object {
        const val EDITTEXT_TAG_ZIP = "zip"
    }

    private val viewModel : VendorViewModel by viewModels()
    private val args : VendorEditFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vendor = args.vendor

        initViews(vendor)
    }

    private fun initViews(vendor: Vendor){
        onBackButtonPress()

        fragment_vendor_edit_imageview_close.setOnClickListener {
            findNavController().popBackStack()
        }

        fragment_vendor_edit_button_submit.setOnClickListener {
            updateVendor(vendor)
        }
        fragment_vendor_edit_edittext_zipcode.tag = EDITTEXT_TAG_ZIP

        val name = vendor.name
        val phone = vendor.phone
        val email = vendor.email
        val street = vendor.street
        val city = vendor.city
        val state = vendor.state!!
        val zipcode = vendor.zipcode

        initSpinners(state!!)

        fragment_vendor_edit_edittext_name.setText(name)
        fragment_vendor_edit_edittext_phone.setText(phone)
        fragment_vendor_edit_edittext_email.setText(email)
        fragment_vendor_edit_edittext_street.setText(street)
        fragment_vendor_edit_edittext_city.setText(city)
        fragment_vendor_edit_edittext_zipcode.setText(zipcode)
    }

    @SuppressLint("ResourceType")
    private fun initSpinners(state: String){
        val statesAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_states, R.layout.spinner_dropdown_item)
        statesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        fragment_vendor_edit_spinner_states.adapter = statesAdapter

        val statePosition = statesAdapter.getPosition(state)
        fragment_vendor_edit_spinner_states.setSelection(statePosition)
    }

    private fun updateVendor(vendor: Vendor){
        checkTextField(fragment_vendor_edit_edittext_name)
        checkTextField(fragment_vendor_edit_edittext_phone)
        checkTextField(fragment_vendor_edit_edittext_email)
        checkTextField(fragment_vendor_edit_edittext_street)
        checkTextField(fragment_vendor_edit_edittext_city)
        checkTextField(fragment_vendor_edit_edittext_zipcode)

        val name : String = fragment_vendor_edit_edittext_name.text.toString()
        val phone : String = fragment_vendor_edit_edittext_phone.text.toString()
        val email : String = fragment_vendor_edit_edittext_email.text.toString()
        val street : String = fragment_vendor_edit_edittext_street.text.toString()
        val city : String  = fragment_vendor_edit_edittext_city.text.toString()
        val state : String  = fragment_vendor_edit_spinner_states.selectedItem.toString()
        val zip : String  = fragment_vendor_edit_edittext_zipcode.text.toString()

        vendor.name = name
        vendor.phone = phone
        vendor.email = email
        vendor.street = street
        vendor.city = city
        vendor.state = state
        vendor.zipcode = zip

        viewModel.insertVendor(vendor)
        findNavController().navigate(R.id.vendorFragment)
    }

    private fun checkTextField(editText: EditText){
        val text: String = editText.text.toString()

        if(TextUtils.isEmpty(text)){
            if(editText.tag != EDITTEXT_TAG_ZIP){
                editText.setText("Not Specified")
            }else{
                editText.setText("-")
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