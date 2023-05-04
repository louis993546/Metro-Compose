package com.louis993546.metro.demo.launcher

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.louis993546.metro.AdaptiveText
import com.louis993546.metro.AdaptiveTextStyle
import com.louis993546.metro.CircleButton
import com.louis993546.metro.LiveTile
import com.louis993546.metro.LiveTileBinding
import com.louis993546.metro.LiveTileBranding
import com.louis993546.metro.LiveTileContentAdaptive
import com.louis993546.metro.LiveTileContentIconWithBadge
import com.louis993546.metro.LiveTileContentTileSquareBlock
import com.louis993546.metro.LiveTileSize
import com.louis993546.metro.LiveTileVisual
import com.louis993546.metro.LocalTextOnBackgroundColor
import com.louis993546.metro.VisualizeLiveTile
import com.louis993546.metro.demo.VerticalTilesGrid
import com.louis993546.metro.demo.apps.Apps

import timber.log.Timber

/**
 * Suppress LongMethod, as in long run, this whole thing should be configurable by the users
 */
@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedBoxWithConstraintsScope")
@Suppress("LongMethod")
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    onAppClick: (Apps) -> Unit,
    onArrowClick: () -> Unit,
) {

    val mail = LiveTile(
        badge = "10",
        visual = LiveTileVisual(
            displayName = "Mail",
            branding = LiveTileBranding.NameAndLogo,
            tileWide = LiveTileBinding(
                content = LiveTileContentAdaptive(
                    children = listOf(
                        AdaptiveText(
                            text = "Basically a title",
                            style = AdaptiveTextStyle.Base,
                        ),
                        AdaptiveText(
                            text = "The actual content here which might very well get cut off",
                            style = AdaptiveTextStyle.CaptionSubtle,
                        ),
                    )
                )
            )
        )
    )

    val calendar = LiveTile(
        visual = LiveTileVisual(
            displayName = "Calendar",
            branding = LiveTileBranding.None,
            tileMedium = LiveTileBinding(
                content = LiveTileContentTileSquareBlock(
                    besideBlockText = "Sat",
                    blockText = "12",
                    text = "This is a place for current calendar events",
                )
            )
        )
    )

    val settings = LiveTile(
        icon = painterResource(id = R.drawable.ic_baseline_settings_24),
        visual = LiveTileVisual(
            displayName = "Settings",
            branding = LiveTileBranding.Name,
            tileMedium = LiveTileBinding(
                content = LiveTileContentIconWithBadge()
            )
        )
    )

    val badge = LiveTile(
        badge = "1",
        icon = painterResource(id = R.drawable.ic_baseline_photo_camera_24),
        visual = LiveTileVisual(
            displayName = "Test",
            branding = LiveTileBranding.Name,
            tileSmall = LiveTileBinding(
                content = LiveTileContentIconWithBadge()
            ),
            tileMedium = LiveTileBinding(
                content = LiveTileContentAdaptive(
                    children = listOf(
                        AdaptiveText(
                            text = "medium"
                        ),
                    )
                )
            ),
            tileWide = LiveTileBinding(
                content = LiveTileContentAdaptive(
                    children = listOf(
                        AdaptiveText(
                            text = "wide"
                        ),
                    )
                )
            ),
            tileLarge = LiveTileBinding(
                content = LiveTileContentAdaptive(
                    children = listOf(
                        AdaptiveText(
                            text = "About those large live tiles",
                            wrap = true,
                            style = AdaptiveTextStyle.Base,
                        ),
                        AdaptiveText(
                            text = "This size never actually managed to come over to the Phone, " +
                                    "though based on early cshell leaks it appears to have been in " +
                                    "the works for Windows 10 Mobile.",
                            wrap = true,
                        ),
                        AdaptiveText(
                            text = "Since the tile library supports it we might as well let the user " +
                                    "have access to large live tiles too, even if they didn't exist " +
                                    "in Windows Phone 8.1.",
                            wrap = true,
                        ),
                    )
                )
            )
        )
    )

    val haptics = LocalHapticFeedback.current;
    val createInteractionModifier: (action: (() -> Unit)?) -> Modifier = { action ->
        Modifier.combinedClickable(
            onClick = {
                if (action != null) {
                    action()
                }
            },
            onLongClick = {
                haptics.performHapticFeedback(HapticFeedbackType.LongPress)
            },
        )
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            VerticalTilesGrid(
                columns = 6,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 18.dp, bottom = 6.dp, start = 14.dp, end = 12.dp),
                gap = 12.dp,
            ) {
                Wide {
                    VisualizeLiveTile(
                        tile = mail,
                        tileSize = LiveTileSize.Wide,
                        modifier = createInteractionModifier {},
                    )
                }
                Medium {
                    VisualizeLiveTile(
                        tile = calendar,
                        tileSize = LiveTileSize.Medium,
                        modifier = createInteractionModifier {},
                    )
                }
                Medium {
                    VisualizeLiveTile(
                        tile = settings,
                        tileSize = LiveTileSize.Medium,
                        modifier = createInteractionModifier { onAppClick(Apps.METRO_SETTINGS) },
                    )
                }
                Wide {
                    VisualizeLiveTile(
                        tile = badge,
                        tileSize = LiveTileSize.Wide,
                        modifier = createInteractionModifier {},
                    )
                }
                Wide {
                    VisualizeLiveTile(
                        tile = badge,
                        tileSize = LiveTileSize.Wide,
                        modifier = createInteractionModifier {},
                    )
                }
                Small {
                    VisualizeLiveTile(
                        tile = badge,
                        tileSize = LiveTileSize.Small,
                        modifier = createInteractionModifier {},
                    )
                }
                Small {
                    VisualizeLiveTile(
                        tile = badge,
                        tileSize = LiveTileSize.Small,
                        modifier = createInteractionModifier {},
                    )
                }
                Small {
                    VisualizeLiveTile(
                        tile = badge,
                        tileSize = LiveTileSize.Small,
                        modifier = createInteractionModifier {},
                    )
                }
                Small {
                    VisualizeLiveTile(
                        tile = badge,
                        tileSize = LiveTileSize.Small,
                        modifier = createInteractionModifier {},
                    )
                }
                Large {
                    VisualizeLiveTile(
                        tile = badge,
                        tileSize = LiveTileSize.Large,
                        modifier = createInteractionModifier {},
                    )
                }
                Medium {
                    VisualizeLiveTile(
                        tile = badge,
                        tileSize = LiveTileSize.Medium,
                        modifier = createInteractionModifier {},
                    )
                }
                Medium {
                    VisualizeLiveTile(
                        tile = badge,
                        tileSize = LiveTileSize.Medium,
                        modifier = createInteractionModifier {},
                    )
                }
                Medium {
                    VisualizeLiveTile(
                        tile = badge,
                        tileSize = LiveTileSize.Medium,
                        modifier = createInteractionModifier {},
                    )
                }
                Medium {
                    VisualizeLiveTile(
                        tile = badge,
                        tileSize = LiveTileSize.Medium,
                        modifier = createInteractionModifier {},
                    )
                }
                Medium {
                    VisualizeLiveTile(
                        tile = badge,
                        tileSize = LiveTileSize.Medium,
                        modifier = createInteractionModifier {},
                    )
                }
                Medium {
                    VisualizeLiveTile(
                        tile = badge,
                        tileSize = LiveTileSize.Medium,
                        modifier = createInteractionModifier {},
                    )
                }
            }

            CircleButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(vertical = 8.dp, horizontal = 12.dp)
                    .clickable {
                        onArrowClick()
                    },
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
}
