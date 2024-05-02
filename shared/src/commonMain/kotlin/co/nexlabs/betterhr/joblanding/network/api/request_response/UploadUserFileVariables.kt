package co.nexlabs.betterhr.joblanding.network.api.request_response

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.formData
import io.ktor.http.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class UploadUserFileVariables(
    val file: String?,
    val type: String,
    val candidate_id: String
)

@Serializable
data class UploadUserFileRequest(
    val query: String,
    val variables: UploadUserFileVariables
)

suspend fun uploadUserFile(file: File?, type: String, candidateId: String) {

    /*try {
        val request = UploadUserFileRequest(
            query = "mutation UploadUserFile(\$file: Upload!, \$type: String!, \$candidate_id: String!) { uploadUserFile(file: \$file, type: \$type, candidate_id: \$candidate_id) { id } }",
            variables = UploadUserFileVariables(
                file = file?.path,
                type = type,
                candidate_id = candidateId
            )
        )

        val formData = formData {
            append("operations", Json.encodeToString(request))
            append("map", "{\"0\":[\"variables.file\"]}")

            // If you have additional form fields, you can append them here
            // append("field_name", "field_value")
        }

        val response = client.post<String> {
            url("your_graphql_endpoint_here")
            body = formData
        }

        println("Response: $response")
    } catch (e: Exception) {
        println("Error: ${e.message}")
    } finally {
        client.close()
    }*/
}
