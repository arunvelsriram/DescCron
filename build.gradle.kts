plugins {
    id("org.jetbrains.intellij.platform") version "2.6.0"
    kotlin("jvm") version "2.1.21"
}

group = "dev.arunvelsriram.desccron"
version = project.property("PLUGIN_VERSION").toString()

kotlin {
    jvmToolchain(17)
}

repositories {
    mavenCentral()
    
    intellijPlatform {
        defaultRepositories()
    }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))
    implementation("com.cronutils:cron-utils:9.2.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.13.0")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher:1.13.0")
    testImplementation("io.mockk:mockk:1.14.2")
    
    intellijPlatform {
        intellijIdeaCommunity("2025.1")
    }
}

intellijPlatform {
    pluginConfiguration {
        name.set("DescCron")
    }
    buildSearchableOptions.set(true)
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.runIde {
    argumentProviders.add(CommandLineArgumentProvider {
        listOf("/Users/arunvelsrirams/github/arunvelsriram/DescCron/example")
    })
}