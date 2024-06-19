package co.nexlabs.betterhr.joblanding.network.api.login

import co.nexlabs.betterhr.joblanding.DispatcherProvider
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.InboxDetailUIState
import co.nexlabs.betterhr.joblanding.network.api.login.data.QRLogInRepository
import co.nexlabs.betterhr.joblanding.network.api.login.data.QRLogInUIState
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.apollographql.apollo3.exception.ApolloParseException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class QRLogInViewModel(private val localStorage: LocalStorage, private val qrLogInRepository: QRLogInRepository): ViewModel() {

    fun getBearerToken(): String {
        return localStorage.bearerToken
    }

    private val _uiState = MutableStateFlow(QRLogInUIState())
    val uiState = _uiState.asStateFlow()

    fun observeUiState(onChange: (QRLogInUIState) -> Unit) {
        viewModelScope.launch {
            uiState.collect { state ->
                onChange(state)
            }
        }
    }

    /*fun validateQRToken(qrToken: String): Observable<WebLoginQRData> {
        return Observable.create<WebLoginQRData> { emitter ->
            try {
                val qrData = WebLoginQRCodeDecrypter()(qrToken)
                qrData?.let {
                    emitter.onNext(it)
                    emitter.onComplete()
                } ?: emitter.onError(InvalidQRCodeException())
            } catch (e: WebLoginException) {
                emitter.onError(e)
            } catch (e: UnsupportedJwtException) {
                emitter.onError(InvalidQRCodeException())
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }*/

    fun qrScanLogIn(qrToken: String) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessQRLogIn = false
                )
            }

            qrLogInRepository.qrScanLogIn(qrToken).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessQRLogIn = false
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
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = UIErrorType.Nothing,
                            isSuccessQRLogIn = false
                        )
                    }
                    if(!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccessQRLogIn = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessQRLogIn = false
                            )
                        }
                    }
                }
        }
    }

}