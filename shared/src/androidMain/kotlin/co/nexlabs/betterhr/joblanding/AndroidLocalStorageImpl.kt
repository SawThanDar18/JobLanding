package co.nexlabs.betterhr.joblanding

import android.content.Context
import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage

class AndroidLocalStorageImpl(context: Context): LocalStorage {
    //private val preferences = AndroidSettings.Factory(context).create("job_landing_preferences")
    private val preferences = context.getSharedPreferences("job_landing_preferences", Context.MODE_PRIVATE)

    override var countryId: String
        get() = preferences.getString("country_id", "") ?: ""
        set(value) {
            preferences.edit().putString("country_id", value).apply()
        }
    override var pageId: String
        get() = preferences.getString("page_id", "") ?: ""
        set(value) {
            preferences.edit().putString("page_id", value).apply()
        }

    override var token: String
        get() = preferences.getString("token", "") ?: ""
        set(value) {
            preferences.edit().putString("token", value).apply()
        }

    override var bearerToken: String
        get() = preferences.getString("bearerToken", "") ?: ""
        set(value) {
            preferences.edit().putString("bearerToken", value).apply()
        }

    override var candidateId: String
        get() = preferences.getString("candidateId", "") ?: ""
        set(value) {
            preferences.edit().putString("candidateId", value).apply()
        }

    override var phone: String
        get() = preferences.getString("phone", "") ?: ""
        set(value) {
            preferences.edit().putString("phone", value).apply()
        }
}