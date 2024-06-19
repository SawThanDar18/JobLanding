package co.nexlabs.betterhr.joblanding.network.api.home

import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CollectionCompaniesRepository
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CollectionCompaniesUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CollectionCompaniesUIState
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CollectionJobsUIModel
import co.nexlabs.betterhr.joblanding.network.register.UiState
import co.nexlabs.betterhr.joblanding.util.UIErrorType
import co.nexlabs.betterhr.joblanding.viewmodel.CollectionCompaniesViewModelMapper
import co.nexlabs.betterhr.joblanding.viewmodel.CollectionJobsViewModelMapper
import co.nexlabs.betterhr.joblanding.viewmodel.HomeViewModelMapper
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.apollographql.apollo3.exception.ApolloNetworkException
import com.apollographql.apollo3.exception.ApolloParseException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class CollectionCompaniesViewModel(private val collectionJobsRepository: CollectionCompaniesRepository): ViewModel() {
    private val _uiState = MutableStateFlow(CollectionCompaniesUIState())
    val uiState = _uiState.asStateFlow()

    fun observeUiState(onChange: (CollectionCompaniesUIState) -> Unit) {
        viewModelScope.launch {
            uiState.collect { state ->
                onChange(state)
            }
        }
    }

    private var currentPage = 1

    private val _items = MutableStateFlow<List<CollectionCompaniesUIModel>>(emptyList())
    val items: StateFlow<List<CollectionCompaniesUIModel>> get() = _items

    fun loadMoreItems(collectionId: String) {
        viewModelScope.launch {
            getCollectionCompanies(collectionId, true, PAGE_SIZE, currentPage)
            currentPage++
        }
    }

    fun getCollectionCompanies(collectionId: String, isPaginate: Boolean, limit: Int, page: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            collectionJobsRepository.getCollectionCompanies(collectionId, isPaginate, limit, page).toFlow()
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
                        _items.value += CollectionCompaniesViewModelMapper.mapResponseToViewModel(
                            data.data!!
                        )

                        //collectionCompaniesList.addAll(CollectionCompaniesViewModelMapper.mapResponseToViewModel(data.data!!))
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = if (data.data == null) UIErrorType.Other("API returned empty list") else UIErrorType.Nothing,
                                collectionCompaniesList = CollectionCompaniesViewModelMapper.mapResponseToViewModel(data.data!!)
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

    companion object {
        private const val PAGE_SIZE = 20
    }

}