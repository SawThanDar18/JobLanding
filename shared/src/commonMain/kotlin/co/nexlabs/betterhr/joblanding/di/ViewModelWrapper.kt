package co.nexlabs.betterhr.joblanding.di

import co.nexlabs.betterhr.joblanding.network.choose_country.ChooseCountryViewModel
import co.nexlabs.betterhr.joblanding.network.choose_country.data.Data
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelWrapper(private val viewModel: ChooseCountryViewModel) {
    private val scope = CoroutineScope(Dispatchers.Main)

    fun observeCountries(callback: (List<Data>) -> Unit) {
        scope.launch {
            callback(viewModel.uiState.value.countries)
        }
    }
}