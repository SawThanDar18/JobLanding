package co.nexlabs.betterhr.joblanding.network.register.data

import android.net.Uri
import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class ProfileRegisterRepository(private val jobLandingService: JobLandingService) {
    suspend fun createCandidate(
        name: String,
        email: String,
        phone: String,
        desiredPosition: String,
        summary: String,
        countryId: String
    ) = jobLandingService.createCandidate(name, email, phone, desiredPosition, summary, countryId)

    suspend fun getBearerToken(token: String) = jobLandingService.getBearerToken(token)

    suspend fun uploadFile(
        file: Uri,
        fileName: String,
        type: String,
        candidateId: String
    ) = jobLandingService.uploadUserFile(file, fileName, type, candidateId)
}