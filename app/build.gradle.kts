plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "de.lichtblickraum.app"
    compileSdk = 36

    defaultConfig {
        // Eindeutige Kennung im Play Store – kann nur VOR der ersten Veröffentlichung geändert werden.
        applicationId = "de.lichtblickraum.app"
        minSdk = 26
        targetSdk = 36
        // Jede Version für den Play Store braucht eine höhere Nummer – GitHub zählt sie automatisch hoch.
        versionCode = (System.getenv("LICHTBLICK_VERSION_CODE") ?: "1").toInt()
        versionName = "1.0"
    }

    signingConfigs {
        // Fester Test-Schlüssel (nur für Debug-Versionen, nicht geheim), damit sich jede
        // neue Testversion über die alte installieren lässt. Die Play-Store-Version bekommt
        // später einen eigenen, geschützten Schlüssel.
        getByName("debug") {
            storeFile = file("debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
        // Geheimer Hochlade-Schlüssel für den Play Store. Er liegt NICHT im Projekt, sondern nur
        // als verschlüsseltes „Secret“ bei GitHub und wird beim Bauen von dort übergeben.
        val uploadStore = System.getenv("LICHTBLICK_UPLOAD_KEYSTORE")
        if (uploadStore != null) {
            create("upload") {
                storeFile = file(uploadStore)
                storePassword = System.getenv("LICHTBLICK_UPLOAD_PASSWORD")
                keyAlias = "lichtblick-upload"
                keyPassword = System.getenv("LICHTBLICK_UPLOAD_PASSWORD")
            }
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debug")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            signingConfigs.findByName("upload")?.let { signingConfig = it }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":core"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.work.runtime.ktx)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material.icons.core)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    testImplementation(libs.junit)
    testImplementation(libs.androidx.test.junit)
    testImplementation(libs.robolectric)
    testImplementation(platform(libs.androidx.compose.bom))
    testImplementation(libs.androidx.compose.ui.test.junit4)
}
