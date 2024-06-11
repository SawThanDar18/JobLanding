plugins {
    //trick: for the same plugin versions in all sub-modules
    id("com.android.application").version("8.2.1").apply(false)
    id("com.android.library").version("8.2.1").apply(false)
    kotlin("android").version("1.9.22").apply(false)
    kotlin("multiplatform").version("1.9.22").apply(false)
    id("org.jetbrains.compose").version("1.5.12").apply(false)
    id("com.apollographql.apollo3") version "3.8.2" apply false
    kotlin("plugin.serialization") version "1.6.10"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}