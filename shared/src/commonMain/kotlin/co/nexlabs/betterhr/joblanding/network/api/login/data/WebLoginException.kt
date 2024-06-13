package co.nexlabs.betterhr.joblanding.network.api.login.data

open class WebLoginException(
    message: String? = null
): Exception(message?: "Web login exception")