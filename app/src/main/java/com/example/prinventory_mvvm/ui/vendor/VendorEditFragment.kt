package com.example.prinventory_mvvm.ui.vendor

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
import androidx.navigation.fragment.navArgs
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.FragmentVendorDetailBinding
import com.example.prinventory_mvvm.databinding.FragmentVendorEditBinding
import com.example.prinventory_mvvm.models.Vendor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VendorEditFragment : Fragment(R.layout.fragment_vendor_edit) {

    companion object {
        const val EDITTEXT_TAG_ZIP = "zip"
    }

    private var _binding: FragmentVendorEditBinding? = null
    private val binding get() = _binding!!

    private val viewModel : VendorViewModel by viewModels()
    private val args : VendorEditFragmentArgs by navArgs()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVendorEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val vendor = args.vendor

        initViews(vendor)
    }

    private fun initViews(vendor: Vendor){
        onBackButtonPress()

        binding.fragmentVendorEditImageviewClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fragmentVendorEditButtonSubmit.setOnClickListener {
            updateVendor(vendor)
        }
        binding.fragmentVendorEditEdittextZipcode.tag = EDITTEXT_TAG_ZIP

        val name = vendor.name
        val phone = vendor.phone
        val email = vendor.email
        val street = vendor.street
        val city = vendor.city
        val state = vendor.state!!
        val zipcode = vendor.zipcode

        initSpinners(state!!)

        binding.fragmentVendorEditEdittextName.setText(name)
        binding.fragmentVendorEditEdittextPhone.setText(phone)
        binding.fragmentVendorEditEdittextEmail.setText(email)
        binding.fragmentVendorEditEdittextStreet.setText(street)
        binding.fragmentVendorEditEdittextCity.setText(city)
        binding.fragmentVendorEditEdittextZipcode.setText(zipcode)
    }

    @SuppressLint("ResourceType")
    private fun initSpinners(state: String){
        val statesAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_states, R.layout.spinner_dropdown_item)
        statesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.fragmentVendorEditSpinnerStates.adapter = statesAdapter

        val statePosition = statesAdapter.getPosition(state)
        binding.fragmentVendorEditSpinnerStates.setSelection(statePosition)
    }

    private fun updateVendor(vendor: Vendor){
        checkTextField(binding.fragmentVendorEditEdittextName)
        checkTextField(binding.fragmentVendorEditEdittextPhone)
        checkTextField(binding.fragmentVendorEditEdittextEmail)
        checkTextField(binding.fragmentVendorEditEdittextStreet)
        checkTextField(binding.fragmentVendorEditEdittextCity)
        checkTextField(binding.fragmentVendorEditEdittextZipcode)

        val name : String = binding.fragmentVendorEditEdittextName.text.toString()
        val phone : String = binding.fragmentVendorEditEdittextPhone.text.toString()
        val email : String = binding.fragmentVendorEditEdittextEmail.text.toString()
        val street : String = binding.fragmentVendorEditEdittextStreet.text.toString()
        val city : String  = binding.fragmentVendorEditEdittextCity.text.toString()
        val state : String  = binding.fragmentVendorEditSpinnerStates.selectedItem.toString()
        val zip : String  = binding.fragmentVendorEditEdittextZipcode.text.toString()

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