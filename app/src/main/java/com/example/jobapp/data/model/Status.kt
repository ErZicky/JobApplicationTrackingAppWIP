package com.example.jobapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "statuses")
data class Status(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val status : String
)
