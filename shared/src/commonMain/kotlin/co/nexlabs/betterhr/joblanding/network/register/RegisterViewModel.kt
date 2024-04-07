package co.nexlabs.betterhr.joblanding.network.register

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class RegisterViewModel(
    private val registerRepository: RegisterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUIState())
    val uiState = _uiState.asStateFlow()

    fun requestOTP(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            registerRepository.requestOTP(phoneNumber)
        }
    }

    fun verifyOTP(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            registerRepository.verifyOTP(code)
        }
    }

}