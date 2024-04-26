package co.nexlabs.betterhr.joblanding.local_storage

import android.content.Context
import com.russhwolf.settings.AndroidSettings

class AndroidLocalStorageImpl(context: Context): LocalStorage {
    private val preferences = AndroidSettings.Factory(context).create("job_landing_preferences")

    override var countryId: String
        get() = preferences.getString("country_id", "")
        set(value) {
            preferences.putString("country_id", value)
        }
    override var pageId: String
        get() = preferences.getString("page_id", "")
        set(value) {
            preferences.putString("page_id", value)
        }

    override var token: String
        get() = preferences.getString("token", "")
        set(value) {
            preferences.putString("token", value)
        }
}