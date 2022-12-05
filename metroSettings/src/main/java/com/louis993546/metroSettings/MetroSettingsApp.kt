package com.louis993546.metroSettings

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.louis993546.metro.ApplicationBar
import com.louis993546.metro.LocalTextOnButtonColor
import com.louis993546.metro.MessageBox
import com.louis993546.metro.Pages
import com.louis993546.metro.Text
import com.louis993546.metro.TitleBar

@ExperimentalPagerApi
@Composable
fun MetroSettingsApp(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TitleBar(title = "METRO SETTINGS")

        Pages(
            modifier = Modifier
                .weight(1f),
            pageTitles = listOf("settings", "about", "open-source licenses")
        ) { page ->
            when (page) {
                0 -> Settings(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                1 -> AboutUs(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
                2 -> OpenSourceLicenses(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }
        }

        val context = LocalContext.current
        ApplicationBar(
            modifier = Modifier.fillMaxWidth(),
            count = 1,
            icon = {
                Image(
                    // make the share icon looks visibly center
                    modifier = Modifier.padding(end = 3.dp),
                    painter = painterResource(id = R.drawable.ic_baseline_share_24),
                    contentDescription = "Share",
                    colorFilter = ColorFilter.tint(color = LocalTextOnButtonColor.current)
                )
            }
        ) {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "https://play.google.com/store/apps/details?id=com.louis993546.metro.demo"
                )
                putExtra(Intent.EXTRA_TITLE, "Metro Demo on Play Store")
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            context.startActivity(shareIntent)
        }
    }
}

@Composable
internal fun Settings(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxHeight()) {
        Text(text = "TODO Real browser, or in-app fake IE")
        Text(text = "TODO typography")
        Text(text = "TODO screen ratio (e.g. 15:9 like 920, 16:9, or full screen)")
        Text(text = "TODO override 4 or 6 columns")
    }
}

@Composable
internal fun AboutUs(
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxHeight()) {
        Text(text = "Under Construction")
    }
}

@Composable
internal fun OpenSourceLicenses(
    modifier: Modifier = Modifier,
) {
    LazyColumn(modifier = modifier) {
        items(items = libraries, key = { it.name }) { lib ->
            OpenSourceLicenseRow(library = lib)
        }
    }
}

private fun Context.startBrowserActivity(uri: String) {
    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
}

@Composable
internal fun OpenSourceLicenseRow(
    modifier: Modifier = Modifier,
    library: Library,
) {
    val context = LocalContext.current
    var openDialog by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.clickable { library.url?.let { context.startBrowserActivity(it) } },
    ) {
        Text(
            text = library.name,
            size = 24.sp,
            modifier = Modifier.clickable { openDialog = true }
        )
    }

    if (openDialog) {
        MessageBox(onDismissRequest = { openDialog = false }) {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    text = "I need a button component",
                    modifier = Modifier.run {
                        if (library.url.isNullOrEmpty()) this
                        else this.clickable { context.startBrowserActivity(library.url) }
                    },
                )
                Text(
                    text = library.license ?: "", // TODO maybe this should not be nullable?
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

data class Library(
    val name: String,
    val url: String?,
    val license: String?,
)

val libraries = listOf(
    Library(
        name = "Selawik",
        url = "https://github.com/Microsoft/Selawik",
        license = """
        Copyright 2015, Microsoft Corporation (www.microsoft.com), with Reserved Font Name Selawik.  All Rights Reserved.  Selawik is a trademark of Microsoft Corporation in the United States and/or other countries.

        This Font Software is licensed under the SIL Open Font License, Version 1.1.
        This license is copied below, and is also available with a FAQ at:
        http://scripts.sil.org/OFL

        -----------------------------------------------------------
        SIL OPEN FONT LICENSE Version 1.1 - 26 February 2007
        -----------------------------------------------------------
        PREAMBLE
        The goals of the Open Font License (OFL) are to stimulate worldwide
        development of collaborative font projects, to support the font creation
        efforts of academic and linguistic communities, and to provide a free and
        open framework in which fonts may be shared and improved in partnership
        with others.

        The OFL allows the licensed fonts to be used, studied, modified and
        redistributed freely as long as they are not sold by themselves. The
        fonts, including any derivative works, can be bundled, embedded, 
        redistributed and/or sold with any software provided that any reserved
        names are not used by derivative works. The fonts and derivatives,
        however, cannot be released under any other type of license. The
        requirement for fonts to remain under this license does not apply
        to any document created using the fonts or their derivatives.

        DEFINITIONS
        "Font Software" refers to the set of files released by the Copyright
        Holder(s) under this license and clearly marked as such. This may
        include source files, build scripts and documentation.

        "Reserved Font Name" refers to any names specified as such after the
        copyright statement(s).

        "Original Version" refers to the collection of Font Software components as
        distributed by the Copyright Holder(s).

        "Modified Version" refers to any derivative made by adding to, deleting,
        or substituting -- in part or in whole -- any of the components of the
        Original Version, by changing formats or by porting the Font Software to a
        new environment.

        "Author" refers to any designer, engineer, programmer, technical
        writer or other person who contributed to the Font Software.

        PERMISSION & CONDITIONS
        Permission is hereby granted, free of charge, to any person obtaining
        a copy of the Font Software, to use, study, copy, merge, embed, modify,
        redistribute, and sell modified and unmodified copies of the Font
        Software, subject to the following conditions:

        1) Neither the Font Software nor any of its individual components,
        in Original or Modified Versions, may be sold by itself.

        2) Original or Modified Versions of the Font Software may be bundled,
        redistributed and/or sold with any software, provided that each copy
        contains the above copyright notice and this license. These can be
        included either as stand-alone text files, human-readable headers or
        in the appropriate machine-readable metadata fields within text or
        binary files as long as those fields can be easily viewed by the user.

        3) No Modified Version of the Font Software may use the Reserved Font
        Name(s) unless explicit written permission is granted by the corresponding
        Copyright Holder. This restriction only applies to the primary font name as
        presented to the users.

        4) The name(s) of the Copyright Holder(s) or the Author(s) of the Font
        Software shall not be used to promote, endorse or advertise any
        Modified Version, except to acknowledge the contribution(s) of the
        Copyright Holder(s) and the Author(s) or with their explicit written
        permission.

        5) The Font Software, modified or unmodified, in part or in whole,
        must be distributed entirely under this license, and must not be
        distributed under any other license. The requirement for fonts to
        remain under this license does not apply to any document created
        using the Font Software.

        TERMINATION
        This license becomes null and void if any of the above conditions are
        not met.

        DISCLAIMER
        THE FONT SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
        EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO ANY WARRANTIES OF
        MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT
        OF COPYRIGHT, PATENT, TRADEMARK, OR OTHER RIGHT. IN NO EVENT SHALL THE
        COPYRIGHT HOLDER BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
        INCLUDING ANY GENERAL, SPECIAL, INDIRECT, INCIDENTAL, OR CONSEQUENTIAL
        DAMAGES, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
        FROM, OUT OF THE USE OR INABILITY TO USE THE FONT SOFTWARE OR FROM
        OTHER DEALINGS IN THE FONT SOFTWARE.

        """.trimIndent(),
    ),
)