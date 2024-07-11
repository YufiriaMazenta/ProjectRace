import java.text.SimpleDateFormat

plugins {
    id("java-library")
    id("maven-publish")
    id("io.github.goooler.shadow").version("8.1.7")
}

repositories {
    mavenLocal()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.maven.apache.org/maven2/")
    maven("http://repo.crypticlib.com:8081/repository/maven-public/") {
        isAllowInsecureProtocol = true
    }
    //PlaceholderAPI
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://www.jitpack.io")
    mavenCentral()
}

dependencies {
    compileOnly("com.github.602723113:ParticleLib:1.5.1")
    compileOnly("org.jetbrains:annotations:24.0.1")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("io.papermc.paper:paper-api:1.21-R0.1-SNAPSHOT")
    implementation("com.crypticlib:bukkit:1.0.6")
    implementation("com.zaxxer:HikariCP:5.1.0")
}

group = "pers.yufiria"
version = "1.0.0"
var mainClass = "pers.yufiria.projectrace.ProjectRaceBukkit"
var pluginVersion: String = version.toString() + "-" + SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis())
java.sourceCompatibility = JavaVersion.VERSION_21
java.targetCompatibility = JavaVersion.VERSION_21

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks {
    val props = HashMap<String, String>()
    props["version"] = pluginVersion
    props["main"] = mainClass
    props["name"] = rootProject.name
    processResources {
        filesMatching("plugin.yml") {
            expand(props)
        }
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    shadowJar {
        relocate("crypticlib", "pers.yufiria.projectrace.crypticlib")
        relocate("com.zaxxer.hikari", "pers.yufiria.projectrace.hikari")
        dependencies {
            exclude(dependency("org.slf4j:slf4j-api"))
        }
        archiveFileName.set("${rootProject.name}-${version}.jar")
    }
    assemble {
        dependsOn(shadowJar)
    }
}
