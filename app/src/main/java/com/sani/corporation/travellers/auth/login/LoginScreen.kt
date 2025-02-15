package com.sani.corporation.travellers.auth.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sani.corporation.travellers.R
import com.sani.corporation.travellers.TravellersTheme
import com.sani.corporation.travellers.auth.PasswordField
import com.sani.corporation.travellers.auth.UsernameField

@Composable
fun LoginScreen(
    modifier: Modifier,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(all = dimensionResource(id = R.dimen.horizontal_margin))
            .verticalScroll(rememberScrollState())
    ) {
        UsernameField(
            username = uiState.username,
            onUsernameChange = viewModel::updateUsername
        )
        PasswordField(
            password = uiState.password,
            onPasswordChange = viewModel::updatePassword
        )
        FilledTonalButton(
            onClick = viewModel::onClickLogin,
            enabled = uiState.loginButtonEnabled
            ) {
            Text(stringResource(R.string.login_button_text))
        }
    }
}

@Preview(device = Devices.NEXUS_6P, showSystemUi = true)
@Composable
fun Preview() {
    TravellersTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            LoginScreen(Modifier)
        }
    }
}
