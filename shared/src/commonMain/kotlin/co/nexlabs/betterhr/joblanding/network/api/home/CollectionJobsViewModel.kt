package co.nexlabs.betterhr.joblanding.network.api.home

import android.util.Log
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CollectionJobsRepository
import co.nexlabs.betterhr.joblanding.network.api.home.home_details.CollectionJobsUIState
import co.nexlabs.betterhr.joblanding.viewmodel.CollectionJobsViewModelMapper
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

class CollectionJobsViewModel(private val collectionJobsRepository: CollectionJobsRepository): ViewModel() {

    private val _uiState = MutableStateFlow(CollectionJobsUIState())
    val uiState = _uiState.asStateFlow()

    fun getCollectionJobs(collectionId: String, isPaginate: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            collectionJobsRepository.getCollectionJobs(collectionId, isPaginate).toFlow()
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
                                collectionJobsList = CollectionJobsViewModelMapper.mapResponseToViewModel(data.data!!)
                            )
                        }
                    } else {
                        Log.d("result>>", "it.hasErrors")
                    }
                }
        }
    }

}