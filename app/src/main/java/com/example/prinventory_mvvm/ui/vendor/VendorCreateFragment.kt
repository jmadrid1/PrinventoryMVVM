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
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.models.Vendor
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_vendor_create.*

@AndroidEntryPoint
class VendorCreateFragment : Fragment(R.layout.fragment_vendor_create) {

    companion object {
        const val EDITTEXT_TAG_ZIP = "zip"
    }

    private val viewModel : VendorViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews(){
        onBackButtonPress()

        fragment_vendor_create_imageview_close.setOnClickListener {
            findNavController().popBackStack()
        }

        fragment_vendor_create_button_submit.setOnClickListener {
            saveVendor()
        }
        fragment_vendor_create_edittext_zipcode.tag = EDITTEXT_TAG_ZIP

        initSpinners()
    }

    @SuppressLint("ResourceType")
    private fun initSpinners(){
        val statesAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_states, R.layout.spinner_dropdown_item)
        statesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        fragment_vendor_create_spinner_states.adapter = statesAdapter
        fragment_vendor_create_spinner_states.setSelection(0)
    }

    private fun saveVendor(){
        checkTextField(fragment_vendor_create_edittext_name)
        checkTextField(fragment_vendor_create_edittext_phone)
        checkTextField(fragment_vendor_create_edittext_email)
        checkTextField(fragment_vendor_create_edittext_street)
        checkTextField(fragment_vendor_create_edittext_city)
        checkTextField(fragment_vendor_create_edittext_zipcode)

        val name : String = fragment_vendor_create_edittext_name.text.toString()
        val phone : String = fragment_vendor_create_edittext_phone.text.toString()
        val email : String = fragment_vendor_create_edittext_email.text.toString()
        val street : String = fragment_vendor_create_edittext_street.text.toString()
        val city : String  = fragment_vendor_create_edittext_city.text.toString()
        val state : String  = fragment_vendor_create_spinner_states.selectedItem.toString()
        val zip : String  = fragment_vendor_create_edittext_zipcode.text.toString()
        
        val vendor = Vendor(
                name = name,
                phone = phone,
                email = email,
                street = street,
                city = city,
                state = state,
                zipcode = zip)

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