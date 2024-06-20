package co.nexlabs.betterhr.joblanding.network.register.data

import co.nexlabs.betterhr.joblanding.FileUri
import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class CompleteProfileRepository(private val jobLandingService: JobLandingService) {
    suspend fun getCandidateData() = jobLandingService.getCandidateDatas()

    suspend fun uploadFile(
        file: FileUri,
        fileName: String,
        type: String,
        candidateId: String
    ) = jobLandingService.uploadUserFile(file, fileName, type, candidateId)

    suspend fun updateFile(
        file: FileUri,
        fileName: String,
        type: String,
        candidateId: String,
        fileId: String
    ) = jobLandingService.updateUserFile(file, fileName, type, candidateId, fileId)

    suspend fun updateSummary(candidateId: String, summary: String) = jobLandingService.updateSummary(candidateId, summary)

    suspend fun createCompany(companyName: String, candidateId: String, fileId: String) = jobLandingService.createCompany(companyName, candidateId, fileId)

    suspend fun createExperience(
        position: String,
        candidateId: String,
        companyId: String,
        title: String,
        location: String,
        experienceLevel: String,
        employmentType: String,
        startDate: String,
        endDate: String,
        isCurrentJob: Boolean,
        description: String
    ) = jobLandingService.createExperience(
        position, candidateId, companyId, title, location, experienceLevel, employmentType, startDate, endDate, isCurrentJob, description
    )

    suspend fun updateExperience(
        id: String,
        candidateId: String,
        companyId: String,
        title: String,
        location: String,
        experienceLevel: String,
        employmentType: String,
        startDate: String,
        endDate: String,
        isCurrentJob: Boolean,
        description: String
    ) = jobLandingService.updateExperience(
        id, candidateId, companyId, title, location, experienceLevel, employmentType, startDate, endDate, isCurrentJob, description
    )
}