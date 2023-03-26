plugins {
    kotlin("multiplatform") version "1.8.0"
    id("app.cash.sqldelight") version "2.0.0-alpha05"
}

group = "com.bethwelamkenya"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    google()
    mavenCentral()
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://dl.bintray.com/kotlin/kotlin-dev")
}

sqldelight {
    databases {
        create("Church") {
            packageName.set("com.bethwelamkenya")
//            sourceFolders.set(listOf("libuiMain"))
//            sourceFolders.set(listOf("sqldelight"))
//            sourceFolders.set("libuiMain")
        }
    }
}

val os = org.gradle.internal.os.OperatingSystem.current()!!

kotlin {
    when {
        os.isWindows -> mingwX64("libui")
        os.isMacOsX -> macosX64("libui")
        os.isLinux -> linuxX64("libui")
        else -> throw Error("Unknown host")
    }.binaries.executable {
        entryPoint = "main"
        if (os.isWindows) {
            windowsResources("hello.rc")
            linkerOpts("-mwindows")
            linkerOpts("-lsqlite3")
//            linkerOpts("-linker-option", "/LIBPATH:C:\\sqlite3")
            linkerOpts("-L", "C:\\sqlite3")
        }
    }

    sourceSets {
        commonMain {
            kotlin.srcDir("src/commonMain/kotlin")
            resources.srcDir("src/commonMain/resources")
        }
        val libuiMain by getting {
            dependencies {
                implementation("com.github.msink:libui:0.1.8")
                implementation("app.cash.sqldelight:native-driver:2.0.0-alpha05")
                implementation("co.touchlab:sqliter:0.7.0")
//                implementation("com.asyncant.crypto:sha256-kt-native:1.0")
//                implementation("com.asyncant.crypto:sha256-kt:1.0")
//            implementation("com.squareup.sqldelight:sqlite-driver:1.5.0")
//            implementation("co.touchlab:sqliter:1.2.1")
//            implementation("com.github.msink:libui:0.1.8")
//                implementation("com.squareup.sqldelight:sqlite-driver:1.5.4")
//                implementation("com.squareup.sqldelight:coroutines-extensions-jvm:1.5.4")
            }
        }
    }
}

fun org.jetbrains.kotlin.gradle.plugin.mpp.Executable.windowsResources(rcFileName: String) {
    val taskName = linkTaskName.replaceFirst("link", "windres")
    val inFile = compilation.defaultSourceSet.resources.sourceDirectories.singleFile.resolve(rcFileName)
    val outFile = buildDir.resolve("processedResources/$taskName.res")

    val windresTask = tasks.create<Exec>(taskName) {
        val konanUserDir = System.getenv("KONAN_DATA_DIR") ?: "${System.getProperty("user.home")}/.konan"
        val konanLlvmDir = "$konanUserDir/dependencies/msys2-mingw-w64-x86_64-2/bin"

        inputs.file(inFile)
        outputs.file(outFile)
        commandLine("$konanLlvmDir/windres", inFile, "-D_${buildType.name}", "-O", "coff", "-o", outFile)
        environment("PATH", "$konanLlvmDir;${System.getenv("PATH")}")

        dependsOn(compilation.compileTaskProvider)
//        dependsOn(compilation.compileKotlinTask)
    }

    linkTask.dependsOn(windresTask)
    linkerOpts(outFile.toString())
}
