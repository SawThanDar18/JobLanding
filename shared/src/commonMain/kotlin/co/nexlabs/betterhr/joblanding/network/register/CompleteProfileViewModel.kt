package co.nexlabs.betterhr.joblanding.network.register

import co.nexlabs.betterhr.joblanding.DispatcherProvider
import co.nexlabs.betterhr.joblanding.FileUri
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.choose_country.data.ChooseCountryUIState
import co.nexlabs.betterhr.joblanding.network.register.data.CompleteProfileRepository
import co.nexlabs.betterhr.joblanding.network.register.data.CompleteProfileUIState
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import co.nexlabs.betterhr.joblanding.viewmodel.CandidateViewModelMapper
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

class CompleteProfileViewModel(
    private val localStorage: LocalStorage,
    private val completeProfileRepository: CompleteProfileRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(CompleteProfileUIState())
    val uiState = _uiState.asStateFlow()

    fun observeUiState(onChange: (CompleteProfileUIState) -> Unit) {
        viewModelScope.launch {
            uiState.collect { state ->
                onChange(state)
            }
        }
    }

    fun updateCandidateId(candidateId: String) {
        localStorage.candidateId = candidateId
    }

    fun updatePhone(phone: String) {
        localStorage.phone = phone
    }

    fun getCandidateData() {
        viewModelScope.launch(DispatcherProvider.io) {
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

    fun updateSummary(summary: String) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            completeProfileRepository.updateSummary(localStorage.candidateId, summary).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessUpdateSummary = false
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
                            isSuccessUpdateSummary = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccessUpdateSummary = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessUpdateSummary = false
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
                    error = UIErrorType.Nothing
                )
            }
            try {
                var response = completeProfileRepository.uploadFile(file, fileName, type, localStorage.candidateId)
                if (response.id != "") {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = UIErrorType.Nothing,
                            getFileId = true,
                            fileId = response.id
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = UIErrorType.Nothing,
                        )
                    }
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

    fun createCompany(companyName: String, fileId: String) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessCreateCompany = false
                )
            }

            completeProfileRepository.createCompany(companyName, localStorage.candidateId, fileId).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessCreateCompany = false
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
                            isSuccessCreateCompany = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccessCreateCompany = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessCreateCompany = false
                            )
                        }
                    }
                }
        }
    }

    fun createPosition(positionName: String) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    getPositionId = false
                )
            }

            completeProfileRepository.createPosition(positionName).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            getPositionId = false
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
                            getPositionId = false
                        )
                    }
                    if (!data.hasErrors()) {
                        if (data.data != null) {
                            if (data.data!!.createPosition != null) {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                        positionId = data.data!!.createPosition!!.id ?: "",
                                        getPositionId = true
                                    )
                                }
                            } else {
                                _uiState.update {
                                    it.copy(
                                        isLoading = false,
                                        error = if (data.data!!.createPosition == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                        getPositionId = false
                                    )
                                }
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                    getPositionId = false
                                )
                            }
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                getPositionId = false
                            )
                        }
                    }
                }
        }
    }

    fun createExperience(
        positionId: String,
        companyId: String,
        title: String,
        location: String,
        experienceLevel: String,
        employmentType: String,
        startDate: String,
        endDate: String,
        isCurrentJob: Boolean,
        description: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessCreateExperience = false
                )
            }

            completeProfileRepository.createExperience(positionId, localStorage.candidateId, companyId, title, location, experienceLevel, employmentType, startDate, endDate, isCurrentJob, description).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessCreateExperience = false
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
                            isSuccessCreateExperience = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccessCreateExperience = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessCreateExperience = false
                            )
                        }
                    }
                }
        }
    }

    fun updateExperience(
        id: String,
        companyId: String,
        title: String,
        location: String,
        experienceLevel: String,
        employmentType: String,
        startDate: String,
        endDate: String,
        isCurrentJob: Boolean,
        description: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessUpdateExperience = false
                )
            }

            completeProfileRepository.updateExperience(id, localStorage.candidateId, companyId, title, location, experienceLevel, employmentType, startDate, endDate, isCurrentJob, description).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessUpdateExperience = false
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
                            isSuccessUpdateExperience = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccessUpdateExperience = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessUpdateExperience = false
                            )
                        }
                    }
                }
        }
    }

    fun createEducation(
        countryName: String,
        institution: String,
        educationLevel: String,
        degree: String,
        fieldOfStudy: String,
        startDate: String,
        endDate: String,
        isCurrentStudy: Boolean,
        description: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessCreateEducation = false
                )
            }

            completeProfileRepository.createEducation(countryName, institution, educationLevel, degree, fieldOfStudy, startDate, endDate, isCurrentStudy, description, localStorage.candidateId).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessCreateEducation = false
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
                            isSuccessCreateEducation = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccessCreateEducation = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessCreateEducation = false
                            )
                        }
                    }
                }
        }
    }

    fun updateEducation(
        id: String,
        countryName: String,
        institution: String,
        educationLevel: String,
        degree: String,
        fieldOfStudy: String,
        startDate: String,
        endDate: String,
        isCurrentStudy: Boolean,
        description: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessUpdateEducation = false
                )
            }

            completeProfileRepository.updateEducation(id, countryName, institution, educationLevel, degree, fieldOfStudy, startDate, endDate, isCurrentStudy, description).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessUpdateEducation = false
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
                            isSuccessUpdateEducation = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccessUpdateEducation = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessUpdateEducation = false
                            )
                        }
                    }
                }
        }
    }

    fun createLanguage(
        languageName: String,
        proficiencyLevel: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessCreateLanguage = false
                )
            }

            completeProfileRepository.createLanguage(languageName, proficiencyLevel, localStorage.candidateId).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessCreateLanguage = false
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
                            isSuccessCreateLanguage = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccessCreateLanguage = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessCreateLanguage = false
                            )
                        }
                    }
                }
        }
    }

    fun updateLanguage(
        id: String,
        languageName: String,
        proficiencyLevel: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessUpdateLanguage = false
                )
            }

            completeProfileRepository.updateLanguage(id, languageName, proficiencyLevel).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessUpdateLanguage = false
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
                            isSuccessUpdateLanguage = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccessUpdateLanguage = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessUpdateLanguage = false
                            )
                        }
                    }
                }
        }
    }

    fun createSkill(
        skillName: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessCreateSkill = false
                )
            }

            completeProfileRepository.createSkill(skillName, localStorage.candidateId).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessCreateSkill = false
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
                            isSuccessCreateSkill = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccessCreateSkill = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessCreateSkill = false
                            )
                        }
                    }
                }
        }
    }

    fun updateSkill(
        id: String,
        skillName: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessUpdateSkill = false
                )
            }

            completeProfileRepository.updateSkill(id, skillName).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessUpdateSkill = false
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
                            isSuccessUpdateSkill = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccessUpdateSkill = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessUpdateSkill = false
                            )
                        }
                    }
                }
        }
    }

    fun createCertificate(
        courseName: String,
        issuingOrganization: String,
        issueDate: String,
        expireDate: String,
        isExpire: Boolean,
        credentialUrl: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessCreateCertificate = false
                )
            }

            completeProfileRepository.createCertification(localStorage.candidateId, courseName, issuingOrganization, issueDate, expireDate, isExpire, credentialUrl).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessCreateCertificate = false
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
                            isSuccessCreateCertificate = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccessCreateCertificate = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessCreateCertificate = false
                            )
                        }
                    }
                }
        }
    }

    fun updateCertificate(
        id: String,
        courseName: String,
        issuingOrganization: String,
        issueDate: String,
        expireDate: String,
        isExpire: Boolean,
        credentialUrl: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing,
                    isSuccessUpdateCertificate = false
                )
            }

            completeProfileRepository.updateCertification(id, courseName, issuingOrganization, issueDate, expireDate, isExpire, credentialUrl).toFlow()
                .catch { e ->
                    _uiState.update {
                        it.copy(
                            isLoading = true,
                            error = if ((e as ApolloException).suppressedExceptions.map { it as ApolloException }
                                    .any { it is ApolloNetworkException || it is ApolloParseException })
                                UIErrorType.Network else UIErrorType.Other(e.message ?: "Something went wrong!"),
                            isSuccessUpdateCertificate = false
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
                            isSuccessUpdateCertificate = false
                        )
                    }
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                isSuccessUpdateCertificate = true
                            )
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = true,
                                error = UIErrorType.Other(data.errors.toString()),
                                isSuccessUpdateCertificate = false
                            )
                        }
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
