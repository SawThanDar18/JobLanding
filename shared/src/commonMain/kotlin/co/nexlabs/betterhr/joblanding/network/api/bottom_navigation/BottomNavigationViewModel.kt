package co.nexlabs.betterhr.joblanding.network.api.bottom_navigation

import android.app.Application
import co.nexlabs.betterhr.joblanding.local_storage.AndroidLocalStorageImpl
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import moe.tlaster.precompose.viewmodel.ViewModel

class BottomNavigationViewModel(application: Application): ViewModel() {
    private val localStorage: LocalStorage
    init {
        localStorage = AndroidLocalStorageImpl(application.applicationContext)
    }

    fun getBearerToken(): String {
        return localStorage.bearerToken
    }
}