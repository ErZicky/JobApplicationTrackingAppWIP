package com.example.jobapp.data.model

import android.graphics.drawable.BitmapDrawable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "applications")
data class Application(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val company: String,
    val role: String,
    val status: String,
    val date: Long,
    val link: String,
    val companyWebsite: String?,


    )
