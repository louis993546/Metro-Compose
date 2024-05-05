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

<details>
<summary>0.80.0 or older</summary>

| `metro` version  | Compose version |
|------------------|-----------------|
| 0.10.0 to 0.12.0 | 1.0.0-rc01      |
| 0.13.0 to 0.25.0 | 1.0.0-rc02      |
| 0.26.0 to 0.49.0 | 1.1.1           |
| 0.50.0 to 0.52.0 | 1.2.0-beta02    |
| 0.53.0           | 1.2.0-rc01      |
| 0.54.0 to 0.66.0 | 1.2.0-rc02      |
| 0.67.0 to 0.69.0 | 1.3.0-beta01    |
| 0.70.0 to 0.73.0 | 1.3.0-beta03    |
| 0.74.0 to 0.79.0 | 1.3.0-rc01      |

</details>

And from 0.80.0 and onwards, it uses the [Compose Bill of Materials](https://developer.android.com/jetpack/compose/setup#bom-version-mapping) instead.

| `metro` version    | BoM version |
|--------------------|-------------|
| 0.80.0 to 0.101.0  | 2022.11.00  |
| 0.102.0 to 0.104.0 | 2023.01.00  |
| 0.105.0 to 0.134.0 | 2023.03.00  |
| 0.135.0 to 0.141.0 | 2023.08.00  |
| 0.142.0 to 0.144.0 | 2023.09.01  |
| 0.146.0 to 0.156.0 | 2023.10.01  |
| 0.157.0 to 0.160.0 | 2024.01.00  |
| 0.161.0 to 0.163.0 | 2024.02.00  |
| 0.164.0 to 0.170.0 | 2024.02.01  |
| 0.171.0 to 0.172.0 | 2024.03.00  |
| 0.173.0 to 0.174.0 | 2024.04.01  |
| 0.175.0 to latest  | 2024.05.00  |


## Project Structure

| Module              | Purpose                                                                      |
|---------------------|------------------------------------------------------------------------------|
| `demo`              | Build a simplified Windows Phone 8.1 UI                                      |
| `metro`             | The library that allows others to build Metro-ish apps using Jetpack Compose |
| `verticalTilesGrid` | The thing that makes the 4-column grid thing possible                        |
| `demoAapps`         | Just a Kotlin class that keeps list of apps. So kinda registry situation     |
| `seattle`           | A real Android launcher that uses the `metro` theme                          |

And these are the list of build-in "apps"

| Module              | App                                                  |
|---------------------|------------------------------------------------------|
| `demoAppDrawer`     | Display list of apps                                 |
| `demoAppRow`        | Display a row of app                                 |
| `demoAppSearch`     | Allow searching of apps                              |
| `demoBrowser`       | Fake Internet Explorer                               |
| `demoCalculator`    | Calculator                                           |
| `demoCalendar`      | Calendar                                             |
| `demoLauncher`      | Controls what the home screen grid shows             |
| `demoMetroSettings` | Settings and information for the demo app itself     |
| `demoRadio`         | Radio app                                            |
| `demoSettings`      | Settings app from WP8 itself                         |
| `demoWordle`        | The game that was popular and then got bought by NYT |
| `demoCamera`        | 📷                                                   |

## Contribution

See [CONTRIBUTIONS](CONTRIBUTIONS.md)

## LICENSE

See [LICENSE](LICENSE)

## Dependency Diagram

### How to update

Run the following

```shell
./gradlew createModuleGraph
```

### Graph

```mermaid
%%{
  init: {
    'theme': 'neutral'
  }
}%%

graph LR
  :demoAppDrawer --> :metro
  :demoAppDrawer --> :demoApps
  :demoAppDrawer --> :demoAppRow
  :demoAppSearch --> :metro
  :demoAppSearch --> :demoApps
  :demoAppSearch --> :demoAppRow
  :demoWordle --> :metro
  :seattle --> :metro
  :seattle --> :demoAppRow
  :demoCamera --> :metro
  :demoSettings --> :metro
  :demoAppRow --> :metro
  :demoAppRow --> :demoApps
  :demoMetroSettings --> :metro
  :demoCalendar --> :metro
  :demo --> :metro
  :demo --> :demoApps
  :demo --> :demoAppDrawer
  :demo --> :demoAppSearch
  :demo --> :demoBrowser
  :demo --> :demoCalculator
  :demo --> :demoCalendar
  :demo --> :demoLauncher
  :demo --> :demoMetroSettings
  :demo --> :demoRadio
  :demo --> :demoSettings
  :demo --> :demoWordle
  :demoCalculator --> :metro
  :demoBrowser --> :metro
  :demoRadio --> :metro
  :demoLauncher --> :metro
  :demoLauncher --> :demoApps
  :demoLauncher --> :verticalTilesGrid
```