package co.nexlabs.betterhr.joblanding.local_storage

object LocalStorageProvider {
    private lateinit var _instance: LocalStorage

    val instance: LocalStorage
        get() = _instance

    fun initialize(factory: () -> LocalStorage) {
        _instance = factory()
    }
}