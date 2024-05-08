package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.with_auth.CandidateQuery
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CandidateUIModel

object CandidateViewModelMapper {
    fun mapDataToViewModel(data: CandidateQuery.Me): CandidateUIModel {

        var profileList: MutableList<String> = ArrayList()
        var cvFileList: MutableList<String> = ArrayList()
        var cvFileNameList: MutableList<String> = ArrayList()
        var coverLetterFileList: MutableList<String> = ArrayList()
        var coverLetterFileNameList: MutableList<String> = ArrayList()

        data.files!!.map {
            if (it.type == "profile") {
                profileList.add(it.full_path ?: "")
            }

            if (it.type == "cv") {
                cvFileList.add(it.full_path ?: "")
                cvFileNameList.add(it.name ?: "")
            }

            if (it.type == "cover_letter") {
                coverLetterFileList.add(it.full_path ?: "")
                coverLetterFileNameList.add(it.name ?: "")
            }
        }


        var profilePath = if (profileList.isEmpty()) {
            ""
        } else {
            if (profileList.size == 1) {
                profileList[0]
            } else {
                profileList[profileList.size - 1]
            }
        }

        var cvFilePath = if (cvFileList.isEmpty()) {
            ""
        } else {
            if (cvFileList.size == 1) {
                cvFileList[0]
            } else {
                cvFileList[cvFileList.size - 1]
            }
        }

        var cvFileName = if (cvFileNameList.isEmpty()) {
            ""
        } else {
            if (cvFileNameList.size == 1) {
                cvFileNameList[0]
            } else {
                cvFileNameList[cvFileNameList.size - 1]
            }
        }

        var coverLetterFilePath = if (coverLetterFileList.isEmpty()) {
            ""
        } else {
            if (coverLetterFileList.size == 1) {
                coverLetterFileList[0]
            } else {
                coverLetterFileList[coverLetterFileList.size - 1]
            }
        }

        var coverLetterFileName = if (coverLetterFileNameList.isEmpty()) {
            ""
        } else {
            if (coverLetterFileNameList.size == 1) {
                coverLetterFileNameList[0]
            } else {
                coverLetterFileNameList[coverLetterFileNameList.size - 1]
            }
        }

        return CandidateUIModel(
            data.id ?: "",
            data.name,
            data.email,
            data.phone,
            data.summary ?: "",
            data.desired_position ?: "",
            data.country_id ?: "",
            profilePath,
            cvFileName.replace(" ", "_"),
            cvFilePath,
            coverLetterFileName.replace(" ", "_"),
            coverLetterFilePath,
            emptyList(),
            emptyList(),
        )
    }
}