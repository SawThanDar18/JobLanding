package co.nexlabs.betterhr.joblanding.network.register

import android.net.Uri
import android.util.Log
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.register.data.CompleteProfileRepository
import co.nexlabs.betterhr.joblanding.network.register.data.CompleteProfileUIState
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import co.nexlabs.betterhr.joblanding.viewmodel.CandidateViewModelMapper
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

class CompleteProfileViewModel(
    private val localStorage: LocalStorage,
    private val completeProfileRepository: CompleteProfileRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(CompleteProfileUIState())
    val uiState = _uiState.asStateFlow()

    fun updateCandidateId(candidateId: String) {
        localStorage.candidateId = candidateId
    }

    fun updatePhone(phone: String) {
        localStorage.phone = phone
    }

    fun getCandidateData() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            completeProfileRepository.getCandidateData().toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!")
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
                                candidateData = CandidateViewModelMapper.mapDataToViewModel(data.data!!.me)
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

    fun uploadFile(
        file: Uri,
        fileName: String,
        type: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                completeProfileRepository.uploadFile(file, fileName, type, localStorage.candidateId)
            } catch (e: Exception) {
                Log.d("error>>", e.message.toString())
            }
        }
    }

    fun updateFile(
        file: Uri,
        fileName: String,
        type: String,
        fileId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }
            try {
                completeProfileRepository.updateFile(
                    file,
                    fileName,
                    type,
                    localStorage.candidateId,
                    fileId
                )
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = UIErrorType.Nothing,
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        error = UIErrorType.Other(e.message.toString()),
                    )
                }
            }
        }
    }
}
