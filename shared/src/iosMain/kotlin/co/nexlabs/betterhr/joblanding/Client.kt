package co.nexlabs.betterhr.joblanding

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin

actual val Client: HttpClient
    get() = HttpClient(Darwin) {

    }