package co.nexlabs.betterhr.joblanding.local_storage

import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CandidateUIModel

interface LocalStorage {
    var countryId: String
    var pageId: String
    var token: String
    var candidateId: String
}