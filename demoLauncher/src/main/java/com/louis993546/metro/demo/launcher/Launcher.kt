package com.louis993546.metro.demo.launcher

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.louis993546.metro.CircleButton
import com.louis993546.metro.LocalAccentColor
import com.louis993546.metro.LocalTextOnAccentColor
import com.louis993546.metro.LocalTextOnBackgroundColor
import com.louis993546.metro.Text
import com.louis993546.metro.demo.VerticalTilesGrid
import com.louis993546.metro.demo.apps.Apps

/**
 * Suppress LongMethod, as in long run, this whole thing should be configurable by the users
 */
@Suppress("LongMethod")
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    onAppClick: (Apps) -> Unit,
    onArrowClick: () -> Unit,
) {
    // TODO https://stackoverflow.com/questions/68334723/how-to-do-the-wp8-background-effect-in-jetpack-compose
//        Image(
//            modifier = Modifier.fillMaxWidth(),
//            painter = painterResource(id = R.drawable.image),
//            contentDescription = "background",
//            contentScale = ContentScale.Crop,
//        )
    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        VerticalTilesGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp)
                .padding(horizontal = 12.dp),
            gap = 12.dp,
        ) {
            s {
                HomeTile(
                    title = "Calculator",
                    iconRes = R.drawable.ic_baseline_calculate_24,
                    activate = { onAppClick(Apps.CALCULATOR) }
                )
            }
            s {
                HomeTile(
                    title = "FM Radio",
                    iconRes = R.drawable.ic_baseline_radio_24,
                )
            }
            m {
                HomeTile(
                    title = "Browser",
                    iconRes = R.drawable.outline_public_24,
                    activate = { onAppClick(Apps.BROWSER) }
                )
            }
            s {
                HomeTile(
                    title = "Camera",
                    iconRes = R.drawable.ic_baseline_photo_camera_24,
                    activate = { onAppClick(Apps.CAMERA) }
                )
            }
            s {
                HomeTile(
                    title = "Calendar",
                    iconRes = R.drawable.ic_baseline_calculate_24,
                    activate = { onAppClick(Apps.CALENDAR) }
                )
            }
            l { HomeTile(title = "Photos") }
            m {
                HomeTile(
                    title = "7:00",
                    iconRes = R.drawable.outline_public_24,
                )
            }
            s {
                HomeTile(
                    title = "Videos"
                )
            }
            s {
                HomeTile(
                    title = "Wordle",
                    iconRes = R.drawable.wordle_icon,
                    activate = { onAppClick(Apps.WORDLE) }
                )
            }
            m {
                HomeTile(
                    title = "Maps",
                    iconRes = R.drawable.ic_baseline_map_24,
                )
            }
            s {
                HomeTile(
                    title = "Docs"
                )
            }
            s {
                HomeTile(
                    title = "", // Metro Settings
                    iconRes = R.drawable.ic_baseline_settings_24,
                    activate = { onAppClick(Apps.SETTINGS) }
                )
            }
            l {
                HomeTile(
                    title = "People"
                )
            }
            s {
                HomeTile(
                    title = "", // Settings
                    iconRes = R.drawable.ic_baseline_settings_24,
                    activate = { onAppClick(Apps.METRO_SETTINGS) }
                )
            }
        }

        CircleButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(8.dp)
                .clickable {
                    onArrowClick()
                }
        ) {
            Image(
                modifier = Modifier
                    .padding(6.dp),
                painter = painterResource(id = R.drawable.ic_baseline_arrow_forward_24),
                contentDescription = "Apps list",
                colorFilter = ColorFilter.tint(
                    LocalTextOnBackgroundColor.current
                ),
            )
        }
    }
}


/**
 * Unlike normal Tile, HomeTile can be rectangle, not just square
 * FIXME We need to know the size of the tile to set the correct
 *  icon size and whether to show the label or not
 */
@Composable
fun HomeTile(
    @DrawableRes iconRes: Int? = null,
    title: String = "",
    backgroundColor: Color = LocalAccentColor.current,
    textColor: Color = LocalTextOnAccentColor.current,
    activate: (() -> Unit)? = null,
) {
    Box(
        modifier = Modifier.clickable { activate?.invoke() }
    ) {
        Box(
            modifier = Modifier
                .background(color = backgroundColor)
                .fillMaxSize()
        ) {
            iconRes?.let { res ->
                Image(
                    modifier = Modifier
                        .size(36.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = res),
                    contentDescription = "", // TODO fix me
                    colorFilter = ColorFilter.tint(textColor),
                )
            }

            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 10.dp, bottom = 6.dp),
                text = title,
                size = 18.sp,
                color = textColor,
                maxLine = 1
            )
        }
    }
}
