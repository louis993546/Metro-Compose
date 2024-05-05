plugins {
    id("metro-app-library-convention")
    id("com.google.protobuf") version "0.9.4"
}

android {
    namespace = "com.louis993546.metro.demo.metroSettings"

    defaultConfig {
        consumerProguardFiles("proguard-rules.pro")
    }
}

dependencies {
    api(libs.androidx.datastore)
    implementation(libs.protobuf.javalite)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${libs.versions.protobufJavalite.get()}"
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
