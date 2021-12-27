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
import com.example.prinventory_mvvm.R
import com.example.prinventory_mvvm.databinding.FragmentTonerBinding
import com.example.prinventory_mvvm.databinding.FragmentVendorCreateBinding
import com.example.prinventory_mvvm.models.Vendor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VendorCreateFragment : Fragment(R.layout.fragment_vendor_create) {

    companion object {
        const val EDITTEXT_TAG_ZIP = "zip"
    }

    private var _binding: FragmentVendorCreateBinding? = null
    private val binding get() = _binding!!

    private val viewModel : VendorViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVendorCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews(){
        onBackButtonPress()

        binding.fragmentVendorCreateImageviewClose.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.fragmentVendorCreateButtonSubmit.setOnClickListener {
            saveVendor()
        }
        binding.fragmentVendorCreateEdittextZipcode.tag = EDITTEXT_TAG_ZIP

        initSpinners()
    }

    @SuppressLint("ResourceType")
    private fun initSpinners(){
        val statesAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(requireContext(), R.array.array_states, R.layout.spinner_dropdown_item)
        statesAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.fragmentVendorCreateSpinnerStates.adapter = statesAdapter
        binding.fragmentVendorCreateSpinnerStates.setSelection(0)
    }

    private fun saveVendor(){
        checkTextField(binding.fragmentVendorCreateEdittextName)
        checkTextField(binding.fragmentVendorCreateEdittextPhone)
        checkTextField(binding.fragmentVendorCreateEdittextEmail)
        checkTextField(binding.fragmentVendorCreateEdittextStreet)
        checkTextField(binding.fragmentVendorCreateEdittextCity)
        checkTextField(binding.fragmentVendorCreateEdittextZipcode)

        val name : String = binding.fragmentVendorCreateEdittextName.text.toString()
        val phone : String = binding.fragmentVendorCreateEdittextPhone.text.toString()
        val email : String = binding.fragmentVendorCreateEdittextEmail.text.toString()
        val street : String = binding.fragmentVendorCreateEdittextStreet.text.toString()
        val city : String  = binding.fragmentVendorCreateEdittextCity.text.toString()
        val state : String  = binding.fragmentVendorCreateSpinnerStates.selectedItem.toString()
        val zip : String  = binding.fragmentVendorCreateEdittextZipcode.text.toString()
        
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