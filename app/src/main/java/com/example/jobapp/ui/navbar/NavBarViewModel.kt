package com.example.jobapp.ui.navbar

import androidx.appcompat.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import  com.example.jobapp.data.model.NavBarItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavBarViewModel : ViewModel() {

    val items = listOf(
        NavBarItem( com.example.jobapp.R.drawable.linki),
        NavBarItem( com.example.jobapp.R.drawable.linki),
        NavBarItem( com.example.jobapp.R.drawable.linki),
    );

    // to keep track of the current selection
    private val _selectedIndex = MutableStateFlow(0)

    //conversion of the mutable (writable/readable) in a standard flow (only readable) to expose
    val selectedIndex : StateFlow<Int> = _selectedIndex.asStateFlow();

    fun onTabSelected(index: Int)
    {
        _selectedIndex.value = index;
    }

}