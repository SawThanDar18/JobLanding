package co.nexlabs.betterhr.joblanding.network.api.inbox.data

import co.nexlabs.betterhr.joblanding.network.api.request_response.FileUploadResponse
import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class SubmitAssignmentUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val isSuccessUploadMultipleFile: Boolean = false,
    val multiFileList: List<FileUploadResponse> = ArrayList(),
    val isAssignmentResponseSuccess: Boolean = false,
    val isSuccess: Boolean = false
)