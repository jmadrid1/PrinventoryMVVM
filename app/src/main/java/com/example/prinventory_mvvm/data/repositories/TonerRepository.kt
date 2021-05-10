package com.example.prinventory_mvvm.data.repositories

import com.example.prinventory_mvvm.models.Toner
import javax.inject.Inject

class TonerRepository @Inject
constructor(private val tonerDAO: TonerDAO) {

    fun allToners() = tonerDAO.getAllToners()

    suspend fun insertToner(toner: Toner) = tonerDAO.insert(toner)

    suspend fun deleteToner(toner: Toner) = tonerDAO.delete(toner)
}