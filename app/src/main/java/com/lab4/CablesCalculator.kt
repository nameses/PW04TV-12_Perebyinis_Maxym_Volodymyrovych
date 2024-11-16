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

@Composable
fun CablesCalculator() {
    var sm by remember { mutableStateOf("1300") }
    var ik by remember { mutableStateOf("2500") }
    var tf by remember { mutableStateOf("2.5") }
    var results by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = sm,
            onValueChange = { sm = it },
            label = { Text("Розрахункове навантаження Sm, кВ)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        OutlinedTextField(
            value = ik,
            onValueChange = { ik = it },
            label = { Text("Струм КЗ Iк, А") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        OutlinedTextField(
            value = tf,
            onValueChange = { tf = it },
            label = { Text("Фіктивний час вимикання навантаження Tf, c") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )

        Button(
            onClick = {
                results = calculate(
                    sm.toDoubleOrNull() ?: 0.0,
                    ik.toDoubleOrNull() ?: 0.0,
                    tf.toDoubleOrNull() ?: 0.0
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }

        Text(results)
    }
}

fun calculate(sm: Double, ik: Double, tf: Double): String {
    val im = sm / (2.0 * kotlin.math.sqrt(3.0) * 10.0)
    val imPa = 2.0 * im
    val sEk = im / 1.4
    val sVsS = ik * kotlin.math.sqrt(tf) / 92

    return "Струм КЗ нормальний режим: ${String.format("%.1f", im)}\n" +
            "Струм КЗ аварійний режим: ${String.format("%.1f", imPa)}\n" +
            "Економічний переріз: ${String.format("%.1f", sEk)}\n" +
            "Мінімальний переріз: ${String.format("%.1f", sVsS)}"
}