package co.nexlabs.betterhr.joblanding.local_storage

import android.content.Context
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CandidateUIModel
import com.russhwolf.settings.AndroidSettings
import com.russhwolf.settings.get

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

    override var candidateId: String
        get() = preferences.getString("candidateId", "")
        set(value) {
            preferences.putString("candidateId", value)
        }
}