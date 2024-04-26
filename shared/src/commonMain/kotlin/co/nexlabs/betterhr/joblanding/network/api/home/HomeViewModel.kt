package co.nexlabs.betterhr.joblanding.network.api.home

import android.app.Application
import android.util.Log
import co.nexlabs.betterhr.joblanding.local_storage.AndroidLocalStorageImpl
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.api.home.data.HomeRepository
import co.nexlabs.betterhr.joblanding.network.api.home.data.HomeUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.HomeUIState
import co.nexlabs.betterhr.joblanding.network.choose_country.data.ChooseCountryUIState
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Data
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Item
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import co.nexlabs.betterhr.joblanding.viewmodel.HomeViewModelMapper
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

class HomeViewModel(application: Application, private val homeRepository: HomeRepository): ViewModel() {

    private val localStorage: LocalStorage

    init {
        localStorage = AndroidLocalStorageImpl(application.applicationContext)
    }

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    fun getJobLandingSections(pageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.getJobLandingSections(pageId).toFlow()
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
                                jobLandingSectionsList = HomeViewModelMapper.mapResponseToViewModel(data.data!!)
                            )
                        }
                    } else {
                        Log.d("result>>", "it.hasErrors")
                    }
                }
        }
    }

    fun getCountryId(): String {
        return localStorage.countryId
    }

    fun getToken(): String {
        return localStorage.token
    }
}