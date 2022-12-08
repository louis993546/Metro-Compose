package com.louis993546.metro.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.louis993546.metro.MetroTheme
import com.louis993546.metro.Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MetroLauncherTheme {
                Text(text = "Hello")
            }
        }
    }
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
