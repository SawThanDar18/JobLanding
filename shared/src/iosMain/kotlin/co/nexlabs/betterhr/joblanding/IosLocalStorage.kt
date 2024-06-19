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
    override var countryName: String
        get() = userDefaults.stringForKey("country_name") ?: ""
        set(value) {
            userDefaults.setObject(value, "country_name")
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

    override var applied: Boolean
        get() = userDefaults.booleanForKey("applied", true)
        set(value) {
            userDefaults.setObject("applied", value)
        }
    override var qualified: Boolean
        get() = userDefaults.booleanForKey("qualified", true)
        set(value) {
            userDefaults.setObject("qualified", value)
        }
    override var interviewing: Boolean
        get() = userDefaults.booleanForKey("interviewing", true)
        set(value) {
            userDefaults.setObject("interviewing", value)
        }
    override var offered: Boolean
        get() = userDefaults.booleanForKey("offer", true)
        set(value) {
            userDefaults.setObject("offer", value)
        }
    override var rejected: Boolean
        get() = userDefaults.booleanForKey("rejected", true)
        set(value) {
            userDefaults.setObject("rejected", value)
        }
    override var complete: Boolean
        get() = userDefaults.booleanForKey("complete", true)
        set(value) {
            userDefaults.setObject("complete", value)
        }
    override var pending: Boolean
        get() = userDefaults.booleanForKey("pending", true)
        set(value) {
            userDefaults.setObject("pending", value)
        }
    override var inboxRejected: Boolean
        get() = userDefaults.booleanForKey("inbox-rejected", true)
        set(value) {
            userDefaults.setObject("inbox-rejected", value)
        }
    override var pin: Boolean
        get() = userDefaults.booleanForKey("pin", true)
        set(value) {
            userDefaults.setObject("pin", value)
        }
}
