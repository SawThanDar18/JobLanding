package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import co.nexlabs.betterhr.joblanding.network.choose_country.ChooseCountryViewModel
import co.nexlabs.betterhr.joblanding.network.choose_country.data.ChooseCountryRepository

class ViewModelFactory {
    companion object {
        fun createChooseCountryViewModel(localStorage: LocalStorage, repository: ChooseCountryRepository): ChooseCountryViewModel {
            return ChooseCountryViewModel(localStorage, repository)
        }
    }
}
