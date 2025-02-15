package com.sani.corporation.travellers.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.sani.corporation.travellers.R
import com.sani.corporation.travellers.TravellersTheme

@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean = false
) {
    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = { Text(stringResource(id = R.string.password_hint)) },
        modifier = Modifier
            .fillMaxWidth(),
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onSecondary
        ),
        visualTransformation =  if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@Composable
fun UsernameField(
    username: String,
    onUsernameChange: (String) -> Unit
) {

    OutlinedTextField(
        value = username,
        onValueChange = onUsernameChange,
        placeholder = { Text(stringResource(id = R.string.username_hint)) },
        modifier = Modifier
            .fillMaxWidth(),
        maxLines = 1,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@Preview(device = Devices.NEXUS_6P, showSystemUi = true)
@Composable
fun PreviewUsernameField() {
    TravellersTheme {
        Box(modifier = Modifier.background(Color.Red)) {
            UsernameField(
                username = "",
                {}
            )
        }
    }
}

@Preview(device = Devices.NEXUS_6P, showSystemUi = true)
@Composable
fun PreviewLoginField() {
    TravellersTheme {
        Box(modifier = Modifier.background(Color.Red)) {
            PasswordField(
                password = "",
                {}
            )
        }
    }
}


