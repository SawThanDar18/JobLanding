package co.nexlabs.betterhr.joblanding.network.choose_country

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.nexlabs.betterhr.joblanding.network.choose_country.data.ChooseCountryRepository
import co.nexlabs.betterhr.joblanding.network.choose_country.data.ChooseCountryUIState
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Data
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Item
import co.nexlabs.betterhr.joblanding.viewmodel.CountriesListViewModelMapper
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
class ChooseCountryViewModel(private val chooseCountryRepository: ChooseCountryRepository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(ChooseCountryUIState())
    val uiState = _uiState.asStateFlow()

    var countriesList: MutableList<Data> = ArrayList()
    var itemList: MutableList<Item> = ArrayList()

    var dynamicPagesID: MutableLiveData<String> = MutableLiveData()

    fun getCountriesList() {
        viewModelScope.launch(Dispatchers.IO) {
            chooseCountryRepository.getDynamicPages().toFlow()
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
                }.collectLatest {
                    if (!it.hasErrors()) {
                       if (it.data?.dynamicPages!![0].id != null && it.data?.dynamicPages!![0].id != "") {
                           dynamicPagesID.postValue(it.data?.dynamicPages!![0].id)
                       }
                    }
                }


            countriesList.clear()
            countriesList.addAll(
                CountriesListViewModelMapper.mapResponseToViewModel(
                    chooseCountryRepository.getCountriesList()
                )
            )

            if (countriesList.isNotEmpty() || countriesList != null) {
                itemList.clear()
                countriesList.map {
                    itemList.add(
                        Item(it.id, it.countryName)
                    )
                }
            }
        }

        _uiState.update {
            it.copy(
                countries = countriesList,
                items = itemList
            )
        }
    }
}