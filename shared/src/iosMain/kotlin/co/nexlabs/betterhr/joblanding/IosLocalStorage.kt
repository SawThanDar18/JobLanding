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
        get() = userDefaults.boolForKey("applied")
        set(value) {
            userDefaults.setBool(value, "applied")
        }
    override var qualified: Boolean
        get() = userDefaults.boolForKey("qualified")
        set(value) {
            userDefaults.setBool(value, "qualified")
        }
    override var interviewing: Boolean
        get() = userDefaults.boolForKey("interviewing")
        set(value) {
            userDefaults.setBool(value, "interviewing")
        }
    override var offered: Boolean
        get() = userDefaults.boolForKey("offer")
        set(value) {
            userDefaults.setBool(value, "offer")
        }
    override var rejected: Boolean
        get() = userDefaults.boolForKey("rejected")
        set(value) {
            userDefaults.setBool(value, "rejected")
        }
    override var complete: Boolean
        get() = userDefaults.boolForKey("complete")
        set(value) {
            userDefaults.setBool(value, "complete")
        }
    override var pending: Boolean
        get() = userDefaults.boolForKey("pending")
        set(value) {
            userDefaults.setBool(value, "pending")
        }
    override var inboxRejected: Boolean
        get() = userDefaults.boolForKey("inbox-rejected")
        set(value) {
            userDefaults.setBool(value, "inbox-rejected")
        }
    override var pin: Boolean
        get() = userDefaults.boolForKey("pin")
        set(value) {
            userDefaults.setBool(value, "pin")
        }
}
