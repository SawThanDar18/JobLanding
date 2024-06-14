package co.nexlabs.betterhr.joblanding.di

import androidx.compose.runtime.Composable
import co.nexlabs.betterhr.joblanding.network.api.home.HomeViewModel
import org.koin.compose.getKoin

object KoinIOS {

    @Composable
    fun getHomeViewModel(): HomeViewModel {
        return getKoin().get()
    }
    
}