package com.lab4

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun NavigationBar() {
    var selectedTab by remember { mutableStateOf(NavigationTab.Cables) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == NavigationTab.Cables,
                    onClick = { selectedTab = NavigationTab.Cables },
                    icon = { Icon(Icons.Default.Settings, "Cables") },
                    label = { Text("Cable") }
                )
                NavigationBarItem(
                    selected = selectedTab == NavigationTab.ShortCircuitBusbar,
                    onClick = { selectedTab = NavigationTab.ShortCircuitBusbar },
                    icon = { Icon(Icons.Default.Settings, "Short Circuit") },
                    label = { Text("SC Current") }
                )
                NavigationBarItem(
                    selected = selectedTab == NavigationTab.ShortCircuitSubstation,
                    onClick = { selectedTab = NavigationTab.ShortCircuitSubstation },
                    icon = { Icon(Icons.Default.Settings, "Power Network") },
                    label = { Text("Network") }
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (selectedTab) {
                NavigationTab.Cables -> CablesCalculator()
                NavigationTab.ShortCircuitBusbar -> ShortCircuitBusbarCalculator()
                NavigationTab.ShortCircuitSubstation -> ShortCircuitSubstationCalculator()
            }
        }
    }
}

enum class NavigationTab {
    Cables,
    ShortCircuitBusbar,
    ShortCircuitSubstation
}