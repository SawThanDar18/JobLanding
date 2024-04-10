package co.nexlabs.betterhr.joblanding.network.choose_country

import co.nexlabs.betterhr.joblanding.network.choose_country.data.ChooseCountryRepository
import co.nexlabs.betterhr.joblanding.network.choose_country.data.ChooseCountryUIState
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Data
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Item
import co.nexlabs.betterhr.joblanding.viewmodel.CountriesListViewModelMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    fun getCountriesList() {
        viewModelScope.launch(Dispatchers.IO) {
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