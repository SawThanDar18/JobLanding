package co.nexlabs.betterhr.joblanding

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform