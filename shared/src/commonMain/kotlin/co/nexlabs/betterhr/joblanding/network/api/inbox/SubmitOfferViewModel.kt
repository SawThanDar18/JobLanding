package co.nexlabs.betterhr.joblanding.network.api.inbox

import co.nexlabs.betterhr.joblanding.DispatcherProvider
import co.nexlabs.betterhr.joblanding.FileUri
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.InboxRepository
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.SubmitOfferUIState
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

class SubmitOfferViewModel(private val localStorage: LocalStorage, private val inboxRepository: InboxRepository): ViewModel() {

    private val _uiState = MutableStateFlow(SubmitOfferUIState())
    val uiState = _uiState.asStateFlow()

    fun uploadSingleFile(
        file: FileUri,
        fileName: String,
        type: String,
        referenceId: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessUploadFile = false
                )
            }
            try {
                var response = inboxRepository.uploadSingleFile(file, fileName, type, localStorage.candidateId, referenceId)
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
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isOfferResponseSuccess = false
                )
            }

            inboxRepository.responseOffer(
                id, note, status, responseDate, attachments, "tenantrickshaw"
            ).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(
                                e.message ?: "Something went wrong!"
                            ),
                            isOfferResponseSuccess = false
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
                            isOfferResponseSuccess = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isOfferResponseSuccess = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isOfferResponseSuccess = false
                            )
                        }
                    }
                }
        }
    }

    fun updateNotification(
        id: String, status: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccess = false
                )
            }

            inboxRepository.updateNotification(
                id, status, true
            ).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(
                                e.message ?: "Something went wrong!"
                            ),
                            isSuccess = false
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
                            isSuccess = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccess = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccess = false
                            )
                        }
                    }
                }
        }
    }
}