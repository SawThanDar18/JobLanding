package co.nexlabs.betterhr.joblanding.network.api.home

import co.nexlabs.betterhr.joblanding.DispatcherProvider
import co.nexlabs.betterhr.joblanding.FileUri
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.FetchSaveJobDatUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.FetchSaveJobsUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.JobDetailRepository
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.JobDetailUIState
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import co.nexlabs.betterhr.joblanding.viewmodel.CandidateViewModelMapper
import co.nexlabs.betterhr.joblanding.viewmodel.JobDetailViewModelMapper
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

/*sealed class UiState {
    object Loading : UiState()
    data class Success(val data: String) : UiState()
    data class Error(val errorMessage: String) : UiState()
}*/

class JobDetailViewModel(
    private val localStorage: LocalStorage,
    private val jobDetailRepository: JobDetailRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(JobDetailUIState())
    val uiState = _uiState.asStateFlow()

    /*private val _uiStateRegister = MutableStateFlow<UiState>(UiState.Loading)
    val uiStateRegister: StateFlow<UiState> = _uiStateRegister

    private val _uiStateForVerify = MutableStateFlow<UiState>(UiState.Loading)
    val uiStateForVerify: StateFlow<UiState> = _uiStateForVerify

    fun observeUiStateForRequestOTP(onChange: (UiState) -> Unit) {
        viewModelScope.launch {
            uiStateRegister.collect { state ->
                onChange(state)
            }
        }
    }

    fun observeUiStateForVerifyOTP(onChange: (UiState) -> Unit) {
        viewModelScope.launch {
            uiStateForVerify.collect { state ->
                onChange(state)
            }
        }
    }*/

    fun observeUiStateForJobDetail(onChange: (JobDetailUIState) -> Unit) {
        viewModelScope.launch {
            uiState.collect { state ->
                onChange(state)
            }
        }
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
        viewModelScope.launch(DispatcherProvider.io) {
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
        println("save>>${localStorage.candidateId}")
        viewModelScope.launch(DispatcherProvider.io) {
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

    fun checkJobIsApplied(referenceJobId: String) {
        println("save>>jobapplied${localStorage.candidateId}")
        viewModelScope.launch(DispatcherProvider.io) {
            jobDetailRepository.checkJobIsApplied(referenceJobId, localStorage.candidateId).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            appliedJobStatus = "",
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
                            appliedJobStatus = "",
                            isLoading = true,
                            error = UIErrorType.Nothing
                        )
                    }
                    if (!data.hasErrors()) {
                        if (data.data!!.applicationIsApplied != null) {
                            _uiState.update {
                                it.copy(
                                    appliedJobStatus = data.data!!.applicationIsApplied!!.status ?: "",
                                    isLoading = false,
                                    error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    appliedJobStatus = "",
                                    isLoading = false,
                                    error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                )
                            }
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                appliedJobStatus = "",
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString())
                            )
                        }
                    }
                }
        }
    }

    fun fetchSaveJobsById(jobId: String) {
        viewModelScope.launch(DispatcherProvider.io) {
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
        viewModelScope.launch(DispatcherProvider.io) {
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
        viewModelScope.launch(DispatcherProvider.io) {
            try {
                var response = jobDetailRepository.requestOTP(phoneNumber)
                //_uiState.value = UiState.Success(response.data.response.message)
                if (response.data != null) {
                    if (response.data!!.response != null) {
                        if (response.data.response.message != "") {
                            _uiState.update {
                                it.copy(
                                    isGetRequestOTPValue = true,
                                    getRequestOTPValue = response.data.response.message,
                                    isLoading = false,
                                    error = UIErrorType.Nothing
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    isGetRequestOTPValue = false,
                                    getRequestOTPValue = "",
                                    isLoading = true,
                                    error = UIErrorType.Other("Data return null")
                                )
                            }
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isGetRequestOTPValue = false,
                                getRequestOTPValue = "",
                                isLoading = true,
                                error = UIErrorType.Other("Data return null")
                            )
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isGetRequestOTPValue = false,
                            getRequestOTPValue = "",
                            isLoading = true,
                            error = UIErrorType.Other("Data return null")
                        )
                    }
                }
            } catch (e: Exception) {
                //_uiState.value = UiState.Error("Error: ${e.message}")
                _uiState.update {
                    it.copy(
                        isGetRequestOTPValue = false,
                        getRequestOTPValue = "",
                        isLoading = true,
                        error = UIErrorType.Other(e.message.toString())
                    )
                }
            }
        }
    }

    fun verifyOTP(code: String) {
        viewModelScope.launch(DispatcherProvider.io) {
            try {
                var response = jobDetailRepository.verifyOTP(code)
                if (response.data != null) {
                    if (response.data!!.verifyPhoneNumber != null) {
                        if (response.data.verifyPhoneNumber.token != "") {
                            _uiState.update {
                                it.copy(
                                    isGetVerifyOTPValue = true,
                                    getVerifyOTPValue = response.data.verifyPhoneNumber.token ?: "",
                                    isLoading = false,
                                    error = UIErrorType.Nothing
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    isGetVerifyOTPValue = false,
                                    getVerifyOTPValue = "",
                                    isLoading = true,
                                    error = UIErrorType.Other("Data return null")
                                )
                            }
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isGetVerifyOTPValue = false,
                                getVerifyOTPValue = "",
                                isLoading = true,
                                error = UIErrorType.Other("Data return null")
                            )
                        }
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isGetVerifyOTPValue = false,
                            getVerifyOTPValue = "",
                            isLoading = true,
                            error = UIErrorType.Other("Data return null")
                        )
                    }
                }
            } catch (e: Exception) {
                //_uiState.value = UiState.Error("Error: ${e.message}")
                _uiState.update {
                    it.copy(
                        isGetVerifyOTPValue = false,
                        getVerifyOTPValue = "",
                        isLoading = true,
                        error = UIErrorType.Other(e.message.toString())
                    )
                }
            }
        }
    }

    //After SignUp
    fun getCandidateData() {
        viewModelScope.launch(DispatcherProvider.io) {
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
        files: MutableList<FileUri?>,
        fileNames: MutableList<String?>,
        types: List<String>
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoadingForApplyJob = true,
                    error = UIErrorType.Nothing,
                    isSuccessUploadMultipleFile = false,
                )
            }
            try {
                val response = jobDetailRepository.uploadMultipleFiles(files, fileNames, types)
                if (response.isNotEmpty()) {
                    _uiState.update {
                        it.copy(
                            isLoadingForApplyJob = false,
                            error = UIErrorType.Nothing,
                            isSuccessUploadMultipleFile = true,
                            multiFileList = response
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoadingForApplyJob = true,
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
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isSuccessCreateApplication = false,
                    isLoadingForApplyJob = true,
                    error = UIErrorType.Nothing,
                    idFromCreateApplication = ""
                )
            }
            try {
                val response = jobDetailRepository.createApplication(
                    referenceJobId,
                    "tenantrickshaw",
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
                        isLoadingForApplyJob = true,
                        error = UIErrorType.Nothing,
                        idFromCreateApplication = response.id
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSuccessCreateApplication = false,
                        isLoadingForApplyJob = true,
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
        files: MutableList<FileUri?>,
        types: MutableList<String>,
        existingFileId: MutableList<String>
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isSuccessCreateApplication = false,
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    idFromCreateApplication = ""
                )
            }
            try {
                val response = jobDetailRepository.createApplicationWithExistingFileIds(
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
        viewModelScope.launch(DispatcherProvider.io) {
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
        file: FileUri,
        fileName: String,
        type: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    idFromProfileUpload = ""
                )
            }
            try {
                val response =
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
        file: FileUri,
        fileName: String,
        type: String,
        fileId: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
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
        viewModelScope.launch(DispatcherProvider.io) {
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
        viewModelScope.launch(DispatcherProvider.io) {
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

        viewModelScope.launch(DispatcherProvider.io) {
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