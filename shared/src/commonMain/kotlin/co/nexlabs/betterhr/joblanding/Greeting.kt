package co.nexlabs.betterhr.joblanding

class Greeting {
    private val platform: Platform = getPlatform()

    fun greets(): String {
        return "Hello, ${platform.name}!"
    }
}