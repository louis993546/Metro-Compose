package com.louis993546.skylight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.louis993546.metro.ApplicationBar
import com.louis993546.metro.LocalAccentColor
import com.louis993546.metro.LocalTextOnAccentColor
import com.louis993546.metro.MetroTheme
import com.louis993546.metro.Pages
import com.louis993546.metro.Text

class SkylightActivity : ComponentActivity() {
    private val pages = listOf(
        "Follow",
        "Discovery",
        "TODO other custom feed"
    )

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO make it look just like the text-heavy Twitter App
            //   https://www.youtube.com/watch?v=dz-Dp9L8l78
            //   then make it work for 2024: gestures, image/videos, re-tweet & quote tweet, etc.
            MetroTheme(
                accentColor = Color(0xFF0285FF),
                onAccentColor = Color.White,
            ) {
                Column {
                    Spacer(modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars))
                    Pages(
                        pageTitles = pages,
                        titleTextColor = LocalAccentColor.current,
                        modifier = Modifier.weight(1f),
                    ) { _ ->
//                        Text(text = pages[pageNumber])
                        Feed()
                    }
                    ApplicationBar(
                        modifier = Modifier.fillMaxWidth(),
                        count = 4,
                        icon = {
                            Image(
                                modifier = Modifier,
                                painter = painterResource(id = R.drawable.baseline_refresh_24),
                                contentDescription = "Search",
                                colorFilter = ColorFilter.tint(color = LocalTextOnAccentColor.current)
                            )
                        },
                        onButtonClicked = { },
                    )
                }
            }
        }
    }
}

@Composable
private fun Feed(
    modifier: Modifier = Modifier,
) {
    // TODO data => state
    val fakeData = listOf(
        Post(
            author = Author(
                name = "Brian Frank",
                handle = "bfrank",
                avatar = Image(
                    url = "https://pbs.twimg.com/profile_images/673278560044363776/ndaf5yoY_400x400.jpg",
                )
            ),
            text = "Hockey time! (@ Yerba Buena Ice Skating & Bowling Center) 4sq.com/KoORys",
        ),
        Post(
            author = Author(
                name = "Grace Chu Lee",
                handle = "gracelee",
                avatar = Image(
                    url = "https://pbs.twimg.com/profile_images/1314010814496473088/QRr7JAFd_400x400.jpg",
                )
            ),
            text = "Love when it bring your family to work day - thx @TwitterSF pic.twitter.com/jj5k6uxx"
        )
    )
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(fakeData, key = { it.hashCode()}) {
            PostCard(post = it)
        }
    }
}

private data class Author(
    val name: String,
    val handle: String,
    val avatar: Image,
    // TODO image
    // TODO should non-follow info be here?
)

private data class Image(
    val url: String,
)

private data class Post(
    val author: Author,
    // TODO time ago?
    val repostAuthor: Author? = null,
    val text: String?, // Jane managed to post something with no content
    // TODO image, video, quote-post, etc.
)

@Composable
private fun PostCard(
    modifier: Modifier = Modifier,
    post: Post,
) {
    Row(modifier = modifier) {
        AsyncImage(
            model = post.author.avatar.url,
            contentDescription = "Avatar of ${post.author.name}",
            modifier = Modifier.size(48.dp),
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(text = post.author.handle)
                Text(text = "timeAgo")
            }
            Spacer(Modifier.height(8.dp))
            Text(text = post.text.orEmpty(), size = 16.sp)
        }
    }
}
