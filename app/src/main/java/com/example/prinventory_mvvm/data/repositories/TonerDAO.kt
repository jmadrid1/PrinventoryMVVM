package com.example.prinventory_mvvm.data.repositories

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.prinventory_mvvm.models.Toner

@Dao
interface TonerDAO {

    companion object {
        const val TABLE_TONER = "toners"
    }

    @Query("SELECT * FROM $TABLE_TONER")
    fun getAllToners(): LiveData<List<Toner>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(toner: Toner)

    @Delete
    suspend fun delete(toner: Toner)
}