package com.lab4

import android.annotation.SuppressLint
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
fun ShortCircuitSubstationCalculator() {
    var rsn by remember { mutableStateOf("10.65") }
    var xsn by remember { mutableStateOf("24.02") }
    var rsnMin by remember { mutableStateOf("34.88") }
    var xsnMin by remember { mutableStateOf("65.68") }
    var results by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = rsn,
            onValueChange = { rsn = it },
            label = { Text("Rsn (Ω)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = xsn,
            onValueChange = { xsn = it },
            label = { Text("Xsn (Ω)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = rsnMin,
            onValueChange = { rsnMin = it },
            label = { Text("Rsn min (Ω)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = xsnMin,
            onValueChange = { xsnMin = it },
            label = { Text("Xsn min (Ω)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                results = calculate(
                    rsn.toDoubleOrNull() ?: 0.0,
                    xsn.toDoubleOrNull() ?: 0.0,
                    rsnMin.toDoubleOrNull() ?: 0.0,
                    xsnMin.toDoubleOrNull() ?: 0.0)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate")
        }

        Text(results)
    }
}

@SuppressLint("DefaultLocale")
fun calculate(rsn: Double, xsn: Double, rsnMin: Double, xsnMin: Double): String {
    val xt = (11.1 * 115.0.pow(2)) / (100 * 6.3)

    val rsh = rsn
    val xsh = xsn + xt
    val zsh = sqrt(rsh.pow(2) + xsh.pow(2))

    val rshMin = rsnMin
    val xshMin = xsnMin + xt
    val zshMin = sqrt(rshMin.pow(2) + xshMin.pow(2))

    val ish3 = (115.0 * 1000) / (sqrt(3.0) * zsh)
    val ish2 = ish3 * (sqrt(3.0) / 2)
    val ish3Min = (115.0 * 1000) / (sqrt(3.0) * zshMin)
    val ish2Min = ish3Min * (sqrt(3.0) / 2)

    val kpr = (11.0.pow(2)) / (115.0.pow(2))
    val rshN = rsh * kpr
    val xshN = xsh * kpr
    val zshN = sqrt(rshN.pow(2) + xshN.pow(2))
    val rshNMin = rshMin * kpr
    val xshNMin = xshMin * kpr
    val zshNMin = sqrt(rshNMin.pow(2) + xshNMin.pow(2))

    val ishN3 = (11.0 * 1000) / (sqrt(3.0) * zshN)
    val ishN2 = ishN3 * (sqrt(3.0) / 2)
    val ishN3Min = (11.0 * 1000) / (sqrt(3.0) * zshNMin)
    val ishN2Min = ishN3Min * (sqrt(3.0) / 2)

    val iL = 0.2 + 0.35 + 0.2 + 0.6 + 2.0 + 2.55 + 3.37 + 3.1
    val rL = iL * 0.64
    val xL = iL * 0.363

    val rsumN = rL + rshN
    val xsumN = xL + xshN
    val zsumN = sqrt(rsumN.pow(2) + xsumN.pow(2))
    val rsumNMin = rL + rshNMin
    val xsumNMin = xL + xshNMin
    val zsumNMin = sqrt(rsumNMin.pow(2) + xsumNMin.pow(2))

    val ilN3 = (11.0 * 1000) / (sqrt(3.0) * zsumN)
    val ilN2 = ilN3 * (sqrt(3.0) / 2)
    val ilN3Min = (11.0 * 1000) / (sqrt(3.0) * zsumNMin)
    val ilN2Min = ilN3Min * (sqrt(3.0) / 2)

    return "Шини 10 кВ з напругою 110 кВ:" +
        "\nТрифазний:" +
            "\nНормальний режим: ${String.format("%.1f", ish3)}" +
            "\nМінімальний режим: ${String.format("%.0f", ish3Min)}" +
        "\nДвофазний:" +
            "\nНормальний режим: ${String.format("%.0f", ish2)}" +
            "\nМінімальний режим: ${String.format("%.0f", ish2Min)}" +
        "\nДійсні струми КЗ на шинах 10 кВ:" +
        "\nТрифазний: " +
            "\nНормальний режим: ${String.format("%.0f", ishN3)}" +
            "\nМінімальний режим: ${String.format("%.0f", ishN3Min)}" +
        "\nДвофазний: " +
            "\nНормальний режим: ${String.format("%.0f", ishN2)}" +
            "\nМінімальний режим: ${String.format("%.0f", ishN2Min)} " +
        "\nСтруми Кз в точці 10:" +
        "\nТрифазний: " +
            "\nНормальний режим: ${String.format("%.0f", ilN3)}" +
            "\nМінімальний режим: ${String.format("%.0f", ilN3Min)}" +
        "\nДвофазний: " +
            "\nНормальний режим: ${String.format("%.0f", ilN2)}" +
            "\nМінімальний режим: ${String.format("%.0f", ilN2Min)}"
}