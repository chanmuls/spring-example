import org.springframework.boot.gradle.tasks.bundling.BootJar

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-batch")
    implementation("org.springframework.batch:spring-batch-integration")

    implementation(project(":facade"))
    implementation(project(":core"))

    testImplementation("org.springframework.batch:spring-batch-test")
}

tasks.named<BootJar>("bootJar") {
    mainClass.set("com.chanmul.ExampleBatchApplication")
    archiveFileName.set("batch.${archiveExtension.get()}")

    manifest {
        attributes("Start-Class" to "com.chanmul.ExampleBatchApplication")
    }
}

tasks.getByName("jar") {
    enabled = false
}