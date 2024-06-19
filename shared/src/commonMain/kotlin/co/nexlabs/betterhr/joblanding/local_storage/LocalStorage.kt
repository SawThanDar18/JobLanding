package co.nexlabs.betterhr.joblanding.local_storage

interface LocalStorage {
    var countryId: String
    var countryName: String
    var pageId: String
    var token: String
    var bearerToken: String
    var candidateId: String
    var phone: String
    var applied: Boolean
    var qualified: Boolean
    var interviewing: Boolean
    var offered: Boolean
    var rejected: Boolean
    var complete: Boolean
    var pending: Boolean
    var inboxRejected: Boolean
    var pin: Boolean
}