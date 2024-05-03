package co.nexlabs.betterhr.joblanding.network.api.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.nexlabs.betterhr.joblanding.local_storage.AndroidLocalStorageImpl
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.FetchSaveJobDatUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.JobDetailRepository
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.JobDetailUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.JobDetailUIState
import co.nexlabs.betterhr.joblanding.network.api.request_response.FileRequest
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import co.nexlabs.betterhr.joblanding.viewmodel.CandidateViewModelMapper
import co.nexlabs.betterhr.joblanding.viewmodel.JobDetailViewModelMapper
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.apollographql.apollo3.exception.ApolloParseException
import kotlinx.coroutines.Dispatchers
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

class JobDetailViewModel(
    application: Application,
    private val jobDetailRepository: JobDetailRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(JobDetailUIState())
    val uiState = _uiState.asStateFlow()

    private val _uiStateRegister = MutableStateFlow<UiState>(UiState.Loading)
    val uiStateRegister: StateFlow<UiState> = _uiStateRegister

    private val _uiStateForVerify = MutableStateFlow<UiState>(UiState.Loading)
    val uiStateForVerify: StateFlow<UiState> = _uiStateForVerify

    private val localStorage: LocalStorage

    init {
        localStorage = AndroidLocalStorageImpl(application)
    }

    fun getToken(): String {
        return localStorage.token
    }

    fun getBearerToken(): String {
        return localStorage.bearerToken
    }

    fun getPageId(): String {
        return localStorage.pageId
    }

    fun getJobDetail(jobId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            jobDetailRepository.getJobDetail(jobId).toFlow()
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
                                jobDetail = JobDetailViewModelMapper.mapDataToViewModel(data.data!!.jobLandingJob)
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

    fun saveJob(jobId: String) {
        Log.d("candi>>", localStorage.candidateId)
        viewModelScope.launch(Dispatchers.IO) {
            jobDetailRepository.saveJob(localStorage.candidateId, jobId).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isSaveJobSuccess = false,
                            isLoading = true,
                            error = UIErrorType.Other(e.message.toString())
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
                            isSaveJobSuccess = false,
                            isLoading = true,
                            error = UIErrorType.Nothing
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isSaveJobSuccess = true,
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

    fun fetchSaveJobsById(jobId: String) {
        Log.d("bearer>>", localStorage.bearerToken)
        Log.d("jobid>>", jobId)
        viewModelScope.launch(Dispatchers.IO) {
            jobDetailRepository.fetchSaveJobsById(jobId).toFlow()
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
                                fetchSaveJobs =
                                JobDetailViewModelMapper.mapFetchSaveJobDataToViewModel(data.data!!.fetchSaveJobByJobId!!)
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

    fun unSaveJob(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            jobDetailRepository.unSaveJob(id).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isUnSaveJobSuccess = false,
                            isLoading = true,
                            error = UIErrorType.Other(e.message.toString())
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
                            isUnSaveJobSuccess = false,
                            isLoading = true,
                            error = UIErrorType.Nothing
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isUnSaveJobSuccess = true,
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

    fun requestOTP(phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response = jobDetailRepository.requestOTP(phoneNumber)
                _uiStateRegister.value = UiState.Success(response.data.response.message ?: "")
            } catch (e: Exception) {
                _uiStateRegister.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    fun verifyOTP(code: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response = jobDetailRepository.verifyOTP(code)
                if (response.data.verifyPhoneNumber.token == null) {
                    _uiStateForVerify.value = UiState.Error("Error: ${response.data.verifyPhoneNumber.message}")
                } else {
                    _uiStateForVerify.value = UiState.Success(response.data.verifyPhoneNumber.token)
                }
            } catch (e: Exception) {
                _uiStateForVerify.value = UiState.Error("Error: ${e.message}")
            }
        }
    }

    //After SignUp
    fun getCandidateData() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            jobDetailRepository.getCandidateData().toFlow()
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

    fun createApplication(
        referenceJobId: String,
        subdomain: String,
        jobTitle: String,
        status: String,
        appliedDate: String,
        currentJobTitle: String,
        currentCompany: String,
        workingSince: String,
        files: List<FileRequest>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            jobDetailRepository.createApplication(
                referenceJobId, subdomain, jobTitle, "applied", appliedDate, localStorage.candidateId, currentJobTitle, currentCompany, workingSince, files
            )
        }
    }
}