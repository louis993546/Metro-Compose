package com.louis993546.metro.metroSettings

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.io.InputStream
import java.io.OutputStream

/**
 * TODO check if things needs to be added to proguard
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

private val Context.settingsDataStore2: DataStore<MetroSettings> by dataStore(
    fileName = "metro_settings.pb",
    serializer = MetroSettingsSerializer,
)

val Context.metroSettingsDataSource: MetroSettingsDataSource
    get() = MetroSettingsDataSourceImpl(this)

data class MetroSettingsConfiguration(
    val isTallScreenRatio: Float,
    val frameRatio: Float?,
) {
    companion object {
        val INITIAL: MetroSettingsConfiguration = MetroSettingsConfiguration(
            isTallScreenRatio = 1.8f,
            frameRatio = 0.6f,
        )
    }
}

fun MetroSettings.toMetroSettingsConfiguration() = MetroSettingsConfiguration(
    isTallScreenRatio = this.isTallScreenRatio,
    frameRatio = this.frameRatio,
)

interface MetroSettingsDataSource {
    fun getConfiguration(): Flow<MetroSettingsConfiguration>

    suspend fun setTallScreenRatio(ratio: Float)

    suspend fun setFrameRatio(ratio: Float)

    suspend fun clearFrameRatio()
}

internal class MetroSettingsDataSourceImpl(context: Context) : MetroSettingsDataSource {
    private val dataStore: DataStore<MetroSettings> = context.settingsDataStore2
    override fun getConfiguration(): Flow<MetroSettingsConfiguration> =
        dataStore.data.map { it.toMetroSettingsConfiguration() }

    override suspend fun setTallScreenRatio(ratio: Float) {
        dataStore.updateData {
            it.toBuilder().setIsTallScreenRatio(ratio).build()
        }
    }

    override suspend fun setFrameRatio(ratio: Float) {
        dataStore.updateData {
            it.toBuilder().setFrameRatio(ratio).build()
        }
    }

    override suspend fun clearFrameRatio() {
        dataStore.updateData {
            it.toBuilder().clearFrameRatio().build()
        }
    }
}
