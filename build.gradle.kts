val kotlinVersion: String by project
// KTOR
val ktorVersion: String by project
// SECURITY
val argon2Version: String by project
val bouncyCastleVersion: String by project
// DATABASE
val h2Version: String by project
val hikariCPVersion: String by project
val exposedVersion: String by project
val postgresVersion: String by project
// DEPENDENCY INJECTION
val koinVersion: String by project
// LOG
val kloggingVersion: String by project
// TEST
val mockkVersion: String by project
val kotestVersion: String by project
val detektVersion: String by project
// SPACE ARTIFACTORY
val spaceUri: String by project
val spaceUsername: String by project
val spacePassword: String by project


plugins {
   `maven-publish`
   kotlin("jvm") version "1.9.22"
   id("io.ktor.plugin") version "2.3.7"
   id("com.github.spotbugs") version "6.0.4"
   id("org.jetbrains.qodana") version "2023.2.1"
   id("io.gitlab.arturbosch.detekt") version "1.23.4"
   id("org.jetbrains.kotlin.plugin.serialization") version "1.9.22"
}


group = "com.trsolutions"
version = "0.0.1"


application {
   mainClass.set("io.ktor.server.netty.EngineMain")

   val isDevelopment: Boolean = project.ext.has("development")
   applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}


repositories {
   mavenCentral()
   maven {
      url = uri(spaceUri)
      credentials {
         username = spaceUsername
         password = spacePassword
      }
   }
}


dependencies {
   // KTOR
   implementation("io.ktor:ktor-server-openapi")
   implementation("io.ktor:ktor-server-core-jvm")
   implementation("io.ktor:ktor-server-cors-jvm")
   implementation("io.ktor:ktor-server-auth-jvm")
   implementation("io.ktor:ktor-server-netty-jvm")
   implementation("io.ktor:ktor-server-swagger-jvm")
   implementation("io.ktor:ktor-server-auth-jwt-jvm")
   implementation("io.ktor:ktor-server-host-common-jvm")
   implementation("io.ktor:ktor-server-status-pages-jvm")
   implementation("io.ktor:ktor-server-content-negotiation-jvm")
   implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
   // SECURITY
   implementation("de.mkammerer:argon2-jvm:$argon2Version")
   implementation("org.bouncycastle:bcpkix-jdk18on:$bouncyCastleVersion")
   // DATABASE
   implementation("com.h2database:h2:$h2Version")
   implementation("com.zaxxer:HikariCP:$hikariCPVersion")
   implementation("org.postgresql:postgresql:$postgresVersion")
   implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
   implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
   implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
   // DEPENDENCY INJECTION
   implementation("io.insert-koin:koin-core:$koinVersion")
   implementation("io.insert-koin:koin-ktor:$koinVersion")
   implementation("io.insert-koin:koin-logger-slf4j:$koinVersion")
   // LOG
   implementation("io.klogging:klogging-jvm:$kloggingVersion")
   // TEST
   testImplementation("io.mockk:mockk:$mockkVersion")
   testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotestVersion")
   testImplementation("io.gitlab.arturbosch.detekt:detekt-api:$detektVersion")
   testImplementation("io.ktor:ktor-server-tests-jvm")
   testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

tasks {
   test { useJUnitPlatform() }
   compileKotlin { kotlinOptions.jvmTarget = "21" }
   compileTestKotlin { kotlinOptions.jvmTarget = "21" }

   detekt.configure {
      debug = true
      ignoreFailures = false
      buildUponDefaultConfig = false
      source = fileTree("src/main/kotlin")
      config = files("src/main/resources/verification/detekt.yml")
      reports {
         html {
            html.required.set(true)
            html.outputLocation.set(file("build/reports/detekt.html"))
         }
         custom {
            reportId = "DetektReport"
            outputLocation.set(file("build/reports/detekt.json"))
         }
      }
   }

   spotbugs {
      toolVersion.set("4.8.3")
      showProgress.set(true)
      ignoreFailures.set(true)
      showStackTraces.set(true)
      effort.set(com.github.spotbugs.snom.Effort.DEFAULT)
      reportLevel.set(com.github.spotbugs.snom.Confidence.DEFAULT)
      omitVisitors.set(listOf("FindNonShortCircuit"))
      extraArgs.add("-nested:false")
      maxHeapSize.set("1g")
      extraArgs.set(listOf("-nested:false"))
      jvmArgs.set(listOf("-Duser.language=en"))
      spotbugsMain {
         reports.create("xml")
      }
   }

}

publishing {
   publications {
      create<MavenPublication>("erp") {
         from(components["kotlin"])
      }
   }
   repositories {
      maven {
         url = uri(spaceUri)
         credentials {
            username = spaceUsername
            password = spacePassword
         }
      }
   }
}

gradleEnterprise {
   buildScan {
      termsOfServiceUrl = "https://gradle.com/terms-of-service"
      termsOfServiceAgree = "yes"
   }
}