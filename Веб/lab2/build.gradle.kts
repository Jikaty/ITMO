plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(files("libs/fastcgi-lib.jar"))
}

tasks.withType<JavaCompile> {
    options.release.set(17)
}

tasks.jar {
    archiveFileName.set("server.jar")
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "Main"
    }
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
}