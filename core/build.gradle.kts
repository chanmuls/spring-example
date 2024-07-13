dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.data:spring-data-commons")
    implementation("org.springframework:spring-tx")
    implementation("org.springframework:spring-jdbc")

    implementation("jakarta.validation:jakarta.validation-api:3.0.2")
    implementation("commons-codec:commons-codec:1.13")

    implementation("ch.qos.logback.contrib:logback-jackson:0.1.5")
    implementation("ch.qos.logback.contrib:logback-json-classic:0.1.5")

    testFixturesImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    testFixturesImplementation("org.testcontainers:testcontainers:1.18.3")
    testFixturesImplementation("org.testcontainers:junit-jupiter:1.18.3")
}

tasks.getByName("jar") {
    enabled = true
}

tasks.getByName("bootJar") {
    enabled = false
}