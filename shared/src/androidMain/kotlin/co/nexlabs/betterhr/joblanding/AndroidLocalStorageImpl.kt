package co.nexlabs.betterhr.joblanding

import android.content.Context
import android.preference.PreferenceManager
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage

class AndroidLocalStorageImpl(context: Context): LocalStorage {
    //private val preferences = AndroidSettings.Factory(context).create("job_landing_preferences")
    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    override var countryId: String
        get() = preferences.getString("country_id", "") ?: ""
        set(value) {
            preferences.edit().putString("country_id", value)
        }
    override var pageId: String
        get() = preferences.getString("page_id", "") ?: ""
        set(value) {
            preferences.edit().putString("page_id", value)
        }

    override var token: String
        get() = preferences.getString("token", "") ?: ""
        set(value) {
            preferences.edit().putString("token", value)
        }

    override var bearerToken: String
        get() = preferences.getString("bearerToken", "") ?: ""
        set(value) {
            preferences.edit().putString("bearerToken", value)
        }

    override var candidateId: String
        get() = preferences.getString("candidateId", "") ?: ""
        set(value) {
            preferences.edit().putString("candidateId", value)
        }

    override var phone: String
        get() = preferences.getString("phone", "") ?: ""
        set(value) {
            preferences.edit().putString("phone", value)
        }
}