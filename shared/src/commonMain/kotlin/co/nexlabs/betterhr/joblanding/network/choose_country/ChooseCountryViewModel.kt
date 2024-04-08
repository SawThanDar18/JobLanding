package co.nexlabs.betterhr.joblanding.network.choose_country

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope

class ChooseCountryViewModel: ViewModel() {

    private val _data = MutableStateFlow<String?>(null)
    val data: StateFlow<String?> = _data

    fun setData(value: String) {
        viewModelScope.launch {
            _data.emit(value)
        }
    }
}