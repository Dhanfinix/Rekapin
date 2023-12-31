package com.dhandev.rekapin.presentation.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dhandev.rekapin.ui.theme.BlueMain
import com.dhandev.rekapin.ui.theme.BlueSecondary
import com.dhandev.rekapin.ui.theme.RekapinTheme
import com.dhandev.rekapin.ui.theme.raleway

@Composable
fun TextFieldView(
    modifier : Modifier = Modifier,
    title: String,
    setData: String = "",
    value: (String) -> Unit
) {
    var data by remember { mutableStateOf(setData) }
    value.invoke(data)
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            modifier = Modifier.padding(bottom = 6.dp),
            text = title,
            style = raleway(fontSize = 14, weight = FontWeight.Normal))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = data,
            onValueChange = { newInput -> data = newInput},
            placeholder = {
                Text(text = "Type here...", color = Color.LightGray)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = BlueSecondary,
                cursorColor = BlueMain
            )
        )
    }
}

@Preview
@Composable
fun PreviewTextField(){
    var typed by remember { mutableStateOf("") }

    RekapinTheme {
        Surface {
            Column {
                TextFieldView(
                    title = "Nama kamu",
                    modifier = Modifier.padding(16.dp),
                    value = {typed = it}
                )
                TextFieldView(
                    title = "Nama kamu",
                    modifier = Modifier.padding(16.dp),
                    value = {typed = it}
                )
            }
        }
    }
}