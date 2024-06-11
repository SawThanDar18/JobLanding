package co.nexlabs.betterhr.joblanding.network.api.bottom_navigation

import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import moe.tlaster.precompose.viewmodel.ViewModel

class BottomNavigationViewModel(private val localStorage: LocalStorage): ViewModel() {

    fun getBearerToken(): String {
        return localStorage.bearerToken
    }
}