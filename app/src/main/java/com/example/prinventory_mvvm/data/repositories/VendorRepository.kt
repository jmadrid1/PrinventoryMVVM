package com.example.prinventory_mvvm.data.repositories

import com.example.prinventory_mvvm.models.Vendor
import javax.inject.Inject

class VendorRepository @Inject
constructor(private val VendorDAO: VendorDAO) {

    fun allVendors() = VendorDAO.getAllVendors()

    suspend fun insertVendor(vendor: Vendor) = VendorDAO.insert(vendor)

    suspend fun deleteVendor(vendor: Vendor) = VendorDAO.delete(vendor)
}