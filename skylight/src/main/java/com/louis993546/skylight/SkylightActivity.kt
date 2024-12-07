package com.louis993546.skylight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class SkylightActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO make it look just like the text-heavy Twitter App
            //   https://www.youtube.com/watch?v=dz-Dp9L8l78
            //   then make it work for 2024: gestures, image/videos, re-tweet & quote tweet, etc.
        }
    }
}
