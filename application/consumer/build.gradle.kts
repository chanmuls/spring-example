import org.springframework.boot.gradle.tasks.bundling.BootJar

val springCloudVersion = "2022.0.4"

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.data:spring-data-commons")

    implementation("org.springframework.kafka:spring-kafka")

    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-stream")
    implementation("org.springframework.cloud:spring-cloud-stream-binder-kinesis:3.0.0")

    implementation(project(":facade"))
    implementation(project(":core"))
}

tasks.named<BootJar>("bootJar") {
    mainClass.set("com.chanmul.ExampleConsumerApplication")
    archiveFileName.set("api.${archiveExtension.get()}")

    val profile = System.getProperty("spring.profiles.active")

    if (profile != null) {
        // clean :application:consumer:build -Dspring.profiles.active=dev
        launchScript {
            properties(mapOf("inlinedConfScript" to "application/${project.name}/script/${archiveBaseName.get()}-${profile}.conf"))
        }
    }

    manifest {
        attributes("Start-Class" to "com.chanmul.ExampleConsumerApplication")
    }
}

tasks.getByName("jar") {
    enabled = false
}
