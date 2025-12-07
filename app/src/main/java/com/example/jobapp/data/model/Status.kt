package com.example.jobapp.data.model

import androidx.room.Entity

@Entity(tableName = "statuses")
data class Status(

    val status : String
)
