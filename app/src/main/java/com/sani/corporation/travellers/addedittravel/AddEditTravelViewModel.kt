package com.sani.corporation.travellers.addedittravel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sani.corporation.travellers.R
import com.sani.corporation.travellers.data.TravelRepository
import com.sani.corporation.travellers.data.source.models.Travel
import com.sani.corporation.travellers.util.network.AsyncState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddEditTravelUiState(
    val title: String = "",
    val description: String = "",
    val imageUri: Any? = R.drawable.ic_default_group_background,
    val isTravelCompleted: Boolean = false,
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val isTravelSaved: Boolean = false
)

@HiltViewModel
class AddTravelScreenViewModel @Inject constructor(
    val travelRepository: TravelRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(AddEditTravelUiState())
    val uiState: StateFlow<AddEditTravelUiState> = _uiState.asStateFlow()


    fun saveTravel() {
        viewModelScope.launch {
            travelRepository.addTravel(Travel(title = uiState.value.title, description = uiState.value.description)).collect { state ->
                when(state) {
                    is AsyncState.SuccessEmpty -> _uiState.update { it.copy(userMessage = R.string.successfully_saved_travel_message) }
                    is AsyncState.Error -> _uiState.update { it.copy(userMessage = R.string.error_saving_travel) }
                    else -> {}
                }

            }
        }
    }

    fun snackbarMessageShown() {
        _uiState.update {
            it.copy(userMessage = null)
        }
    }

    fun updateTitle(newTitle: String) {
        _uiState.update {
            it.copy(title = newTitle)
        }
    }

    fun updateDescription(newDescription: String) {
        _uiState.update {
            it.copy(description = newDescription)
        }
    }

    fun updateImageUri(uri: Uri) {
        _uiState.update {
            it.copy(imageUri = uri)
        }
    }
}