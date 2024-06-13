package co.nexlabs.betterhr.joblanding.network.register

import co.nexlabs.betterhr.joblanding.DispatcherProvider
import co.nexlabs.betterhr.joblanding.FileUri
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.register.data.ProfileRegisterRepository
import co.nexlabs.betterhr.joblanding.network.register.data.ProfileRegisterUIState
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

class ProfileRegisterViewModel(
    private val localStorage: LocalStorage,
    private val profileRegisterRepository: ProfileRegisterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileRegisterUIState())
    val uiState = _uiState.asStateFlow()

    fun getPageId(): String {
        return localStorage.pageId
    }

    fun getToken(): String {
        return localStorage.token
    }

    fun updateBearerToken(bearerToken: String) {
        localStorage.bearerToken = bearerToken
    }

    fun updateCandidateId(candidateId: String) {
        localStorage.candidateId = candidateId
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

            profileRegisterRepository.createCandidate(
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

    fun uploadFile(
        file: FileUri,
        fileName: String,
        type: String
    ) {
        viewModelScope.launch(DispatcherProvider.io) {
            try {
                profileRegisterRepository.uploadFile(file, fileName, type, localStorage.candidateId)
                println("fileuploadsingle>>>success")
            } catch (e: Exception) {
                println("fileuploadsingle>>>${e.message}")
            }
        }
    }


    fun getBearerToken(token: String) {

        viewModelScope.launch(DispatcherProvider.io) {
            _uiState.update {
                it.copy(
                    isSuccessForCandidateId = false,
                    isSuccessForBearerToken = false,
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            profileRegisterRepository.getBearerToken(token).toFlow()
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