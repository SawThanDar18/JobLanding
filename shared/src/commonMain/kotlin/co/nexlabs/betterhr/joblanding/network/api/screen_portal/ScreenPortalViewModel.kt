package co.nexlabs.betterhr.joblanding.network.api.screen_portal

import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import moe.tlaster.precompose.viewmodel.ViewModel

class ScreenPortalViewModel(private val localStorage: LocalStorage): ViewModel() {

    fun getCountryId(): String {
        return localStorage.countryId
    }

    fun getPageId(): String {
        return localStorage.pageId
    }

    fun updatePage(pageId: String) {
        localStorage.pageId = pageId
    }

}