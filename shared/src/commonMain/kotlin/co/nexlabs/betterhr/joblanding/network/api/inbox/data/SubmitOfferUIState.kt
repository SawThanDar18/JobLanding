package co.nexlabs.betterhr.joblanding.network.api.inbox.data

import co.nexlabs.betterhr.joblanding.network.api.request_response.FileUploadResponse
import co.nexlabs.betterhr.joblanding.util.UIErrorType

data class SubmitOfferUIState(
    val isLoading: Boolean = true,
    val error: UIErrorType = UIErrorType.Nothing,
    val isSuccessUploadFile: Boolean = false,
    val fileList: FileUploadResponse = FileUploadResponse(
        "", "", "", "", "", ""
    ),
)