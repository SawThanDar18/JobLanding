package co.nexlabs.betterhr.joblanding

import co.nexlabs.betterhr.joblanding.local_storage.LocalStorage
import com.russhwolf.settings.Settings
import platform.Foundation.NSUserDefaults

class IOSLocalStorage : LocalStorage {
    //private val settings: Settings = AppleSettings()
    private val userDefaults = NSUserDefaults.standardUserDefaults

    override var countryId: String
        get() = userDefaults.stringForKey("country_id") ?: ""
        set(value) {
            userDefaults.setObject(value, "country_id")
        }
    override var pageId: String
        get() = userDefaults.stringForKey("page_id") ?: ""
        set(value) {
            userDefaults.setObject(value, "page_id")
        }
    override var token: String
        get() = userDefaults.stringForKey("token") ?: ""
        set(value) {
            userDefaults.setObject(value, "token")
        }
    override var bearerToken: String
        get() = userDefaults.stringForKey("bearerToken") ?: ""
        set(value) {
            userDefaults.setObject(value, "bearerToken")
        }
    override var candidateId: String
        get() = userDefaults.stringForKey("candidateId") ?: ""
        set(value) {
            userDefaults.setObject(value, "candidateId")
        }
    override var phone: String
        get() = userDefaults.stringForKey("phone") ?: ""
        set(value) {
            userDefaults.setObject(value, "phone")
        }
}
