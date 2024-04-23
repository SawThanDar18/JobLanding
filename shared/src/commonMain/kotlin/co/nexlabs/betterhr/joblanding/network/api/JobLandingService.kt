package co.nexlabs.betterhr.joblanding.network.api

import co.nexlabs.betterhr.joblanding.DynamicPagesQuery
import co.nexlabs.betterhr.joblanding.JobLandingCollectionCompaniesQuery
import co.nexlabs.betterhr.joblanding.JobLandingCollectionJobsQuery
import co.nexlabs.betterhr.joblanding.JobLandingCompanyDetailQuery
import co.nexlabs.betterhr.joblanding.JobLandingJobDetailQuery
import co.nexlabs.betterhr.joblanding.JobLandingSectionsQuery
import co.nexlabs.betterhr.joblanding.network.api.request_response.GetCountriesListResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationCodeRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.SendVerificationResponse
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyOTPRequest
import co.nexlabs.betterhr.joblanding.network.api.request_response.VerifyPhoneNumResponse
import com.apollographql.apollo3.ApolloCall

interface JobLandingService {

    suspend fun sendVerification(body: SendVerificationCodeRequest): SendVerificationResponse

    suspend fun validateCode(body: VerifyOTPRequest): VerifyPhoneNumResponse

    suspend fun getCountriesList(): GetCountriesListResponse

    suspend fun getDynamicPagesQuery(countryId: String, platform: String): ApolloCall<DynamicPagesQuery.Data>

    suspend fun getJobLandingSections(pageId: String): ApolloCall<JobLandingSectionsQuery.Data>

    suspend fun getJobLandingCollectionCompanies(collectionId: String, isPaginate: Boolean): ApolloCall<JobLandingCollectionCompaniesQuery.Data>

    suspend fun getJobLandingCollectionJobs(collectionId: String, isPaginate: Boolean): ApolloCall<JobLandingCollectionJobsQuery.Data>

    suspend fun getJobDetail(jobId: String): ApolloCall<JobLandingJobDetailQuery.Data>

    suspend fun getCompanyDetail(companyId: String): ApolloCall<JobLandingCompanyDetailQuery.Data>

}