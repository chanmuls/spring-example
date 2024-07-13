dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.retry:spring-retry")
    implementation("org.springframework:spring-aspects")

    implementation(project(":client:redis-client"))
    implementation(project(":domain"))
    implementation(project(":core"))

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.2.0")

    testImplementation(testFixtures(project(":client:redis-client")))
    testImplementation(testFixtures(project(":domain")))
    testImplementation(testFixtures(project(":core")))
}

tasks.getByName("jar") {
    enabled = true
}

tasks.getByName("bootJar") {
    enabled = false
}