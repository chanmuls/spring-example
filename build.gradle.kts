plugins {
    idea
    java
    `java-library`
    `java-test-fixtures`
    `maven-publish`
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.jpa") version "2.0.0" apply false
    kotlin("plugin.spring") version "2.0.0" apply false
    id("org.springframework.boot") version "3.3.1" apply false
    id("io.spring.dependency-management") version "1.1.5" apply false
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

allprojects {
    group = "com.chanmul"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "java-library")
    apply(plugin = "java-test-fixtures")
    apply(plugin = "maven-publish")

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter")

        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("org.jetbrains.kotlin:kotlin-reflect")

        compileOnly("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        implementation("org.mapstruct:mapstruct:1.4.2.Final")
        annotationProcessor("org.mapstruct:mapstruct-processor:1.4.2.Final")

        implementation("jakarta.json:jakarta.json-api:2.1.2")
        implementation("org.apache.commons:commons-lang3:3.12.0")
        implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
        implementation("com.google.guava:guava:33.2.1-jre")

        testCompileOnly("org.projectlombok:lombok")
        testAnnotationProcessor("org.projectlombok:lombok")
        testImplementation("net.joshka:junit-json-params:5.9.2-r0")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.testcontainers:testcontainers:1.18.3")
        testImplementation("org.testcontainers:junit-jupiter:1.18.3")
        testImplementation("net.joshka:junit-json-params:5.9.3-r0")
        testImplementation("org.eclipse.parsson:parsson:1.1.1")
        testImplementation("com.h2database:h2")

        testFixturesImplementation("org.springframework.boot:spring-boot-starter-test")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")

        implementation("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}