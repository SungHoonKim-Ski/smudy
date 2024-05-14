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
	maven(url="https://repo.spring.io/milestone")
	maven(url="https://repo.spring.io/snapshot")
}

extra["springCloudVersion"] = "2023.0.1"
extra["springAiVersion"] = "0.8.1"

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

	// jasypt
	implementation("com.github.ulisesbocchio:jasypt-spring-boot-starter:3.0.5")

	// Security
	implementation("io.jsonwebtoken:jjwt-api:0.11.5")
	implementation("io.jsonwebtoken:jjwt-impl:0.11.5")
	implementation("io.jsonwebtoken:jjwt-jackson:0.11.5")
	implementation("org.springframework.boot:spring-boot-starter-security")

	// spring ai
//	implementation(platform("org.springframework.ai:spring-ai-bom:0.8.1-SNAPSHOT"))
	implementation("org.springframework.ai:spring-ai-pgvector-store-spring-boot-starter")
	implementation("org.springframework.ai:spring-ai-openai-spring-boot-starter")

}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
		mavenBom("org.springframework.ai:spring-ai-bom:${property("springAiVersion")}")
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
