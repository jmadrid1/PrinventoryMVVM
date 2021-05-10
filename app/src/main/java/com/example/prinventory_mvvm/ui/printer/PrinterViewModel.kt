package com.example.prinventory_mvvm.ui.printer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prinventory_mvvm.data.repositories.PrinterRepository
import com.example.prinventory_mvvm.models.Printer
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PrinterViewModel @Inject constructor(
    private val printerRepository: PrinterRepository): ViewModel() {

    val getPrinters = printerRepository.allPrinters()

    fun insertPrinter(printer: Printer) = viewModelScope.launch {
        printerRepository.insertPrinter(printer)
    }

    fun deletePrinter(printer: Printer) = viewModelScope.launch {
        printerRepository.deletePrinter(printer)
    }
}