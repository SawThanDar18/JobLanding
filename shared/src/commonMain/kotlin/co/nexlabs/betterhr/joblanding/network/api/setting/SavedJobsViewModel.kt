package co.nexlabs.betterhr.joblanding.network.api.setting

import co.nexlabs.betterhr.joblanding.DispatcherProvider
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.setting.data.SavedJobsRepository
import co.nexlabs.betterhr.joblanding.network.api.setting.data.SavedJobsUIState
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import co.nexlabs.betterhr.joblanding.viewmodel.SavedJobsIdsViewModelMapper
import co.nexlabs.betterhr.joblanding.viewmodel.SavedJobsViewModelMapper
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

class SavedJobsViewModel(private val localStorage: LocalStorage, private val savedJobsRepository: SavedJobsRepository): ViewModel() {
    private val _uiState = MutableStateFlow(SavedJobsUIState())
    val uiState = _uiState.asStateFlow()

    fun observeUiState(onChange: (SavedJobsUIState) -> Unit) {
        viewModelScope.launch {
            uiState.collect { state ->
                onChange(state)
            }
        }
    }

    fun fetchSavedJobsIds() {
        println("b>>${localStorage.bearerToken}")
        viewModelScope.launch(DispatcherProvider.io) {
            savedJobsRepository.fetchSavedJobsIds().toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessSavedJobsIds = false
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
                            isSuccessSavedJobsIds = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                savedJobIds = SavedJobsIdsViewModelMapper.mapDataToViewModel(data.data!!),
                                isSuccessSavedJobsIds = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessSavedJobsIds = false
                            )
                        }
                    }
                }
        }
    }

    fun fetchSavedJobs(jobIds: List<String>) {
        viewModelScope.launch(DispatcherProvider.io) {
            savedJobsRepository.fetchSavedJobs(jobIds).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
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
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                savedJobList = SavedJobsViewModelMapper.mapDataToViewModel(data.data!!),
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = UIErrorType.Other(data.errors.toString()),
                            )
                        }
                    }
                }
        }
    }

}