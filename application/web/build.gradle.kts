import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.session:spring-session-core:3.3.1")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    implementation(project(":domain"))
    implementation(project(":core"))

    testImplementation("org.springframework.security:spring-security-test")
}

tasks.named<BootJar>("bootJar") {
    mainClass.set("com.chanmul.ExampleWebApplication")
    archiveFileName.set("web.${archiveExtension.get()}")

    val profile = System.getProperty("spring.profiles.active")

    if (profile != null) {
        // clean :application:web:build -Dspring.profiles.active=dev
        launchScript {
            properties(mapOf("inlinedConfScript" to "application/${project.name}/script/${archiveBaseName.get()}-${profile}.conf"))
        }
    }

    manifest {
        attributes("Start-Class" to "com.chanmul.ExampleWebApplication")
    }
}

tasks.getByName("jar") {
    enabled = false
}
