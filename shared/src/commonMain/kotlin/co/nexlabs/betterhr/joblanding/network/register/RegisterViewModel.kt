package co.nexlabs.betterhr.joblanding.network.register

import android.app.Application
import android.util.Log
import co.nexlabs.betterhr.joblanding.local_storage.AndroidLocalStorageImpl
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import co.nexlabs.betterhr.joblanding.viewmodel.CandidateViewModelMapper
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.apollographql.apollo3.exception.ApolloParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
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
    val application: Application,
    private val registerRepository: RegisterRepository,
) : ViewModel() {

    private val localStorage: LocalStorage

    init {
        localStorage = AndroidLocalStorageImpl(application)
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _uiStateForVerify = MutableStateFlow<UiState>(UiState.Loading)
    val uiStateForVerify: StateFlow<UiState> = _uiStateForVerify

    fun requestOTP(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response = registerRepository.requestOTP(phoneNumber)
                _uiState.value = UiState.Success(response.data.response.message ?: "")
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
                    _uiStateForVerify.value = UiState.Success(response.data.verifyPhoneNumber.token)
                    localStorage.token = response.data.verifyPhoneNumber.token
                    getCandidateDataAndUpdate()
                }
            } catch (e: Exception) {
                _uiStateForVerify.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun getCandidateDataAndUpdate() {
        viewModelScope.launch(Dispatchers.IO) {
            registerRepository.getCandidateData().toFlow()
                .catch {e ->
                    _uiStateForVerify.value = UiState.Error("Error: ${e.message}")
                }.collectLatest {
                    if (!it.hasErrors()) {
                        localStorage.candidateId = it.data!!.me.id ?: ""
                    } else {
                        _uiStateForVerify.value = UiState.Error("Error: data has error")
                    }
                }
        }
    }

    fun updateToken(token: String) {
        localStorage.token = token
    }

    fun getPageId(): String {
        return localStorage.pageId
    }

    fun updateCandidateId(candidateId: String) {
        localStorage.candidateId = candidateId
    }

}