package com.example.prinventory_mvvm.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "printers")
data class Printer(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "make")
    var make: String?,
    @ColumnInfo(name = "model")
    var model: String?,
    @ColumnInfo(name = "serial")
    var serial: String?,
    @ColumnInfo(name = "status")
    var status: Int?,
    @ColumnInfo(name = "color")
    var color: Int?,
    @ColumnInfo(name = "owner")
    var owner: String?,
    @ColumnInfo(name = "department")
    var dept: String?,
    @ColumnInfo(name = "location")
    var location: String?,
    @ColumnInfo(name = "floor")
    var floor: String?,
    @ColumnInfo(name = "ip")
    var ip: String?) : Serializable

