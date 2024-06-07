package co.nexlabs.betterhr.joblanding.network.api.home

import android.app.Application
import android.net.Uri
import android.util.Log
import co.nexlabs.betterhr.job.with_auth.FetchSaveJobByJobIdQuery
import co.nexlabs.betterhr.joblanding.local_storage.AndroidLocalStorageImpl
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.FetchSaveJobDatUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.FetchSaveJobsUIModel
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
import java.io.File

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

    fun updateToken(token: String) {
        localStorage.token = token
    }

    fun updatePhone(phone: String) {
        localStorage.phone = phone
    }

    fun getPageId(): String {
        return localStorage.pageId
    }

    fun getBearerToken(): String {
        return localStorage.bearerToken
    }

    fun checkBearToken(jobId: String) {
        if (localStorage.bearerToken != "") {
            fetchSaveJobsById(jobId)
           /* _uiState.update {
                it.copy(
                    isBearerTokenExist = true
                )
            }*/
        }
    }

    fun updateBearerToken(bearerToken: String) {
        localStorage.bearerToken = bearerToken
    }

    fun updateCandidateId(candidateId: String) {
        localStorage.candidateId = candidateId
    }

    fun getToken(): String {
        return localStorage.token
    }

    fun getJobDetail(jobId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            jobDetailRepository.getJobDetail(jobId).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isSuccessGetJobDetail = false,
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
                            isSuccessGetJobDetail = false,
                            isLoading = true,
                            error = UIErrorType.Nothing
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isSuccessGetJobDetail = true,
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                jobDetail = JobDetailViewModelMapper.mapDataToViewModel(data.data!!.jobLandingJob)
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isSuccessGetJobDetail = false,
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
        Log.d("jobId>>", jobId)
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
        Log.d("can>>", localStorage.candidateId)
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
                        if (data.data!!.fetchSaveJobByJobId == null) {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    fetchSaveJobId = "",
                                    fetchSaveJobs = FetchSaveJobsUIModel(FetchSaveJobDatUIModel(
                                        "", "", ""
                                    ))
                                )
                            }
                        } else {
                            Log.d("fetch>>", "not null")
                            _uiState.update {
                                it.copy(
                                    fetchSaveJobId = data.data!!.fetchSaveJobByJobId!!.id ?: "",
                                    isLoading = false,
                                    fetchSaveJobs = JobDetailViewModelMapper.mapFetchSaveJobDataToViewModel(data.data!!)
                                    )
                            }
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
                    _uiStateForVerify.value =
                        UiState.Error("Error: ${response.data.verifyPhoneNumber.message}")
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

    fun uploadMultipleFiles(
        files: MutableList<Uri?>,
        fileNames: MutableList<String?>,
        types: List<String>
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
                var response = jobDetailRepository.uploadMultipleFiles(files, fileNames, types)
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

    fun createApplication(
        referenceJobId: String,
        subdomain: String,
        jobTitle: String,
        appliedDate: String,
        currentJobTitle: String,
        currentCompany: String,
        workingSince: String,
//        fileName: MutableList<String?>,
//        files: MutableList<Uri?>,
//        types: MutableList<String>,
        fileIds: MutableList<String>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isSuccessCreateApplication = false,
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    idFromCreateApplication = ""
                )
            }
            try {
                var response = jobDetailRepository.createApplication(
                    referenceJobId,
                    subdomain,
                    jobTitle,
                    "applied",
                    appliedDate,
                    localStorage.candidateId,
                    currentJobTitle,
                    currentCompany,
                    workingSince,
                    fileIds
                )
                _uiState.update {
                    it.copy(
                        isSuccessCreateApplication = true,
                        isLoading = true,
                        error = UIErrorType.Nothing,
                        idFromCreateApplication = response.id
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSuccessCreateApplication = false,
                        isLoading = true,
                        error = UIErrorType.Other(e.message.toString()),
                        idFromCreateApplication = ""
                    )
                }
            }
        }
    }

    fun createApplicationWithFileIds(
        referenceJobId: String,
        subdomain: String,
        jobTitle: String,
        appliedDate: String,
        currentJobTitle: String,
        currentCompany: String,
        workingSince: String,
        fileName: MutableList<String?>,
        files: MutableList<Uri?>,
        types: MutableList<String>,
        existingFileId: MutableList<String>
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isSuccessCreateApplication = false,
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    idFromCreateApplication = ""
                )
            }
            try {
                var response = jobDetailRepository.createApplicationWithExistingFileIds(
                    referenceJobId,
                    subdomain,
                    jobTitle,
                    "applied",
                    appliedDate,
                    localStorage.candidateId,
                    currentJobTitle,
                    currentCompany,
                    workingSince,
                    fileName,
                    files,
                    types,
                    existingFileId
                )
                _uiState.update {
                    it.copy(
                        isSuccessCreateApplication = true,
                        isLoading = false,
                        error = UIErrorType.Nothing,
                        idFromCreateApplication = response.id
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSuccessCreateApplication = false,
                        isLoading = true,
                        error = UIErrorType.Other(e.message.toString()),
                        idFromCreateApplication = ""
                    )
                }
            }
        }
    }

    fun updateApplication(id: String) {
        Log.d("updateid>>", id)
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            jobDetailRepository.updateApplication(id).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isSuccessUpdateApplication = false,
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
                            isSuccessUpdateApplication = false,
                            isLoading = true,
                            error = UIErrorType.Nothing
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isSuccessUpdateApplication = true,
                                isLoading = true,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isSuccessUpdateApplication = false,
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
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    idFromProfileUpload = ""
                )
            }
            try {
                var response =
                    jobDetailRepository.uploadFile(file, fileName, type, localStorage.candidateId)
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = UIErrorType.Nothing,
                        idFromProfileUpload = response.id
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        error = UIErrorType.Other(e.message.toString()),
                        idFromProfileUpload = ""
                    )
                }
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
                    error = UIErrorType.Nothing,
                    idFromProfileUpload = ""
                )
            }
            try {
                var response = jobDetailRepository.updateFile(
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
                        idFromProfileUpload = response.id
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = true,
                        error = UIErrorType.Other(e.message.toString()),
                        idFromProfileUpload = ""
                    )
                }
            }
        }
    }

    fun createCandidate(
        name: String,
        email: String,
        desiredPosition: String,
        summary: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isSuccessForCandidateId = false,
                    isSuccessForBearerToken = false,
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            jobDetailRepository.createCandidate(
                name, email, localStorage.phone, desiredPosition, summary, localStorage.countryId
            ).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isSuccessForCandidateId = false,
                            isSuccessForBearerToken = false,
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
                            isSuccessForCandidateId = false,
                            isSuccessForBearerToken = false,
                            isLoading = true,
                            error = UIErrorType.Nothing
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isSuccessForCandidateId = true,
                                isSuccessForBearerToken = false,
                                candidateId = data.data!!.createCandidate!!.id.toString(),
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                            )
                        }


                        Log.d("tok>>", localStorage.token)
                        Log.d("can>>", data.data!!.createCandidate!!.id.toString())
                    } else {
                        _uiState.update {
                            it.copy(
                                isSuccessForCandidateId = false,
                                isSuccessForBearerToken = false,
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString())
                            )
                        }
                    }
                }
        }
    }


    fun applyJob(
        referenceId: String, jobId: String, subDomain: String
    ) {
        Log.d("reference>>>", referenceId)
        Log.d("candidate>>>", localStorage.candidateId)
        Log.d("jobId>>>", jobId)
        Log.d("subdomain>>", subDomain)
        viewModelScope.launch(Dispatchers.IO) {
            jobDetailRepository.applyJob(
                referenceId,
                localStorage.candidateId,
                jobId,
                "screening",
                "tenantrickshaw"
            ).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isApplyJobSuccess = false,
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
                            isApplyJobSuccess = false,
                            isLoading = true,
                            error = UIErrorType.Nothing
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isApplyJobSuccess = true,
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isApplyJobSuccess = false,
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString())
                            )
                        }
                    }
                }
        }
    }

    fun getBearerTokenFromAPI(token: String) {

        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isSuccessForCandidateId = false,
                    isSuccessForBearerToken = false,
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            jobDetailRepository.getBearerToken(token).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isSuccessForCandidateId = false,
                            isSuccessForBearerToken = false,
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
                            isSuccessForCandidateId = false,
                            isSuccessForBearerToken = false,
                            isLoading = true,
                            error = UIErrorType.Nothing
                        )
                    }
                    if (data.data!!.verifySmsTokenAndAuth != null) {
                        _uiState.update {
                            it.copy(
                                isSuccessForCandidateId = false,
                                isSuccessForBearerToken = true,
                                bearerToken = data.data!!.verifySmsTokenAndAuth.token.toString(),
                                isLoading = false,
                                error = if (data.data!!.verifySmsTokenAndAuth == null) UIErrorType.Other(
                                    "API returned empty list"
                                ) else UIErrorType.Nothing,
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isSuccessForCandidateId = false,
                                isSuccessForBearerToken = false,
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString())
                            )
                        }
                    }
                }
        }
    }
}