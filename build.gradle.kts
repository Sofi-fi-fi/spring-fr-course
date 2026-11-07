plugins {
    java
    id("org.springframework.boot") version "4.1.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.taskplanner"
version = "0.2.0"
description = "Планувальник задач — лабораторні роботи зі Spring Framework"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
