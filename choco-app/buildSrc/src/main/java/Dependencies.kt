
object Versions {
    const val kotlinVersion = "1.5.20"
//    const val safeArgsGradlePlugin = "2.3.5"
    const val gradleVersion = "4.2.2"
    const val kotlinStd = "1.5.31"
    const val androidCoreKtx = "1.6.0"
    const val appCompat = "1.3.1"
    const val googleMaterial = "1.4.0"
    const val jUnit = "4.13.2"
    const val androidJUnitExt = "1.1.3"
    const val espresso = "3.4.0"
    const val navComponent = "2.3.5"
    const val dataBindingCompiler = "3.1.4"
    const val retrofit = "2.7.1"
    const val gson = "2.8.6"
    const val gsonConverter = "2.6.4"
    const val loggingInterceptor = "3.12.12"
    const val koin = "3.1.3"
    const val lifeCycle = "2.2.0"
    const val coroutinesCore = "1.5.0"
    const val coroutinesAndroid = "1.4.2"
    const val okhttp = "3.14.7"
    const val swipeRefresh = "1.1.0"
    const val room = "2.3.0"
    const val coroutinesTest = "1.5.1"
    const val androidXcoreTesting = "2.1.0"
    const val mockK= "1.10.6"
    const val testRunner = "1.2.0"
    const val mockitoKotlinVersion = "2.2.0"
    const val robolectricVersion = "4.5.1"
    const val mockKotlin = "4.0.0"
}

object Dependencies {
    const val kotlinStd = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlinStd}"
    const val androidCoreKtx = "androidx.core:core-ktx:${Versions.androidCoreKtx}"
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    const val googleMaterial = "com.google.android.material:material:${Versions.googleMaterial}"
    const val navFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navComponent}"
    const val navUiExt = "androidx.navigation:navigation-ui-ktx:${Versions.navComponent}"
    const val dataBindingCompiler = "com.android.databinding:compiler:${Versions.dataBindingCompiler}"
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val androidJUnitExt = "androidx.test.ext:junit:${Versions.androidJUnitExt}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.gsonConverter}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.loggingInterceptor}"
    const val koinAndroid = "io.insert-koin:koin-android:${Versions.koin}"
    const val lifeCycleViewModel = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifeCycle}"
    const val lifeCycleRuntime = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifeCycle}"
    const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifeCycle}"
    const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutinesTest}"
    const val androidXcoreTesting = "androidx.arch.core:core-testing:${Versions.androidXcoreTesting}"
    const val mockK = "io.mockk:mockk:${Versions.mockK}"
    const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    const val mockitoKotlin = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlinVersion}"
    const val mockKotlin = "org.mockito.kotlin:mockito-kotlin:${Versions.mockKotlin}"
    const val robolectric = "org.robolectric:robolectric:${Versions.robolectricVersion}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val room = "androidx.room:room-ktx:${Versions.room}"
    const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"
}