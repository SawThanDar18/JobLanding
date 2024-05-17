package co.nexlabs.betterhr.joblanding.network.api.inbox

import android.app.Application
import android.net.Uri
import android.util.Log
import co.nexlabs.betterhr.joblanding.local_storage.AndroidLocalStorageImpl
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.InboxRepository
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.InboxUIState
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.SubmitAssignmentUIState
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.SubmitOfferUIState
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.apollographql.apollo3.exception.ApolloParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class SubmitOfferViewModel(application: Application, private val inboxRepository: InboxRepository): ViewModel() {

    private val localStorage: LocalStorage

    init {
        localStorage = AndroidLocalStorageImpl(application)
    }

    private val _uiState = MutableStateFlow(SubmitOfferUIState())
    val uiState = _uiState.asStateFlow()

    fun uploadSingleFile(
        file: Uri,
        fileName: String,
        type: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessUploadFile = false
                )
            }
            try {
                var response = inboxRepository.uploadSingleFile(file, fileName, type, localStorage.candidateId)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = UIErrorType.Nothing,
                        isSuccessUploadFile = true,
                        fileList = response
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        error = UIErrorType.Other(e.message.toString()),
                        isSuccessUploadFile = false
                    )
                }
            }
        }
    }

    fun responseOffer(
        id: String,
        note: String,
        status: String,
        responseDate: String,
        attachments: String,
        subDomain: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            inboxRepository.responseOffer(
                id, note, status, responseDate, attachments, subDomain
            ).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
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
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = UIErrorType.Nothing
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString())
                            )
                        }
                    }
                }
        }
    }
}