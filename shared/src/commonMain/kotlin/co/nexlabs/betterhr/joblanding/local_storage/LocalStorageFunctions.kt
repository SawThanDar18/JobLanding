package co.nexlabs.betterhr.joblanding.local_storage

object LocalStorageFunctions {

    fun updateCountryId(localStorage: LocalStorage, countryId: String) {
        localStorage.countryId = countryId
    }

    fun getCountryId(localStorage: LocalStorage): String {
        return localStorage.countryId
    }

    fun updateToken(localStorage: LocalStorage, token: String) {
        localStorage.token = token
    }

    fun getToken(localStorage: LocalStorage): String {
        return localStorage.token
    }
}