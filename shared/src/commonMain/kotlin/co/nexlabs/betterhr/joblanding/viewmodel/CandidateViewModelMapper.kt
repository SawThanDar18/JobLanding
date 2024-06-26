package co.nexlabs.betterhr.joblanding.viewmodel

import co.nexlabs.betterhr.job.with_auth.CandidateQuery
import co.nexlabs.betterhr.job.with_auth.type.File
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CandidateUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CertificateUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.CompaniesUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.EducationUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.ExperienceUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.FilesUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.LanguageUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.PositionUIModel
import co.nexlabs.betterhr.joblanding.network.api.bottom_navigation.data.SkillUIModel
import kotlin.math.exp

object CandidateViewModelMapper {
    fun mapDataToViewModel(data: CandidateQuery.Me): CandidateUIModel {

        var profileList: MutableList<String> = ArrayList()
        var cvFileList: MutableList<String> = ArrayList()
        var cvFileNameList: MutableList<String> = ArrayList()
        var coverLetterFileList: MutableList<String> = ArrayList()
        var coverLetterFileNameList: MutableList<String> = ArrayList()

        var profile = FilesUIModel(
            "", "", "", ""
        )

        var cv = FilesUIModel(
            "", "", "", ""
        )

        var coverLetter = FilesUIModel(
            "", "", "", ""
        )

        data.files!!.map {
            if (it.type == "profile") {
                profile = FilesUIModel(
                    it.id ?: "", it.name ?: "", it.type, it.full_path ?: ""
                )
                profileList.add(it.full_path ?: "")
            }

            if (it.type == "cv") {
                cv = FilesUIModel(
                    it.id ?: "", it.name ?: "", it.type, it.full_path ?: ""
                )

                cvFileList.add(it.full_path ?: "")
                cvFileNameList.add(it.name ?: "")
            }

            if (it.type == "cover_letter") {
                coverLetter = FilesUIModel(
                    it.id ?: "", it.name ?: "", it.type, it.full_path ?: ""
                )

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

        /*var experienceList: MutableList<ExperienceUIModel> = ArrayList()

        data.companies?.let { companies ->
            companies.map { company ->
                company.experiences?.let {  experiences ->
                    experiences.map {
                        experienceList.add(
                            ExperienceUIModel(
                                it!!.id,
                                it.position_id ?: "",
                                it.candidate_id ?: "",
                                it.title ?: "",
                                it.location ?: "",
                                it.experience_level ?: "",
                                "",
                                it.start_date ?: "",
                                it.end_date ?: "",
                                it.is_current_job ?: false,
                                it.description ?: "",
                                it.company_id ?: "",
                                PositionUIModel(
                                    it.position!!.id ?: "",
                                    it.position.name ?: ""
                                )
                            )
                        )
                    }
                }
            }
        }

        var companiesList = data.companies?.let {
            it.map {
                CompaniesUIModel(
                    it.id ?: "",
                    it.name,
                    it.file?.let {
                        FilesUIModel(
                            it.id ?: "", it.name ?: "", "", it.full_path ?: ""
                        )
                    } ?: FilesUIModel("", "", "", ""),
                    experienceList
                )
            }
        }*/

        var experienceList: MutableList<ExperienceUIModel> = ArrayList()
        var companiesList = data.companies?.map { company ->
            company.experiences?.map { experience ->
                experienceList.add(
                    ExperienceUIModel(
                        experience!!.id,
                        experience.position_id ?: "",
                        experience.candidate_id ?: "",
                        experience.title ?: "",
                        experience.location ?: "",
                        experience.experience_level ?: "",
                        experience.employment_type ?: "",
                        experience.start_date ?: "",
                        experience.end_date ?: "",
                        experience.is_current_job ?: false,
                        experience.description ?: "",
                        experience.company_id ?: "",
                        PositionUIModel(
                            experience.position!!.id ?: "",
                            experience.position.name ?: ""
                        )
                    )
                )
            }

            CompaniesUIModel(
                company.id ?: "",
                company.name,
                company.file?.let {
                    FilesUIModel(
                        it.id ?: "", it.name ?: "", "", it.full_path ?: ""
                    )
                } ?: FilesUIModel("", "", "", ""),
                experienceList.toList()
            )
        }

        var educationList = data.educations?.map { education ->
            EducationUIModel(
                education.id ?: "",
                education.country_name,
                education.institution ?: "",
                education.education_level ?: "",
                education.degree ?: "",
                education.field_of_study ?: "",
                education.start_date ?: "",
                education.end_date ?: "",
                education.is_current_study ?: false,
                education.description ?: ""
            )
        }

        var languageList = data.languages?.map { language ->
            LanguageUIModel(
                language.id ?: "",
                language.name ?: "",
                language.proficiency_level ?: ""
            )
        }

        var skillList = data.skills?.map { skill ->
            SkillUIModel(
                skill.id ?: "",
                skill.name ?: ""
            )
        }

        var certificateList = data.certificates?.map { certificate ->
            CertificateUIModel(
                certificate.id ?: "",
                certificate.candidate_id ?: "",
                certificate.course_name ?: "",
                certificate.issuing_organization ?: "",
                certificate.issue_date ?: "",
                certificate.expire_date ?: "",
                certificate.is_expire ?: false,
                certificate.credential_url ?: ""
            )
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
            profile, cv, coverLetter,
            emptyList(),
            companiesList ?: emptyList(),
            educationList ?: emptyList(),
            languageList ?: emptyList(),
            skillList ?: emptyList(),
            certificateList ?: emptyList()
        )
    }
}