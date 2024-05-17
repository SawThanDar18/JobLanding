package co.nexlabs.betterhr.joblanding.network.api.inbox.data

import android.net.Uri
import co.nexlabs.betterhr.joblanding.network.api.JobLandingService

class InboxRepository(private val jobLandingService: JobLandingService) {

    suspend fun fetchInbox(
        status: List<String>,
        search: String,
        limit: Int
    ) = jobLandingService.fetchNotification(status, search, limit)

    suspend fun fetchInboxById(id: String) = jobLandingService.fetchNotificationById(id)

    suspend fun uploadMultipleFiles(
        files: MutableList<Uri?>,
        fileNames: MutableList<String?>,
        types: List<String>,
        candidateId: String
    ) = jobLandingService.uploadMultipleFiles(files, fileNames, types, candidateId)

    suspend fun responseAssignment(
        candidateId: String,
        jobId: String,
        referenceId: String,
        title: String,
        description: String,
        status: String,
        summitedDate: String,
        candidateDescription: String,
        endTime: String,
        attachments: String,
        subDomain: String,
        referenceApplicationId: String
    ) = jobLandingService.responseAssignment(candidateId, jobId, referenceId, title, description, status, summitedDate, candidateDescription, endTime, attachments, subDomain, referenceApplicationId)

    suspend fun uploadSingleFile(
        file: Uri,
        fileName: String,
        type: String,
        candidateId: String
    ) = jobLandingService.uploadSingleFile(file, fileName, type, candidateId)

    suspend fun responseOffer(
        id: String,
        note: String,
        status: String,
        responseDate: String,
        attachments: String,
        subDomain: String
    ) = jobLandingService.responseOffer(id, note, status, responseDate, attachments, subDomain)
}