package com.test.testcompose.ui.component

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun DatePicker() {
    val context = LocalContext.current
    var date by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    Button(onClick = {
        DatePickerDialog(
            context,
            { _: DatePicker, y: Int, m: Int, d: Int ->
                date = "$d/${m + 1}/$y"
            },
            year, month, day
        ).show()
    }) {
        Text(if (date.isEmpty()) "Pick Date" else date)
    }
}