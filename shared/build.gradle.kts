plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("com.apollographql.apollo3")
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.animation)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                api("com.apollographql.apollo3:apollo-runtime:3.8.2")
                api("com.apollographql.apollo3:apollo-normalized-cache:3.8.2")

                api("io.insert-koin:koin-core:3.4.3")
                api("io.insert-koin:koin-compose:1.0.4")

                api("moe.tlaster:precompose:1.5.0")
                api("moe.tlaster:precompose-viewmodel:1.5.0")
                api("moe.tlaster:precompose-koin:1.5.0")

                implementation(KTOR.clientCore)
                implementation(KTOR.clientLogging)
                implementation(KTOR.clientSerilization)
                implementation(KTOR.serilization)
                implementation(KTOR.clientNegotiation)
                implementation(KTOR.clientEncoding)
                implementation(KTOR.ktorSerialization)
                implementation(KTOR.kotlinXSerialization)
                implementation(KTOR.ktorJson)
                implementation(KTOR.ktorAuth)

                implementation(ReactiveX.rxjava)
                implementation(ReactiveX.rxandroid)
                implementation(ReactiveX.rxKotlin)

                implementation(AndroidArchLifeCycle.livedata)

                implementation("com.squareup.okhttp3:okhttp:4.9.1")
                implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

                implementation("io.github.joelkanyi:sain:2.0.3")

            }
        }
        val androidMain by getting {
            dependencies {
                api("androidx.activity:activity-compose:1.7.2")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.10.1")

                implementation(KTOR.clientCore)
                implementation(KTOR.clientAndroid)
                implementation(KTOR.clientOKHttp)
                implementation(KTOR.kotlinXSerialization)
                implementation(KTOR.ktorJson)
                implementation(KTOR.clientLogging)
                implementation(KTOR.clientSerilization)
                implementation(KTOR.clientNegotiation)
                implementation(KTOR.ktorSerialization)

                implementation(ReactiveX.rxjava)
                implementation(ReactiveX.rxandroid)
                implementation(ReactiveX.rxKotlin)

                implementation(AndroidArchLifeCycle.livedata)

                implementation("com.squareup.okhttp3:okhttp:4.9.1")
                implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

                //implementation("com.russhwolf:multiplatform-settings:0.7.4")
            }
        }

        val iosX64Main by getting {
            dependencies {
                //implementation(KTOR.clientOKHttp)
                implementation(KTOR.clientiOS)
                implementation(KTOR.clientDarwin)
            }
        }
        val iosArm64Main by getting {
            dependencies {
                //implementation(KTOR.clientOKHttp)
                implementation(KTOR.clientiOS)
                implementation(KTOR.clientDarwin)
            }
        }
        val iosSimulatorArm64Main by getting {
            dependencies {
                //implementation(KTOR.clientOKHttp)
                implementation(KTOR.clientiOS)
                implementation(KTOR.clientDarwin)
            }
        }
        /*val iosMain by creating {
            dependencies {
                implementation(KTOR.clientOKHttp)
                implementation(KTOR.clientiOS)
                implementation(KTOR.clientDarwin)
            }
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }*/
    }
}

android {
    namespace = "co.nexlabs.betterhr.joblanding"
    compileSdk = BuildConfig.compileSdk
    defaultConfig {
        minSdk = BuildConfig.minSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.lifecycle:lifecycle-livedata-core-ktx:2.7.0")
}

apollo {
    service("with_auth") {
        sourceFolder.set("co/nexlabs/betterhr/job/with_auth")
        packageName.set("co.nexlabs.betterhr.job.with_auth")
    }
    service("without_auth") {
        sourceFolder.set("co/nexlabs/betterhr/job/without_auth")
        packageName.set("co.nexlabs.betterhr.job.without_auth")
    }
}

kotlin.sourceSets.all {
    this.languageSettings.enableLanguageFeature("DataObjects")
}