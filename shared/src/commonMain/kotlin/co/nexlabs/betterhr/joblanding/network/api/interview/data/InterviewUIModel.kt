package co.nexlabs.betterhr.joblanding.network.api.interview.data

data class InterviewUIModel(
    val id: String,
    val candidateId: String,
    val referenceNotificationId: String,
    val jobId: String,
    val interviewType: String,
    val interviewRound: String,
    val interviewDate: String,
    val interviewStartTime: String,
    val interviewEndTime: String
)