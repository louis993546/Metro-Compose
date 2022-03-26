# Metro-Compose

I haven't use anything after Windows Mobile 6, so this is a chance for me to see what's all the
hype ~~is~~ was about

It's currently on open testing on Play Store

<a href='https://play.google.com/store/apps/details?id=com.louis993546.metro.demo&pcampaignid=pcampaignidMKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/static/images/badges/en_badge_web_generic.png' width="200"/></a>

## Status

### The demo app

<img src="/metro-demo.gif" width="360" alt="Screen recording of the demo app"/>

### The library

For the time being, the library is published to GitHub Packages
[over here](https://github.com/louis993546/Metro-Compose/packages/896987)

| `metro` version  | Compose version |
|------------------|-----------------|
| 0.10.0 - 0.12.0  | 1.0.0-rc01      |
| 0.13.0 to latest | 1.0.0-rc02      |

## Project Structure

| Module                | Purpose                                                                      |
|-----------------------|------------------------------------------------------------------------------|
| `demo`                | Build a simplified Windows Phone 8.1 UI                                      |
| `metro`               | The library that allows others to build Metro-ish apps using Jetpack Compose |
| `apps`                | Just a Kotlin class that keeps list of apps. So kinda registry situation     |
| `vertical_tiles_grid` | The thing that makes the 4-column grid thing possible                        |

And these are the list of build-in "apps"

| Module           | App                                              |
|------------------|--------------------------------------------------|
| `metro_settings` | Settings and information for the demo app itself |
| `calculator`     | Calculator                                       |
| `calendar`       | Calendar                                         |
| `settings`       | Settings app from WP8 itself                     |
| `browser`        | Fake Internet Explorer                           |
| `radio`          | Radio app                                        |
| `app_drawer`     | Display list of apps                             |
| `app_search`     | Allow searching of apps                          |
| `launcher`       | Controls what the home screen grid shows         |

## Contribution

### Bugs

(WIP)

### Feature Requests

(WIP)

### Add New App

(WIP, need to make the home screen useable first)

## LICENSE

See [LICENSE](LICENSE)
