package com.louis993546.metro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension

@Composable
fun VisualizeLiveTile (
    modifier: Modifier = Modifier,
    tile: LiveTile,
    tileSize: LiveTileSize,
) {
    val visual = tile.visual

    ConstraintLayout(
        modifier = modifier
            .fillMaxSize()
            .background(color = LocalAccentColor.current),
    ) {
        val binding = when (tileSize) {
            LiveTileSize.Small -> visual.tileSmall
            LiveTileSize.Medium -> visual.tileMedium
            LiveTileSize.Wide -> visual.tileWide
            LiveTileSize.Large -> visual.tileLarge
        } ?: return@ConstraintLayout

        val content = binding.content

        val body = createRef()
        VisualizeLiveTileContent(
            content = content,
            tile = tile,
            modifier = Modifier.constrainAs(body) {
                // TODO: this should be it's own util function since
                //       it's later used in groups.

                if (content is LiveTileContentAdaptive) {
                    when (content.textStacking) {
                        AdaptiveTextStacking.Center -> {
                            centerHorizontallyTo(parent)
                            centerVerticallyTo(parent)
                            return@constrainAs
                        }
                        AdaptiveTextStacking.Bottom -> {
                            // TODO
                        }

                        // Handled bellow
                        AdaptiveTextStacking.Top -> {}
                        AdaptiveTextStacking.Default -> {}
                        else -> {}
                    }
                }

                linkTo(
                    top = parent.top,
                    bottom = parent.bottom,
                    start = parent.start,
                    end = parent.end,
                )
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )

        // TODO: binding might overwrite visual.
        val branding = createRef()
        VisualizeLiveTileBranding(
            visual = when(tileSize) {
                LiveTileSize.Small -> LiveTileVisual(
                    displayName = visual.displayName,
                    branding = LiveTileBranding.None
                )
                else -> visual
            },
            modifier = Modifier
                // TODO: fix line-heights instead of doing this.
                .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 8.dp)
                .constrainAs(branding) {
                bottom.linkTo(parent.bottom)
                width = Dimension.fillToConstraints
            },
        )
    }
}

@Composable
fun VisualizeLiveTileContent (
    modifier: Modifier = Modifier,
    tile: LiveTile,
    content: LiveTileContent,
) {
    val padding = Modifier
        // TODO: fix line-heights instead of doing this.
        .padding(top = 10.dp, start = 10.dp, end = 10.dp, bottom = 8.dp)
    val paddingBrandingMargin = padding
        .padding(bottom = 20.dp)

    when(content) {
        is LiveTileContentAdaptive -> {
            VisualizeLiveTileContentAdaptive(
                modifier = modifier
                    .then(paddingBrandingMargin),
                content = content,
            )
        }
        is LiveTileContentTileSquareBlock -> {
            VisualizeLiveTileContentTileSquareBlock(
                modifier = modifier
                    .then(padding),
                content = content,
            )
        }
        is LiveTileContentIconWithBadge -> {
            VisualizeLiveTileContentIconWithBadge(
                modifier = modifier,
                tile = tile,
                content = content,
            )
        }
        else -> {} // TODO
    }
}

@Composable
fun VisualizeLiveTileContentTileSquareBlock (
    modifier: Modifier = Modifier,
    content: LiveTileContentTileSquareBlock,
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxSize(),
    ) {
        val body = createRef()
        Box(
            modifier = Modifier
                .constrainAs(body) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                },
        ) {
            BasicText(
                text = content.text,
                style = FromAdaptiveTextStyle(
                    AdaptiveTextStyle.Caption,
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 3
            )
        }

        val title = createRef()
        Row(
            modifier = Modifier
                .constrainAs(title) {
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth(),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.End),
        ) {
            BasicText(
                modifier = Modifier,
                text = content.besideBlockText,
                style = FromAdaptiveTextStyle(
                    AdaptiveTextStyle.Caption,
                    AdaptiveTextAlign.Right,
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            BasicText(
                modifier = Modifier
                    .height(34.dp), // TODO: fix line-height instead.
                text = content.blockText,
                style = FromAdaptiveTextStyle(
                    AdaptiveTextStyle.Title,
                    AdaptiveTextAlign.Left,
                ).copy(
                    fontSize = 32.sp,
                    lineHeight = 34.sp
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Composable
fun VisualizeLiveTileContentIconWithBadge (
    modifier: Modifier = Modifier,
    tile: LiveTile,
    content: LiveTileContentIconWithBadge,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterHorizontally),
        ) {
            if (tile.icon != null)
                Image(
                    modifier = Modifier
                        .then(
                            if (tile.badge == "")
                                Modifier.size(64.dp)
                            else
                                Modifier
                        ),
                    painter = tile.icon,
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    alignment = Alignment.Center,
                )

            if (tile.badge != "")
                BasicText(
                    text = tile.badge, // TODO: handle 99+.
                    style = FromAdaptiveTextStyle(
                        // TODO: base size on tile size.
                        AdaptiveTextStyle.Subtitle,
                    ),
                    overflow = TextOverflow.Clip,
                    maxLines = 1,
                )
        }
    }
}

@Composable
fun VisualizeLiveTileContentAdaptive (
    modifier: Modifier = Modifier,
    content: LiveTileContentAdaptive,
) {
    Column(
        modifier = modifier,
    ) {
        if (content.children.isNullOrEmpty())
            return@Column

        content.children.forEach { entry ->
            when(entry) {
                is AdaptiveText -> {
                    VisualizeLiveTileContentAdaptiveText(
                        content = entry,
                    )
                }
                is AdaptiveImage -> {
                    // TODO
                }
                else -> {}
            }
        }
    }
}
@Composable
fun VisualizeLiveTileContentAdaptiveText (
    modifier: Modifier = Modifier,
    content: AdaptiveText,
) {
    // Get style and text alignment
    val style = FromAdaptiveTextStyle(
        content.style,
        content.align
    )
    BasicText(
        modifier = modifier
            .fillMaxWidth(),
        text = content.text,
        style = style,
        overflow = TextOverflow.Ellipsis,
        maxLines = if(!content.wrap) 1 else Int.MAX_VALUE
    )
}

@Composable
fun VisualizeLiveTileBranding (
    modifier: Modifier = Modifier,
    visual: LiveTileVisual,
) {
    Box(
        modifier = modifier
    ) {
        when (visual.branding) {
            LiveTileBranding.Logo -> {
                VisualizeLiveTileBrandingLogo(
                    visual = visual
                )
            }
            LiveTileBranding.Name -> {
                VisualizeLiveTileBrandingName(
                    visual = visual
                )
            }
            LiveTileBranding.NameAndLogo -> {
                VisualizeLiveTileBrandingName(
                    visual = visual
                )
                VisualizeLiveTileBrandingLogo(
                    visual = visual
                )
            }
            LiveTileBranding.None -> {}
        }
    }
}
@Composable
fun VisualizeLiveTileBrandingLogo (
    modifier: Modifier = Modifier,
    visual: LiveTileVisual,
) {
    /*Image(
        modifier = modifier,
        contentDescription = ""
    )*/
}
@Composable
fun VisualizeLiveTileBrandingName (
    modifier: Modifier = Modifier,
    visual: LiveTileVisual,
) {
    BasicText(
        modifier = modifier,
        text = visual.displayName,
        style = FromAdaptiveTextStyle(
            style = AdaptiveTextStyle.Caption
        ),
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}
