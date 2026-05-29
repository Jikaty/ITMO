plugins {
    java
    application
}

group = "ru.itmo"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:1.18.34")
    annotationProcessor("org.projectlombok:lombok:1.18.34")
    implementation("com.thoughtworks.xstream:xstream:1.4.20")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.postgresql:postgresql:42.7.3")
    
    // JavaFX dependencies
    val javafxVersion = "21.0.2"
    implementation("org.openjfx:javafx-controls:$javafxVersion")
    implementation("org.openjfx:javafx-fxml:$javafxVersion")
    implementation("org.openjfx:javafx-graphics:$javafxVersion")
    
    testImplementation("junit:junit:4.13.2")
}

tasks.register<Jar>("serverJar") {
    archiveBaseName.set("lab7-server")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest { attributes["Main-Class"] = "itmo.lab7.server.Server" }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get())
}

tasks.register<Jar>("clientConsoleJar") {
    archiveBaseName.set("lab7-client-console")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest { attributes["Main-Class"] = "itmo.lab7.client.ClientConsole" }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get())
}

tasks.register<Jar>("clientGuiJar") {
    archiveBaseName.set("lab8-client-gui")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest { attributes["Main-Class"] = "itmo.lab7.client.gui.MainApp" }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks.jar.get())
}

tasks.withType<ProcessResources> {
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}

application {
    mainClass.set("itmo.lab7.client.gui.MainApp")
}
