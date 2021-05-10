package com.example.prinventory_mvvm.data.repositories

import com.example.prinventory_mvvm.models.Printer
import javax.inject.Inject

class PrinterRepository @Inject
    constructor(private val printerDao: PrinterDAO) {

    fun allPrinters() = printerDao.getAllPrinters()

    suspend fun insertPrinter(printer: Printer) = printerDao.insert(printer)

    suspend fun deletePrinter(printer: Printer) = printerDao.delete(printer)

}