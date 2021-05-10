package com.example.prinventory_mvvm.ui.toner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prinventory_mvvm.data.repositories.TonerRepository
import com.example.prinventory_mvvm.models.Toner
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TonerViewModel @Inject constructor(
        private val tonerRepository: TonerRepository): ViewModel() {

    val getToners = tonerRepository.allToners()

    fun insertToner(toner: Toner) = viewModelScope.launch {
        tonerRepository.insertToner(toner)
    }

    fun deleteToner(toner: Toner) = viewModelScope.launch {
        tonerRepository.deleteToner(toner)
    }

    val usesColor = MutableLiveData<Boolean>()

}