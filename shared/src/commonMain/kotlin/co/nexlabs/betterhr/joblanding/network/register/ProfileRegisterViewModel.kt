package co.nexlabs.betterhr.joblanding.network.register

import android.app.Application
import android.net.Uri
import android.util.Log
import co.nexlabs.betterhr.joblanding.local_storage.AndroidLocalStorageImpl
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.register.data.ProfileRegisterRepository
import co.nexlabs.betterhr.joblanding.network.register.data.ProfileRegisterUIState
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import co.nexlabs.betterhr.joblanding.viewmodel.HomeViewModelMapper
import co.nexlabs.betterhr.joblanding.viewmodel.ProfileRegisterViewModelMapper
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.apollographql.apollo3.exception.ApolloParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ProfileRegisterViewModel(
    application: Application,
    private val profileRegisterRepository: ProfileRegisterRepository
) : ViewModel() {
    private val localStorage: LocalStorage

    init {
        localStorage = AndroidLocalStorageImpl(application)
    }

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
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isSuccessForCandidateId = false,
                    isSuccessForBearerToken = false,
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }

            profileRegisterRepository.createCandidate(
                //name, email, localStorage.phone, desiredPosition, summary, localStorage.countryId
                name, "emememailemail@gmail.com", "+959254614870", desiredPosition, summary, localStorage.countryId
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

    fun uploadFile(
        file: Uri,
        fileName: String,
        type: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                var response = profileRegisterRepository.uploadFile(file, fileName, type, localStorage.candidateId)
                Log.d("response>>", response.id)
            } catch (e: Exception) {
                Log.d("error>>", e.message.toString())
            }
        }
    }


    fun getBearerToken(token: String) {

        viewModelScope.launch(Dispatchers.IO) {
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