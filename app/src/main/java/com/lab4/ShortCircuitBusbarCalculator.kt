package com.lab4

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun ShortCircuitBusbarCalculator() {
    var power by remember { mutableStateOf("200") }
    var results by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = power,
            onValueChange = { power = it },
            label = { Text("Потужність КЗ, МВ * А)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Button(
            onClick = {
                results = calculate(power.toDoubleOrNull() ?: 0.0)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }
    }

    Text(results)
}

fun calculate(power: Double): String {
    val xc = 10.5.pow(2) / power
    val xt = (10.5 / 100) * (10.5.pow(2) / 6.3)
    val ip0 = 10.5 / (sqrt(3.0) * (xc + xt))

    return "Опора елементу xc: ${String.format("%.2f", xc)}\n" +
            "Опора елементу xt: ${String.format("%.2f", xt)}\n" +
            "Сумарний опір: ${String.format("%.2f", xc + xt)}\n" +
            "Початкове діюче значення струму: ${String.format("%.1f", ip0)}"
}