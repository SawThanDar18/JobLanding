package co.nexlabs.betterhr.joblanding.network.api.application

import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ApplicationFilterViewModel(private val localStorage: LocalStorage) : ViewModel() {
    private val _filters = MutableStateFlow(
        mapOf(
            "Applied" to localStorage.applied,
            "Qualified" to localStorage.qualified,
            "Interviewing" to localStorage.interviewing,
            "Offered" to localStorage.offered,
            "Rejected" to localStorage.rejected
        )
    )
    val filters: StateFlow<Map<String, Boolean>> = _filters

    fun updateFilter(key: String, value: Boolean) {
        viewModelScope.launch {
            when (key) {
                "Applied" -> localStorage.applied = value
                "Qualified" -> localStorage.qualified = value
                "Interviewing" -> localStorage.interviewing = value
                "Offered" -> localStorage.offered = value
                "Rejected" -> localStorage.rejected = value
            }
            _filters.value = _filters.value.toMutableMap().apply { this[key] = value }
        }
    }
}

