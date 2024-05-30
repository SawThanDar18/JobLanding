package co.nexlabs.betterhr.joblanding.network.api

import androidx.compose.runtime.*
import androidx.lifecycle.MutableLiveData
import moe.tlaster.precompose.viewmodel.ViewModel

class SharedViewModel: ViewModel() {
    var sharedData: String by mutableStateOf("")
}