package co.nexlabs.betterhr.joblanding.network.register.data

import android.net.Uri
import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class CompleteProfileRepository(private val jobLandingService: JobLandingService) {
    suspend fun getCandidateData() = jobLandingService.getCandidateDatas()

    suspend fun uploadFile(
        file: Uri,
        fileName: String,
        type: String,
        candidateId: String
    ) = jobLandingService.uploadUserFile(file, fileName, type, candidateId)

    suspend fun updateFile(
        file: Uri,
        fileName: String,
        type: String,
        candidateId: String,
        fileId: String
    ) = jobLandingService.updateUserFile(file, fileName, type, candidateId, fileId)
}