package co.nexlabs.betterhr.joblanding.network.api.setting

import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import moe.tlaster.precompose.viewmodel.ViewModel

class SettingViewModel(private val localStorage: LocalStorage): ViewModel() {

    fun getPageId(): String {
        return localStorage.pageId
    }

    fun clearLocalData() {
        localStorage.token = ""
        localStorage.bearerToken = ""
        localStorage.candidateId = ""
        localStorage.phone = ""
    }
}