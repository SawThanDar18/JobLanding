package co.nexlabs.betterhr.joblanding.network.register

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: String) : UiState()
    data class Error(val errorMessage: String) : UiState()
}

class RegisterViewModel(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _uiStateForVerify = MutableStateFlow<UiState>(UiState.Loading)
    val uiStateForVerify: StateFlow<UiState> = _uiStateForVerify

    fun requestOTP(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response = registerRepository.requestOTP(phoneNumber)
                _uiState.value = UiState.Success(response.data.response.message)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun verifyOTP(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response = registerRepository.verifyOTP(code)
                if (response.data.verifyPhoneNumber.token == null) {
                    _uiStateForVerify.value = UiState.Error("Error: ${response.data.verifyPhoneNumber.message}")
                } else {
                    _uiStateForVerify.value = UiState.Success(
                        "token>>${response.data.verifyPhoneNumber.token}"
                    )
                }
            } catch (e: Exception) {
                _uiStateForVerify.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

}