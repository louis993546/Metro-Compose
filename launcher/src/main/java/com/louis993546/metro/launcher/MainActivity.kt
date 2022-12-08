package com.louis993546.metro.launcher

import android.content.pm.LauncherApps
import android.os.Bundle
import android.os.Process
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.louis993546.metro.MetroTheme
import com.louis993546.metro.Text


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val installedApps = getInstalledApps()

        setContent {
            MetroLauncherTheme {
                Column {
                    installedApps.forEach { app ->
                        Text(text = app)
                    }
                }
            }
        }
    }

    private fun getInstalledApps(): List<String> = getSystemService(LauncherApps::class.java)
        .getActivityList(null, Process.myUserHandle())
        .map { it.label.toString() }
        .sorted()
}

@Composable
fun MetroLauncherTheme(
    content: @Composable () -> Unit,
) {
    MetroTheme(
        accentColor = Color.Cyan,
        content = content,
    )
}
