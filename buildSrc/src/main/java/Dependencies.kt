object BuildConfig {
    const val compileSdk = 34
    const val minSdk = 21
    const val targetSdk = 33

    private const val versionMajor = 1
    private const val versionMinor = 1
    private const val versionPatch = 0
    private const val versionBuild = 1

    const val versionName =
            "$versionMajor.$versionMinor.$versionPatch($versionBuild)"
    const val versionCode = 19 * 10000000 + versionMajor * 1000000 + versionMinor * 10000 + versionPatch * 100 + versionBuild

    const val debugTag = "(All-in-one)"
}

object NavigationCompose {
    const val version = "2.5.3"

    const val navigationCompose = "androidx.navigation:navigation-compose:${version}"
}

object EdgeToEdge {
    const val activity_version = "1.8.2"

    const val edgeToEdgeJava = "androidx.activity:activity:$activity_version"
    const val edgeToEdgeKotlin = "androidx.activity:activity-ktx:$activity_version"
}

object MaterialCompose {
    const val version = "1.2.0"
    const val materialAdaptiveVersion = "1.0.0-alpha06"
    const val materialAdaptiveSuiteVersion = "1.0.0-alpha04"

    const val material = "androidx.compose.material3:material3:${version}"
    const val materialSize = "androidx.compose.material3:material3-window-size-class:${version}"
    const val materialAdaptive = "androidx.compose.material3:material3-adaptive:${materialAdaptiveVersion}"
    const val materialAdaptiveSuite = "androidx.compose.material3:material3-adaptive-navigation-suite:${materialAdaptiveSuiteVersion}"
}

object KTOR {
    const val version = "2.3.7"

    const val clientCore = "io.ktor:ktor-client-core:${version}"
    const val clientDarwin = "io.ktor:ktor-client-darwin:${version}"
    const val clientEncoding = "io.ktor:ktor-client-encoding:${version}"
    const val clientLogging = "io.ktor:ktor-client-logging:${version}"
    const val clientOKHttp = "io.ktor:ktor-client-okhttp:${version}"
    const val clientAndroid = "io.ktor:ktor-client-android:${version}"
    const val clientiOS = "io.ktor:ktor-client-ios:${version}"
    const val clientSerilization = "io.ktor:ktor-serialization-kotlinx-json:${version}"
    const val serilization = "io.ktor:ktor-client-serialization:${version}"
    const val clientNegotiation = "io.ktor:ktor-client-content-negotiation:${version}"
    const val ktorSerialization = "io.ktor:ktor-client-serialization:${version}"
    const val kotlinXSerialization = "org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0"
    const val ktorJson = "io.ktor:ktor-client-json:${version}"
    const val ktorAuth = "io.ktor:ktor-server-auth:${version}"

}

object CommonLibs {
    const val android_gradle_plugin = "com.android.tools.build:gradle:4.1.1"

    const val dexter = "com.karumi:dexter:5.0.0"
    const val phrase = "com.squareup.phrase:phrase:1.1.0"
    const val sonar = "com.facebook.sonar:sonar:0.0.1"
    const val junit = "junit:junit:4.13"
    const val truth = "com.google.truth:truth:1.0.1"
    const val javaxInject = "javax.inject:javax.inject:1"
    const val rabbkt = "com.aungkyawpaing.rabbkt:rabbkt:1.0.1"
    const val timber = "com.jakewharton.timber:timber:4.7.1"
    const val libPhoneNumber = "io.michaelrocks:libphonenumber-android:8.13.13"
}

/*object Apollo {
    const val version = "2.4.5"

    const val gradle_plugin = "com.apollographql.apollo:apollo-gradle-plugin:$version"
    const val runtime = "com.apollographql.apollo:apollo-runtime:$version"
    const val android = "com.apollographql.apollo:apollo-android-support:$version"
    const val httpCache = "com.apollographql.apollo:apollo-http-cache:$version"
    const val rx2 = "com.apollographql.apollo:apollo-rx2-support:$version"
    const val coroutines = "com.apollographql.apollo:apollo-coroutines-support:$version"
}*/


//region AndroidX
object AndroidXAnnotations {
    const val annotations = "androidx.annotation:annotation:1.1.0"
}

object AndroidXAppCompat {
    const val app_compat = "androidx.appcompat:appcompat:1.2.0"
}

object AndroidXRecyclerView {
    private const val version = "1.1.0"
    const val recycler_view = "androidx.recyclerview:recyclerview:$version"
    const val selection = "androidx.recyclerview:recyclerview-selection:$version"
}

object AndroidXCardView {
    const val card_view = "androidx.cardview:cardview:1.0.0"
}

object AndroidXConstraintLayout {
    private const val version = "2.0.4"
    const val constraint_layout = "androidx.constraintlayout:constraintlayout:$version"
}

object AndroidXViewPager {
    const val view_pager = "androidx.viewpager:viewpager:1.0.0"
}

object Epoxy {
    private const val version = "4.1.0"
    const val core = "com.airbnb.android:epoxy:${version}"
    const val processor = "com.airbnb.android:epoxy-processor:${version}"
}

object GooglePlayServices {
    const val maps = "com.google.android.gms:play-services-maps:18.0.0"
    const val locations = "com.google.android.gms:play-services-location:18.0.0"
}

object AndroidXLegacy {
    private const val version = "1.0.0"
    const val support_v4 = "androidx.legacy:legacy-support-v4:$version"
}

object Room {
    const val version = "2.2.5"

    const val runtime = "androidx.room:room-runtime:$version"
    const val runtimeKtx = "androidx.room:room-ktx:$version"
    const val rx2 = "androidx.room:room-rxjava2:$version"
    const val compiler = "androidx.room:room-compiler:$version"
    const val testing = "androidx.room:room-testing:$version"
}

object AndroidXSqlite {
    private const val version = "2.0.0-rc01"
    const val sqlite = "androidx.sqlite:sqlite:$version"
    const val sqlite_framework = "androidx.sqlite:sqlite-framework:$version"
    const val sqlite_ktx = "androidx.sqlite:sqlite-ktx:$version"
}

object AndroidArchLifeCycle {
    private const val version = "2.2.0"
    const val extension = "androidx.lifecycle:lifecycle-extensions:$version"
    const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
    const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
    const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:$version"
    const val process = "androidx.lifecycle:lifecycle-process:$version"
    const val lifecycle_compiler = "androidx.lifecycle:lifecycle-compiler:$version"
}

object CoilJetPack {
    private const val version = "0.20.0"
    const val coil = "com.google.accompanist:accompanist-coil:$version"
}

object GlideJetPack {
    private const val glideVersion = "4.12.0"
    private const val accompanistGlideVesion = "0.10.0"
    const val accompanistGlide = "com.google.accompanist:accompanist-glide:$accompanistGlideVesion"
    const val glide = "com.github.bumptech.glide:glide:$glideVersion"
    const val glideCompiler = "com.github.bumptech.glide:compiler:$glideVersion"
}

object AndroidArchCore {
    private const val version = "2.1.0"
    const val runtime = "androidx.arch.core:core-runtime:$version"
    const val common = "androidx.arch.core:core-common:$version"

    const val test = "android.arch.core:core-testing:$version"

}

object Image {
    const val compressor = "id.zelory:compressor:2.1.0"
}

object AndroidArchWork {
    private const val version = "2.7.0"

    const val work_runtime = "androidx.work:work-runtime:$version"
    const val work_runtime_ktx = "androidx.work:work-runtime-ktx:$version"

    const val work_rxjava2 = "androidx.work:work-rxjava2:$version"
}

object AndroidArchNavigation {
    private const val version = "2.3.1"
    const val fragment_ktx = "androidx.navigation:navigation-fragment-ktx:$version"
    const val ui_ktx = "androidx.navigation:navigation-ui-ktx:$version"

    const val common = "android.arch.navigation:navigation-common:$version"
    const val common_ktx = "android.arch.navigation:navigation-common-ktx:$version"
    const val fragment = "android.arch.navigation:navigation-fragment:$version"
    const val runtime = "android.arch.navigation:navigation-runtime:$version"
    const val runtime_ktx = "android.arch.navigation:navigation-runtime-ktx:$version"
    const val safe_args_generator = "android.arch.navigation:navigation-safe-args-generator:$version"
    const val safe_args_plugin = "android.arch.navigation:navigation-safe-args-gradle-plugin:$version"
    const val testing = "android.arch.navigation:navigation-testing:$version"
    const val testing_ktx = "android.arch.navigation:navigation-testing-ktx:$version"
    const val ui = "android.arch.navigation:navigation-ui:$version"
}

object AndroidXCore {
    private const val version = "1.3.2"
    const val core = "androidx.core:core:$version"
    const val core_ktx = "androidx.core:core-ktx:$version"
}

object AndroidXFragment {
    private const val version = "1.1.0-alpha03"

    const val fragment = "androidx.fragment:fragment:$version"
    const val fragment_ktx = "androidx.fragment:fragment-ktx:$version"
    const val fragment_testing = "androidx.fragment:fragment-testing:$version"
}

object AndroidXTest {
    private const val version = "1.2.0"
    const val core = "androidx.test:core:$version"
    const val core_ktx = "androidx.test:core-ktx:$version"
    const val runner = "androidx.test:runner:$version"
    const val rules = "androidx.test:rules:$version"
    const val roboelectric = "org.robolectric:robolectric:4.3.1"
}

object AndroidXTestExt {
    private const val version = "1.1.1"

    const val junit = "androidx.test.ext:junit:$version"
    const val junit_ktx = "androidx.test.ext:junit-ktx:$version"
    const val truth = "androidx.test.ext:truth:1.2.0"
}

object AndroidXEspresso {
    private const val version = "3.2.0"
    const val core = "androidx.test.espresso:espresso-core:$version"
    const val contrib = "androidx.test.espresso:espresso-contrib:$version"
    const val intents = "androidx.test.espresso:espresso-intents:$version"
    const val idling_resource = "androidx.test.espresso:espresso-idling-resource:$version"
    const val idling_concurrent = "androidx.test.espresso.idling:idling-concurrent:$version"
    const val rx_idler = "com.squareup.rx.idler:rx2-idler:0.11.0"
}

//endregion

object Dagger {
    private const val version = "2.29.1"

    const val core = "com.google.dagger:dagger:$version"
    const val compiler = "com.google.dagger:dagger-compiler:$version"
    const val android_core = "com.google.dagger:dagger-android:$version"
    const val android_support = "com.google.dagger:dagger-android-support:$version"
    const val android_processor = "com.google.dagger:dagger-android-processor:$version"
}

object Firebase {
    const val auth = "com.google.firebase:firebase-auth:20.0.1"
    const val auth_ui = "com.firebaseui:firebase-ui-auth:7.1.0"
    const val analytics = "com.google.firebase:firebase-analytics:19.0.2"
    const val messaging = "com.google.firebase:firebase-messaging:22.0.0"
}

object FragmentTestRule {
    private const val version = "1.1.0"

    const val android = "com.21buttons:fragment-test-rule:$version"
    const val extra = "com.21buttons:fragment-test-rule-extras:$version"
}

object Glide {
    private const val version = "4.11.0"
    const val runtime = "com.github.bumptech.glide:glide:$version"
    const val compiler = "com.github.bumptech.glide:compiler:$version"
    const val transformations = "jp.wasabeef:glide-transformations:4.1.0"

    const val svgImageLoadVersion = "v2.0.0"
    const val svgImageLoadRuntime = "com.github.2coffees1team:GlideToVectorYou:$svgImageLoadVersion"
}

object SmartMaterialSpinner {
    const val version = "1.5.0"
    const val runtime = "com.github.chivorns:smartmaterialspinner:$version"
}

object GoogleService {
    const val ads = "com.google.android.gms:play-services-ads:17.1.2"
    const val consent = "com.google.android.ads.consent:consent-library:1.0.6"
    const val gms = "com.google.gms:google-services:4.3.14"
}

object Kotlin {
    private const val version = "1.4.20"

    const val stdblib_jdk = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$version"
    const val gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
}

object Mockito {
    private const val version = "3.2.0"

    const val core = "org.mockito:mockito-core:$version"
    const val android = "org.mockito:mockito-android:$version"
    const val inline = "org.mockito:mockito-inline:$version"
    const val kotlin = "com.nhaarman:mockito-kotlin:1.5.0"
}

object LeakCanary {
    const val debugLeakcanary = "com.squareup.leakcanary:leakcanary-android:2.7"
}

/*object Sentry {
    const val android = "io.sentry:sentry-android:5.2.4"
}*/

object Moshi {
    private const val version = "1.6.0"

    const val core = "com.squareup.moshi:moshi:$version"
    const val code_gen = "com.squareup.moshi:moshi-kotlin-codegen:$version"
}

object OkHttp {
    private const val version = "4.9.0"

    const val client = "com.squareup.okhttp3:okhttp:$version"
    const val logger = "com.squareup.okhttp3:logging-interceptor:$version"
}

object ReactiveX {
    const val rxjava = "io.reactivex.rxjava2:rxjava:2.2.9"
    const val rxandroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    const val rxKotlin = "io.reactivex.rxjava2:rxkotlin:2.3.0"
    const val rxComprehension = "com.github.pakoito.RxComprehensions:rxcomprehensions2:1.3.0"
}

object DeeplinkDispatch {
    private const val version = "5.2.0"

    const val runtime = "com.airbnb:deeplinkdispatch:$version"
    const val compiler = "com.airbnb:deeplinkdispatch-processor:$version"
}

object RxBinding {
    private const val version = "3.0.0-alpha2"

    const val platform = "com.jakewharton.rxbinding3:rxbinding:$version"
    const val appcompat = "com.jakewharton.rxbinding3:rxbinding-appcompat:$version"
}

object Retrofit {
    private const val version = "2.9.0"

    const val core = "com.squareup.retrofit2:retrofit:$version"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:$version"
    const val retrofitRxAdapter = "com.squareup.retrofit2:adapter-rxjava2:$version"
    const val moshi_converter = "com.squareup.retrofit2:converter-moshi:$version"
}

object SqlDelight {
    private const val version = "1.0.2"

    const val gradle_plugin = "com.squareup.sqldelight:gradle-plugin:$version"
    const val android_driver = "com.squareup.sqldelight:android-driver:$version"
    const val runtime = "com.squareup.sqldelight:runtime-common::$version"
}

object Stetho {
    private const val version = "1.5.0"

    const val core = "com.facebook.stetho:stetho:$version"
    const val okhttp3 = "com.facebook.stetho:stetho-okhttp3:$version"
}

object Shimmer {
    const val reycler_view = "com.github.sharish:ShimmerRecyclerView:v1.3"
}

object ThreeTenBp {
    private const val version = "1.4.5"

    const val core = "org.threeten:threetenbp:$version"
    const val no_tz_db = "org.threeten:threetenbp:$version:no-tzdb"
    const val android = "com.jakewharton.threetenabp:threetenabp:1.2.4"
}

object Coroutines {
    private const val version = "1.4.2"
    const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$version"
    const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$version"
    const val rx2 = "org.jetbrains.kotlinx:kotlinx-coroutines-rx2:$version"
    const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$version"
}

object GetStream {
    private const val version = "4.4.6"
    const val chat_client = "io.getstream:stream-chat-android-client:$version"
}

object SpecialPermission {
    private const val version = "4.8.0"
    const val permissionsdispatcher_ktx = "com.github.permissions-dispatcher:permissionsdispatcher-ktx:$version"
}

object TrueTime {
    private const val version = "3.5"
    const val trueTime = "com.github.instacart.truetime-android:library-extension-rx:$version"
}

object CardStackView {
    private const val version = "2.3.4"
    const val cardStackView = "com.yuyakaido.android:card-stack-view:$version"
}

object CountryCodePicker {
    private const val version = "2.5.4"
    const val cpp = "com.hbb20:ccp:$version"
}

object RootBeer {
    private const val version = "0.1.0"
    const val rootBeer = "com.scottyab:rootbeer-lib:$version"
}
