package co.nexlabs.betterhr.joblanding.local_storage

object LocalStorageProvider {
    lateinit var instance: LocalStorage

    private set

    fun initialize(factory: () -> LocalStorage) {
        instance = factory()
    }
}