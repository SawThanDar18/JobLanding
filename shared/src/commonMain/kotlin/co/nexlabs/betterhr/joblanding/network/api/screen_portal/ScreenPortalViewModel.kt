package co.nexlabs.betterhr.joblanding.network.api.screen_portal

import android.app.Application
import co.nexlabs.betterhr.joblanding.local_storage.AndroidLocalStorageImpl
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import moe.tlaster.precompose.viewmodel.ViewModel

class ScreenPortalViewModel(application: Application): ViewModel() {

    private val localStorage: LocalStorage

    init {
        localStorage = AndroidLocalStorageImpl(application.applicationContext)
    }

    fun getCountryId(): String {
        return localStorage.countryId
    }

    fun getPageId(): String {
        return localStorage.pageId
    }

}