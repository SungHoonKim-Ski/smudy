import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.2.5"
	id("io.spring.dependency-management") version "1.1.4"
	kotlin("jvm") version "1.9.23"
	kotlin("plugin.spring") version "1.9.23"
	kotlin("plugin.jpa") version "1.9.23"
}

group = "com.ssafy"
version = "0.0.1-SNAPSHOT"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
	mavenCentral()
}

extra["springCloudVersion"] = "2023.0.1"

dependencies {

	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")
	implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// DB
	implementation("org.postgresql:postgresql")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")

	// logging
	implementation("io.github.oshai:kotlin-logging-jvm:5.1.0")

	// text[] type parsing
	implementation("io.hypersistence:hypersistence-utils-hibernate-62:3.6.0")
	implementation("org.hibernate.orm:hibernate-core:6.2.8.Final")
	implementation("com.fasterxml.jackson.module:jackson-module-jakarta-xmlbind-annotations")

	// https://mvnrepository.com/artifact/com.github.ulisesbocchio/jasypt-spring-boot-starter
	implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

	// Security
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("org.springframework.boot:spring-boot-starter-security")

	// Spotify
	implementation("se.michaelthelin.spotify:spotify-web-api-java:8.4.0")

	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

	// https://mvnrepository.com/artifact/junit/junit
	testImplementation("junit:junit:4.13.1")

	// coroutine
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	// feign okhttp
	implementation("io.github.openfeign:feign-okhttp")
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs += "-Xjsr305=strict"
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
