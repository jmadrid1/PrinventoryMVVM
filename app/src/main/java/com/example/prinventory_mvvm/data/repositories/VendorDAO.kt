package com.example.prinventory_mvvm.data.repositories

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.prinventory_mvvm.models.Vendor

@Dao
interface VendorDAO {

    companion object {
        const val TABLE_VENDOR = "vendors"
    }

    @Query("SELECT * FROM $TABLE_VENDOR")
    fun getAllVendors(): LiveData<List<Vendor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vendor: Vendor)

    @Delete
    suspend fun delete(vendor: Vendor)
}