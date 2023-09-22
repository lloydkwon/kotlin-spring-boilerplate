import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.0.0"
	id("io.spring.dependency-management") version "1.1.0"
	id("org.jetbrains.kotlin.kapt") version "1.7.21"
	id("org.asciidoctor.jvm.convert") version "3.3.2"
	kotlin("jvm") version "1.7.21"
	kotlin("plugin.spring") version "1.7.21"
	kotlin("plugin.jpa") version "1.7.21"
	kotlin("plugin.allopen") version "1.7.21"
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

group = "boilerplate"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

val asciidoctorExt: Configuration by configurations.creating
val snippetsDir by extra { file("build/generated-snippets") }

dependencies {
//	implementation("org.springframework.boot:spring-boot-starter-actuator")
	asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
	implementation("io.github.microutils:kotlin-logging-jvm:3.0.2")
	implementation("io.jsonwebtoken:jjwt-api:0.11.2")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.slf4j:slf4j-api:2.0.5")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//	implementation("org.springframework.boot:spring-boot-starter-data-redis")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-web")
	kapt("com.querydsl:querydsl-apt:5.0.0:jakarta")
	kapt("jakarta.annotation:jakarta.annotation-api")
	kapt("jakarta.persistence:jakarta.persistence-api")
	kapt("org.springframework.boot:spring-boot-configuration-processor")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.11.2")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.11.2")
	runtimeOnly("org.postgresql:postgresql")
	testImplementation("com.ninja-squad:springmockk:4.0.0")
	testImplementation("io.kotest.extensions:kotest-extensions-spring:1.1.2")
	testImplementation("io.kotest:kotest-assertions-core:5.3.2")
	testImplementation("io.kotest:kotest-runner-junit5:5.3.2")
	testImplementation("io.mockk:mockk:1.13.3")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
}

tasks {
	withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "17"
		}
	}
	withType<Test> {
		useJUnitPlatform()
		jvmArgs("--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED")
		outputs.dir(snippetsDir)
	}
	asciidoctor {
		inputs.dir(snippetsDir)
		configurations("asciidoctorExt")
		dependsOn(test)
		baseDirFollowsSourceFile()
	}
	test {
		useJUnitPlatform()
		outputs.dir(snippetsDir)
	}
	val copyDocs = register<Copy>("copyDocs") {
		dependsOn(asciidoctor)
		from("${asciidoctor.get().outputDir}/index.html")
		into("src/main/resources/static/docs")
	}
	bootJar {
		dependsOn(copyDocs)
	}
}
