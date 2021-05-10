package com.example.prinventory_mvvm.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.prinventory_mvvm.data.repositories.PrinterDAO
import com.example.prinventory_mvvm.data.repositories.TonerDAO
import com.example.prinventory_mvvm.data.repositories.VendorDAO
import com.example.prinventory_mvvm.models.Printer
import com.example.prinventory_mvvm.models.Toner
import com.example.prinventory_mvvm.models.Vendor

@Database(entities = [Printer::class, Toner::class, Vendor::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun printerDao(): PrinterDAO

    abstract fun tonerDao(): TonerDAO

    abstract fun vendorDao(): VendorDAO

}
