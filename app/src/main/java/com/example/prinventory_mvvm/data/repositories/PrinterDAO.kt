package com.example.prinventory_mvvm.data.repositories

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.prinventory_mvvm.models.Printer

@Dao
interface PrinterDAO {

    companion object {
        const val TABLE_PRINTER = "printers"
    }

    @Query("SELECT * FROM $TABLE_PRINTER")
    fun getAllPrinters(): LiveData<List<Printer>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(printer: Printer)

    @Delete
    suspend fun delete(printer: Printer)

}
