package co.nexlabs.betterhr.joblanding.network.choose_country

import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.choose_country.data.ChooseCountryRepository
import co.nexlabs.betterhr.joblanding.network.choose_country.data.ChooseCountryUIState
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Data
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Item
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import co.nexlabs.betterhr.joblanding.viewmodel.CountriesListViewModelMapper
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

class ChooseCountryViewModel(
    private val localStorage: LocalStorage,
    private val chooseCountryRepository: ChooseCountryRepository
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ChooseCountryUIState())
    val uiState = _uiState.asStateFlow()

    fun getCountriesList() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = UIErrorType.Nothing
                )
            }


            var countriesList: MutableList<Data> = ArrayList()
            countriesList.addAll(
                CountriesListViewModelMapper.mapResponseToViewModel(
                    chooseCountryRepository.getCountriesList()
                )
            )

            var itemList: MutableList<Item> = ArrayList()
            if (countriesList.isNotEmpty() || countriesList != null) {
                countriesList.map {
                    itemList.add(
                        Item(it.id, it.countryName)
                    )
                }
            }

            if (itemList.isNotEmpty() || itemList != null) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = UIErrorType.Nothing,
                        countries = countriesList,
                        items = itemList
                    )
                }
            }
        }
    }

    fun getDynamicPagesId(countryId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            chooseCountryRepository.getDynamicPages(countryId).toFlow()
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
                        if (data.data!!.dynamicPages.isNotEmpty()) {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = if (data.data == null || data.data!!.dynamicPages.isNullOrEmpty()) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                    dynamicPageId = data.data?.dynamicPages!![0].id,
                                )
                            }
                        } else {
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    error = UIErrorType.Other("No Data!")
                                )
                            }
                        }
                    } else {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = UIErrorType.Other(data.errors.toString())
                            )
                        }
                    }
                }
        }
    }

    fun updateCountryId(countryId: String) {
        localStorage.countryId = countryId
    }

    fun getCountryId(): String {
        return localStorage.countryId
    }

    fun updatePageId(pageId: String) {
        localStorage.pageId = pageId
    }
}