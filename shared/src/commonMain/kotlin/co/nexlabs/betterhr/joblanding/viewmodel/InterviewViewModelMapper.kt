package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.with_auth.FetchInterviewQuery
import co.nexlabs.betterhr.joblanding.network.api.interview.data.InterviewUIModel

object InterviewViewModelMapper {
    fun mapResponseToViewModel(data: FetchInterviewQuery.Data): List<InterviewUIModel> {
        return data.upcomingInterviews.map {
            mapDataToInterview(it)
        }
    }

    fun mapDataToInterview(data: FetchInterviewQuery.UpcomingInterview): InterviewUIModel {
        return InterviewUIModel(
            data.id ?: "",
            data.candidate_id ?: "",
            data.reference_notification_id ?: "",
            data.job_id ?: "",
            data.interview_type ?: "",
            data.interview_round ?: "",
            data.interview_date ?: "",
            data.interview_start_time ?: "",
            data.interview_end_time ?: ""
        )
    }
}