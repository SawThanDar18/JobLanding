package co.nexlabs.betterhr.joblanding.network.register.data

import co.nexlabs.betterhr.job.with_auth.CreateSkillMutation
import co.nexlabs.betterhr.joblanding.FileUri
import co.nexlabs.betterhr.joblanding.network.api.JobLandingService
import com.apollographql.apollo3.ApolloCall

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

    suspend fun createPosition(positionName: String) = jobLandingService.createPosition(positionName)

    suspend fun createExperience(
        positionId: String,
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
        positionId, candidateId, companyId, title, location, experienceLevel, employmentType, startDate, endDate, isCurrentJob, description
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

    suspend fun createEducation(
        countryName: String,
        institution: String,
        educationLevel: String,
        degree: String,
        fieldOfStudy: String,
        startDate: String,
        endDate: String,
        isCurrentStudy: Boolean,
        description: String,
        candidateId: String
    ) = jobLandingService.createEducation(countryName, institution, educationLevel, degree, fieldOfStudy, startDate, endDate, isCurrentStudy, description, candidateId)

    suspend fun updateEducation(
        id: String,
        countryName: String,
        institution: String,
        educationLevel: String,
        degree: String,
        fieldOfStudy: String,
        startDate: String,
        endDate: String,
        isCurrentStudy: Boolean,
        description: String
    ) = jobLandingService.updateEducation(id, countryName, institution, educationLevel, degree, fieldOfStudy, startDate, endDate, isCurrentStudy, description)

    suspend fun createLanguage(
        name: String,
        proficiencyLevel: String,
        candidateId: String
    ) = jobLandingService.createLanguage(name, proficiencyLevel, candidateId)

    suspend fun updateLanguage(
        id: String,
        name: String,
        proficiencyLevel: String
    ) = jobLandingService.updateLanguage(id, name, proficiencyLevel)

    suspend fun createSkill(
        name: String,
        candidateId: String
    ) = jobLandingService.createSkill(name, candidateId)

    suspend fun updateSkill(
        id: String,
        name: String
    ) = jobLandingService.updateSkill(id, name)

    suspend fun createCertification(
        candidateId: String,
        courseName: String,
        issuingOrganization: String,
        issueDate: String,
        expireDate: String,
        isExpire: Boolean,
        credentialUrl: String
    ) = jobLandingService.createCertification(candidateId, courseName, issuingOrganization, issueDate, expireDate, isExpire, credentialUrl)

    suspend fun updateCertification(
        id: String,
        courseName: String,
        issuingOrganization: String,
        issueDate: String,
        expireDate: String,
        isExpire: Boolean,
        credentialUrl: String
    ) = jobLandingService.updateCertification(id, courseName, issuingOrganization, issueDate, expireDate, isExpire, credentialUrl)

}