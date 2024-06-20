package co.nexlabs.betterhr.joblanding.network.register

import co.nexlabs.betterhr.joblanding.DispatcherProvider
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.choose_country.data.ChooseCountryUIState
import co.nexlabs.betterhr.joblanding.network.register.data.RegisterRepository
import co.nexlabs.betterhr.joblanding.network.register.data.RegisterUIState
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.apollographql.apollo3.exception.ApolloParseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val localStorage: LocalStorage,
    private val registerRepository: RegisterRepository,
) : ViewModel() {

    private val _registerUiState = MutableStateFlow(RegisterUIState())
    val registerUiState = _registerUiState.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    private val _uiStateForVerify = MutableStateFlow<UiState>(UiState.Loading)
    val uiStateForVerify: StateFlow<UiState> = _uiStateForVerify

    fun observeUiStateForRequestOTP(onChange: (UiState) -> Unit) {
        viewModelScope.launch {
            uiState.collect { state ->
                onChange(state)
            }
        }
    }

    fun observeUiStateForVerifyOTP(onChange: (UiState) -> Unit) {
        viewModelScope.launch {
            uiStateForVerify.collect { state ->
                onChange(state)
            }
        }
    }

    fun observeUiStateForBearerToken(onChange: (RegisterUIState) -> Unit) {
        viewModelScope.launch {
            registerUiState.collect { state ->
                onChange(state)
            }
        }
    }

    fun requestOTP(phoneNumber: String) {
        viewModelScope.launch(DispatcherProvider.io) {
            try {
                var response = registerRepository.requestOTP(phoneNumber)
                _uiState.value = UiState.Success(response.data.response.message ?: "")
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun verifyOTP(code: String) {
        viewModelScope.launch(DispatcherProvider.io) {
            try {
                var response = registerRepository.verifyOTP(code)
                if (response.data.verifyPhoneNumber.token == null) {
                    _uiStateForVerify.value =
                        UiState.Error("Error: ${response.data.verifyPhoneNumber.message}")
                } else {
                    _uiStateForVerify.value = UiState.Success(response.data.verifyPhoneNumber.token ?: "")
                }
            } catch (e: Exception) {
                _uiStateForVerify.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun updatePhone(phone: String) {
        localStorage.phone = phone
    }

    fun updateToken(token: String) {
        localStorage.token = token
    }

    fun updateBearerToken(bearerToken: String) {
        localStorage.bearerToken = bearerToken
    }

    fun getPageId(): String {
        return localStorage.pageId
    }

    fun getBearerToken(): String {
        return localStorage.bearerToken
    }


    fun getBearerToken(token: String) {

        viewModelScope.launch(DispatcherProvider.io) {
            _registerUiState.update {
                it.copy(
                    isSuccessForBearerToken = false,
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            registerRepository.getBearerToken(token).toFlow()
                .catch { e ->
                    _registerUiState.update {
                        it.copy(
                            isSuccessForBearerToken = false,
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(
                                e.message ?: "Something went wrong!"
                            )
                        )
                    }
                    when (e) {
                        is ApolloHttpException -> {
                            println("HTTP error: ${e.message}")
                        }

                        is ApolloNetworkException -> {
                            println("Network error: ${e.message}")
                        }

                        is ApolloParseException -> {
                            println("Parse error: ${e.message}")
                        }

                        else -> {
                            println("An error occurred: ${e.message}")
                            e.printStackTrace()
                        }
                    }
                }.collectLatest { data ->
                    _registerUiState.update {
                        it.copy(
                            isSuccessForBearerToken = false,
                            isLoading = true,
                            error = UIErrorType.Nothing
                        )
                    }
                    if (data.data!!.verifySmsTokenAndAuth != null) {
                        _registerUiState.update {
                            it.copy(
                                isSuccessForBearerToken = true,
                                bearerToken = data.data!!.verifySmsTokenAndAuth.token.toString(),
                                isLoading = false,
                                error = if (data.data!!.verifySmsTokenAndAuth == null) UIErrorType.Other(
                                    "API returned empty list"
                                ) else UIErrorType.Nothing,
                            )
                        }
                    } else {
                        _registerUiState.update {
                            it.copy(
                                isSuccessForBearerToken = false,
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString())
                            )
                        }
                    }
                }
        }
    }
}