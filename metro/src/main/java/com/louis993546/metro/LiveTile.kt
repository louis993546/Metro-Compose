package com.louis993546.metro

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter

enum class LiveTileSize {
    Small,
    Medium,
    Wide,
    Large,
}
enum class LiveTileBranding {
    None,
    Logo,
    Name,
    NameAndLogo
}

enum class AdaptiveTextStacking {
    Default,
    Top,
    Center,
    Bottom,
}

abstract class AdaptiveContent(
) {}
class AdaptiveImage(
    public val source: String,
    public val align: AdaptiveImageAlign =
        AdaptiveImageAlign.Default,
) : AdaptiveContent() {}
enum class AdaptiveImageAlign {
    Default,
    Stretch,
    Left,
    Center,
    Right,
}

class AdaptiveText(
    public val text: String,
    public val wrap: Boolean = false,
    public val style: AdaptiveTextStyle =
        AdaptiveTextStyle.Default,
    public val align: AdaptiveTextAlign =
        AdaptiveTextAlign.Default,
) : AdaptiveContent() {}
enum class AdaptiveTextStyle {
    Default,
    Caption,
    CaptionSubtle,
    Body,
    BodySubtle,
    Base,
    BaseSubtle,
    Subtitle,
    SubtitleSubtle,
    Title,
    TitleSubtle,
    TitleNumeral,
    Subheader,
    SubheaderSubtle,
    SubheaderNumeral,
    Header,
    HeaderSubtle,
    HeaderNumeral,
}
enum class AdaptiveTextAlign {
    Default,
    Auto,
    Left,
    Center,
    Right,
}

class LiveTilePeekImage(
    public val source: String,
    public val overlay: Int = 20,
) {}
class LiveTileBackgroundImage(
    public val source: String,
    public val overlay: Int = 20,
) {}

abstract class LiveTileContent {}
class LiveTileContentAdaptive(
    public val peekImage: LiveTilePeekImage? =
        null,
    public val backgroundImage: LiveTileBackgroundImage? =
        null,
    public val textStacking: AdaptiveTextStacking =
        AdaptiveTextStacking.Default,
    public val children: List<AdaptiveContent>? =
        emptyList()
) : LiveTileContent() {}

//
class LiveTileContentIconWithBadge(
) : LiveTileContent() {}

// https://learn.microsoft.com/en-us/previous-versions/windows/apps/hh761491(v=win.10)#tilesquareblocktilesquare150x150block
class LiveTileContentTileSquareBlock(
    public val blockText: String,
    public val besideBlockText: String,
    public val text: String = "",
) : LiveTileContent() {}

class LiveTileBinding (
    public val displayName: String? = null,
    public val branding: LiveTileBranding? = null,
    public val content: LiveTileContent,
) {}

class LiveTileVisual (
    public val displayName: String,
    public val branding: LiveTileBranding =
        LiveTileBranding.Logo,

    public val tileSmall: LiveTileBinding? = null,
    public val tileMedium: LiveTileBinding? = null,
    public val tileWide: LiveTileBinding? = null,
    public val tileLarge: LiveTileBinding? = null,
) {}

class LiveTile (
    public val visual: LiveTileVisual,
    public val badge: String = "",
    public val icon: Painter? = null,
    public val modifier: Modifier? = null,
) {}

// https://learn.microsoft.com/en-us/previous-versions/windows/apps/hh761491(v=win.10)
