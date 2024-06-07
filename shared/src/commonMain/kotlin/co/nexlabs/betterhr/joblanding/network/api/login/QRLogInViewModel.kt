package co.nexlabs.betterhr.joblanding.network.api.login

import android.app.Application
import co.nexlabs.betterhr.joblanding.local_storage.AndroidLocalStorageImpl
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.login.data.InvalidQRCodeException
import co.nexlabs.betterhr.joblanding.network.api.login.data.QRLogInRepository
import co.nexlabs.betterhr.joblanding.network.api.login.data.QRLogInUIState
import co.nexlabs.betterhr.joblanding.network.api.login.data.WebLoginException
import co.nexlabs.betterhr.joblanding.network.api.login.data.WebLoginQRCodeDecrypter
import co.nexlabs.betterhr.joblanding.network.api.login.data.WebLoginQRData
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.apollographql.apollo3.exception.ApolloParseException
import io.jsonwebtoken.UnsupportedJwtException
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class QRLogInViewModel(application: Application, private val qrLogInRepository: QRLogInRepository): ViewModel() {

    private val localStorage: LocalStorage

    init {
        localStorage = AndroidLocalStorageImpl(application)
    }

    fun getBearerToken(): String {
        return localStorage.bearerToken
    }

    private val _uiState = MutableStateFlow(QRLogInUIState())
    val uiState = _uiState.asStateFlow()

    fun validateQRToken(qrToken: String): Observable<WebLoginQRData> {
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
    }

    fun qrScanLogIn(qrToken: String) {
        viewModelScope.launch(Dispatchers.IO) {
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