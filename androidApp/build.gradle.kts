plugins {
    id("com.android.application")
    kotlin("android")
    id("org.jetbrains.compose")
    kotlin("plugin.serialization")
}

android {
    namespace = "co.nexlabs.betterhr.joblanding.android"
    compileSdk = BuildConfig.compileSdk
    defaultConfig {
        applicationId = "co.nexlabs.betterhr.joblanding.android"
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.ui:ui-tooling:1.4.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.activity:activity-compose:1.7.1")

    val composeBom = platform("androidx.compose:compose-bom:2024.02.02")
    implementation(composeBom)
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.3")

    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    implementation(NavigationCompose.navigationCompose)
    implementation(MaterialCompose.material)
    implementation(EdgeToEdge.edgeToEdgeJava)
    implementation(EdgeToEdge.edgeToEdgeKotlin)

    implementation(KTOR.clientCore)
    implementation(KTOR.clientLogging)
    implementation(KTOR.clientSerilization)
    implementation(KTOR.serilization)
    implementation(KTOR.clientNegotiation)
    implementation(KTOR.clientEncoding)
    implementation(KTOR.ktorSerialization)
    implementation(KTOR.kotlinXSerialization)
    implementation(KTOR.clientOKHttp)
    implementation(KTOR.ktorJson)

    implementation(CommonLibs.libPhoneNumber)

    implementation(ReactiveX.rxjava)
    implementation(ReactiveX.rxandroid)
    implementation(ReactiveX.rxKotlin)

    implementation(AndroidArchLifeCycle.livedata)
    implementation(GlideJetPack.accompanistGlide)
    implementation("io.coil-kt:coil-compose:2.0.0")
    implementation("io.coil-kt:coil-gif:2.0.0")

    implementation("com.github.nanihadesuka:LazyColumnScrollbar:1.10.0")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("com.google.accompanist:accompanist-swiperefresh:0.24.13-rc")

    implementation("com.google.accompanist:accompanist-systemuicontroller:0.20.0")


}
