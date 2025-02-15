package com.sani.corporation.travellers

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


sealed interface LoginState{
    object UNKNOWN: LoginState
    object LOGGED_IN: LoginState
    object NOT_LOGGED_IN: LoginState
}

@HiltViewModel
class TravellersViewModel @Inject constructor(
    preferences: DataStore<Preferences>
): ViewModel() {

    val loginState: StateFlow<LoginState> = preferences.data.map { preferences ->
        if (preferences[JWT_KEY] != null) LoginState.LOGGED_IN else LoginState.NOT_LOGGED_IN
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        LoginState.UNKNOWN
    )



}

val JWT_KEY = stringPreferencesKey("JWT")