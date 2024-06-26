package co.nexlabs.betterhr.joblanding.network.api.application

import co.nexlabs.betterhr.joblanding.DispatcherProvider
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.application.data.ApplicationRepository
import co.nexlabs.betterhr.joblanding.network.api.application.data.ApplicationUIState
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import co.nexlabs.betterhr.joblanding.viewmodel.ApplicationByIdViewModelMapper
import co.nexlabs.betterhr.joblanding.viewmodel.ApplicationViewModelMapper
import co.nexlabs.betterhr.joblanding.viewmodel.JobLandingJobListViewModelMapper
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

class ApplicationViewModel(private val localStorage: LocalStorage, private val applicationRepository: ApplicationRepository): ViewModel() {

    fun getBearerToken(): String {
        return localStorage.bearerToken
    }

    private val _uiState = MutableStateFlow(ApplicationUIState())
    val uiState = _uiState.asStateFlow()

    fun observeUiState(onChange: (ApplicationUIState) -> Unit) {
        viewModelScope.launch {
            uiState.collect { state ->
                onChange(state)
            }
        }
    }

    fun fetchApplication() {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isSuccessGetApplicationData = false,
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            applicationRepository.fetchApplication(50).toFlow()
                .catch { e->
                    _uiState.update {
                        it.copy(
                            isSuccessGetApplicationData = false,
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
                            isSuccessGetApplicationData = false,
                            isLoading = true,
                            error = UIErrorType.Nothing
                        )
                    }
                    if (!data.hasErrors()) {
                        var jobIds: List<String> = ArrayList()
                        if (data.data != null) {
                            if (data.data!!.applications.isNotEmpty()) {
                                jobIds = data.data!!.applications.map {
                                    it.reference_job_id ?: ""
                                }
                            }
                        }

                        if (jobIds.isNotEmpty()) {
                            getCompanyInfo(jobIds)
                        }

                        _uiState.update {
                            it.copy(
                                isSuccessGetApplicationData = true,
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                application = ApplicationViewModelMapper.mapResponseToViewModel(data.data!!)
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isSuccessGetApplicationData = false,
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString())
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

            applicationRepository.getJobLandingJobList(ids).toFlow()
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

    fun fetchApplicationById(id: String) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            applicationRepository.fetchApplicationById(id).toFlow()
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
                                applicationById = ApplicationByIdViewModelMapper.mapDataToViewModel(data.data!!.application!!)
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

    private val _filters = MutableStateFlow(
        mapOf(
            "Applied" to localStorage.applied,
            "Qualified" to localStorage.qualified,
            "Interviewing" to localStorage.interviewing,
            "Offer" to localStorage.offered,
            "Rejected" to localStorage.rejected
        )
    )
    val filters: StateFlow<Map<String, Boolean>> = _filters

    fun updateFilter(key: String, value: Boolean) {
        viewModelScope.launch {
            when (key) {
                "Applied" -> localStorage.applied = value
                "Qualified" -> localStorage.qualified = value
                "Interviewing" -> localStorage.interviewing = value
                "Offer" -> localStorage.offered = value
                "Rejected" -> localStorage.rejected = value
            }
            _filters.value = _filters.value.toMutableMap().apply { this[key] = value }
        }
    }
}