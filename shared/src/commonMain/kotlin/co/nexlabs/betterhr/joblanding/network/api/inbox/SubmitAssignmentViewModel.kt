package co.nexlabs.betterhr.joblanding.network.api.inbox

import android.net.Uri
import android.util.Log
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.InboxRepository
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.SubmitAssignmentUIState
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

class SubmitAssignmentViewModel(private val localStorage: LocalStorage, private val inboxRepository: InboxRepository): ViewModel() {

    private val _uiState = MutableStateFlow(SubmitAssignmentUIState())
    val uiState = _uiState.asStateFlow()

    fun uploadMultipleFiles(
        files: MutableList<Uri?>,
        fileNames: MutableList<String?>,
        types: List<String>,
        referenceId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessUploadMultipleFile = false
                )
            }
            try {
                var response = inboxRepository.uploadMultipleFiles(files, fileNames, types, localStorage.candidateId, referenceId)
                if (response.isNotEmpty()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = UIErrorType.Nothing,
                            isSuccessUploadMultipleFile = true,
                            multiFileList = response
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        error = UIErrorType.Other(e.message.toString()),
                        isSuccessUploadMultipleFile = false
                    )
                }
            }
        }
    }

    fun responseAssignment(
        jobId: String,
        referenceId: String,
        title: String,
        description: String,
        status: String,
        summitedDate: String,
        candidateDescription: String,
        endTime: String,
        attachments: String,
        subDomain: String,
        referenceApplicationId: String
    ) {
        Log.d("candi>>", localStorage.candidateId)
        Log.d("jobId>>", jobId)
        Log.d("referenceId>>", referenceId)
        Log.d("title>>", title)
        Log.d("description>>", description)
        Log.d("status>>", status)
        Log.d("summitedDate>>", summitedDate)
        Log.d("candidateDescription>>", candidateDescription)
        Log.d("endTime>>", endTime)
        Log.d("attachments>>", attachments)
        Log.d("subDomain>>", subDomain)
        Log.d("ApplicationId>>", referenceApplicationId)
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isAssignmentResponseSuccess = false
                )
            }

            inboxRepository.responseAssignment(
                localStorage.candidateId, jobId, referenceId, title, description, status, summitedDate, candidateDescription, endTime, attachments, "tenantrickshaw", referenceApplicationId
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
                            isAssignmentResponseSuccess = false
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
                            isAssignmentResponseSuccess = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isAssignmentResponseSuccess = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isAssignmentResponseSuccess = false
                            )
                        }
                    }
                }
        }
    }

    fun updateNotification(
        id: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccess = false
                )
            }

            inboxRepository.updateNotification(
                id, "complete", false
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