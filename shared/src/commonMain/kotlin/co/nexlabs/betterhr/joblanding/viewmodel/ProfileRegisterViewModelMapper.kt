package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.with_auth.CreateCandidateMutation
import co.nexlabs.betterhr.joblanding.network.register.data.AccountCreationCandidateDataUIModel
import co.nexlabs.betterhr.joblanding.network.register.data.AccountCreationData
import co.nexlabs.betterhr.joblanding.network.register.data.ProfileRegisterUIModel

object ProfileRegisterViewModelMapper {
    fun mapCreateCandidateToViewModel(data: CreateCandidateMutation.Data): ProfileRegisterUIModel {
        return ProfileRegisterUIModel(
            AccountCreationData(
                AccountCreationCandidateDataUIModel(
                    data.createCandidate!!.id ?: "",
                    data.createCandidate.name,
                    data.createCandidate.email ?: ""
                )
            )
        )
    }
}