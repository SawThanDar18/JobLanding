package co.nexlabs.betterhr.joblanding.network.api.interview

import co.nexlabs.betterhr.joblanding.DispatcherProvider
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.inbox.data.InboxDetailUIState
import co.nexlabs.betterhr.joblanding.network.api.interview.data.InterviewUIState
import co.nexlabs.betterhr.joblanding.network.api.interview.data.InterviewsRepository
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import co.nexlabs.betterhr.joblanding.viewmodel.InterviewViewModelMapper
import co.nexlabs.betterhr.joblanding.viewmodel.JobLandingJobListViewModelMapper
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

class InterviewViewModel(private val localStorage: LocalStorage, private val interviewRepository: InterviewsRepository): ViewModel() {

    fun getBearerToken(): String {
        return localStorage.bearerToken
    }

    private val _uiState = MutableStateFlow(InterviewUIState())
    val uiState = _uiState.asStateFlow()

    fun observeUiState(onChange: (InterviewUIState) -> Unit) {
        viewModelScope.launch {
            uiState.collect { state ->
                onChange(state)
            }
        }
    }

    fun fetchInterviews() {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            interviewRepository.fetchInterviews(20, 1).toFlow()
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
                    if(!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                interviewList = InterviewViewModelMapper.mapResponseToViewModel(data.data!!)
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors!![0].toString())
                            )
                        }
                    }
                }
        }
    }

    fun getCompanyInfo(ids: List<String>) {
        println("jobids>>$ids")
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            interviewRepository.getJobLandingJobList(ids).toFlow()
                .catch { e->
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
                                companyData = JobLandingJobListViewModelMapper.mapResponseToViewModel(data.data!!)
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