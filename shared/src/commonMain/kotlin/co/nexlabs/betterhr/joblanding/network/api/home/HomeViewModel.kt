package co.nexlabs.betterhr.joblanding.network.api.home

import android.util.Log
import co.nexlabs.betterhr.joblanding.network.api.home.data.HomeRepository
import co.nexlabs.betterhr.joblanding.network.api.home.data.HomeUIModel
import co.nexlabs.betterhr.joblanding.network.api.home.data.HomeUIState
import co.nexlabs.betterhr.joblanding.network.choose_country.data.ChooseCountryUIState
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Data
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Item
import co.nexlabs.betterhr.joblanding.viewmodel.HomeViewModelMapper
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

class HomeViewModel(private val homeRepository: HomeRepository): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUIState())
    val uiState = _uiState.asStateFlow()

    var jobLandingSectionList: MutableList<HomeUIModel> = ArrayList()

    fun getJobLandingSections(pageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            homeRepository.getJobLandingSections(pageId).toFlow()
                .catch { e ->
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
                    if (!data.hasErrors()) {
                        _uiState.update {
                            it.copy(
                                jobLandingSectionsList = HomeViewModelMapper.mapResponseToViewModel(data.data!!)
                            )
                        }
                        /*jobLandingSectionList.clear()
                        jobLandingSectionList.addAll(
                            HomeViewModelMapper.mapResponseToViewModel(it.data!!)
                        )*/
                    } else {
                        Log.d("result>>", "it.hasErrors")
                    }
                }
        }
    }
}