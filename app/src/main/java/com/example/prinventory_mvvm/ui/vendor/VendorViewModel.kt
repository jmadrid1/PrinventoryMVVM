package com.example.prinventory_mvvm.ui.vendor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prinventory_mvvm.data.repositories.VendorRepository
import com.example.prinventory_mvvm.models.Vendor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VendorViewModel @Inject constructor(
        private val vendorRepository: VendorRepository): ViewModel() {

    val getVendors = vendorRepository.allVendors()

    fun insertVendor(vendor: Vendor) = viewModelScope.launch {
        vendorRepository.insertVendor(vendor)
    }

    fun deleteVendor(vendor: Vendor) = viewModelScope.launch {
        vendorRepository.deleteVendor(vendor)
    }

}