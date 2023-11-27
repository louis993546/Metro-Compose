plugins {
    id("metro-app-library-convention")
    id("com.google.protobuf") version "0.9.4"
}

android {
    namespace = "com.louis993546.metro.demo.metroSettings"

    defaultConfig {
        consumerProguardFiles("proguard-rules.pro")
    }

    // K2 lint crash
    lint {
        disable.add("MutableCollectionMutableState")
        disable.add("AutoboxingStateCreation")
    }
}

dependencies {
    api("androidx.datastore:datastore:${rootProject.extra.get("datastore_version")}")
    implementation("com.google.protobuf:protobuf-javalite:${rootProject.extra.get("protofbuf_version")}")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${rootProject.extra.get("protofbuf_version")}"
    }

    // Generates the java Protobuf-lite code for the Protobufs in this project. See
    // https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
    // for more information.
    generateProtoTasks {
        all().forEach { task ->
            task.builtins {
                this.register("java") {
                    option("lite")
                }
            }
        }
    }
}
