package co.nexlabs.betterhr.joblanding.network.api.login.data

import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class QRLogInRepository(private val jobLandingService: JobLandingService) {
    suspend fun qrScanLogIn(qrToken: String) = jobLandingService.scanWebLogIn(qrToken)
}