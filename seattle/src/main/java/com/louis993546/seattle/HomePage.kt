package com.louis993546.seattle

import android.content.pm.PackageManager
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.louis993546.metro.demo.VerticalTilesGrid

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    tempListOfApps: List<App>,
) {
    // TODO need to save which app user wants to display on home page and which config

    val context = LocalContext.current
    val pm = context.packageManager

    VerticalTilesGrid(
        modifier = modifier,
    ) {
        tempListOfApps.forEach { app ->
            s {
                HomeTile(app = app) {
                    val intent = pm.getLaunchIntentForPackage(app.packageName)
                    context.startActivity(intent)
                }
            }
        }
    }
}
