package com.louis993546.metro.demo

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

/**
 * TODO check if things needs to be added to proguard
 *
 * TODO this needs to be move to somewhere that metroSettings can access
 */
object MetroSettingsSerializer : Serializer<MetroSettings> {
    @Suppress("MagicNumber")
    override val defaultValue: MetroSettings = MetroSettings.newBuilder()
        .setFrameRatio(0.6f)            // Lumia 920 has 768 * 1280 screen
        .setIsTallScreenRatio(1.8f)     // Magic number that has enough room for the buttons
        .build()

    override suspend fun readFrom(input: InputStream): MetroSettings {
        try {
            return MetroSettings.parseFrom(input)
        } catch (e: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", e)
        }
    }

    override suspend fun writeTo(t: MetroSettings, output: OutputStream) {
        t.writeTo(output)
    }
}

val Context.settingsDataStore: DataStore<MetroSettings> by dataStore(
    fileName = "metro_settings.pb",
    serializer = MetroSettingsSerializer,
)
