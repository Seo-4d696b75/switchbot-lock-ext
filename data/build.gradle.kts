import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

// TODO remove reading from .properties
val credentials = project.file("credential.properties").let { file ->
    Properties().apply {
        load(FileInputStream(file))
    }
}

android {
    namespace = "com.seo4d696b75.android.switchbot_lock_ext.data"
    compileSdk = 33

    defaultConfig {
        minSdk = 27

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            "String",
            "SWITCH_BOT_TOKEN",
            "\"${credentials.getProperty("token")}\"",
        )
        buildConfigField(
            "String",
            "SWITCH_BOT_SECRET",
            "\"${credentials.getProperty("secret")}\"",
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(project(":domain"))
    implementation(project(":api"))

    implementation(libs.androidx.security)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    testImplementation(libs.junit)
}
