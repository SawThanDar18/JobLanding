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
    override var countryName: String
        get() = preferences.getString("country_name", "") ?: ""
        set(value) {
            preferences.edit().putString("country_name", value).apply()
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
    override var applied: Boolean
        get() = preferences.getBoolean("applied", true)
        set(value) {
            preferences.edit().putBoolean("applied", value).apply()
        }
    override var qualified: Boolean
        get() = preferences.getBoolean("qualified", true)
        set(value) {
            preferences.edit().putBoolean("qualified", value).apply()
        }
    override var interviewing: Boolean
        get() = preferences.getBoolean("interviewing", true)
        set(value) {
            preferences.edit().putBoolean("interviewing", value).apply()
        }
    override var offered: Boolean
        get() = preferences.getBoolean("offer", true)
        set(value) {
            preferences.edit().putBoolean("offer", value).apply()
        }
    override var rejected: Boolean
        get() = preferences.getBoolean("rejected", true)
        set(value) {
            preferences.edit().putBoolean("rejected", value).apply()
        }
    override var complete: Boolean
        get() = preferences.getBoolean("complete", true)
        set(value) {
            preferences.edit().putBoolean("complete", value).apply()
        }
    override var pending: Boolean
        get() = preferences.getBoolean("pending", true)
        set(value) {
            preferences.edit().putBoolean("pending", value).apply()
        }
    override var inboxRejected: Boolean
        get() = preferences.getBoolean("inbox-rejected", true)
        set(value) {
            preferences.edit().putBoolean("inbox-rejected", value).apply()
        }
    override var pin: Boolean
        get() = preferences.getBoolean("pin", true)
        set(value) {
            preferences.edit().putBoolean("pin", value).apply()
        }
}