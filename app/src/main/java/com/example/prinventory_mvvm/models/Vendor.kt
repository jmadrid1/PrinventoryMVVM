package com.example.prinventory_mvvm.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "vendors")
data class Vendor(
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null,
        @ColumnInfo(name = "name")
        var name: String?,
        @ColumnInfo(name = "phone")
        var phone: String?,
        @ColumnInfo(name = "email")
        var email: String?,
        @ColumnInfo(name = "street")
        var street: String?,
        @ColumnInfo(name = "city")
        var city: String?,
        @ColumnInfo(name = "state")
        var state: String?,
        @ColumnInfo(name = "zipcode")
        var zipcode: String?) : Serializable