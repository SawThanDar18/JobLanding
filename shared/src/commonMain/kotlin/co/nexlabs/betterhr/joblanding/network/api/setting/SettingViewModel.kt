package co.nexlabs.betterhr.joblanding.network.api.setting

import android.app.Application
import co.nexlabs.betterhr.joblanding.local_storage.AndroidLocalStorageImpl
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import moe.tlaster.precompose.viewmodel.ViewModel

class SettingViewModel(application: Application): ViewModel() {
    private val localStorage: LocalStorage

    init {
        localStorage = AndroidLocalStorageImpl(application)
    }

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