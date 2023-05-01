## Build

- Open this project with "Android Studio Hedgehog | 2023.1.1 Canary 1" or newer.
  - if you use homebrew: use [this formula](https://github.com/Homebrew/homebrew-cask-versions/blob/master/Casks/android-studio-preview-canary.rb)
- Run `git config core.hooksPath githooks` to make sure the githook is run for each commit.

### File Bugs

Please file a bug in "Issue" page above. 

### Fix Bugs

PRs are always welcome! And I am also slowly staring to start tagging good first-contribution issues.

### File Feature Requests

Please file a bug in "Issue" page above. 

### File New App

(WIP, need to make the home screen useable first)

### Naming scheme

For codes in `metro`, they should mostly follow the typical Android naming scheme, to make the
`metro` library easier to get familiar with. However, feel free to add some alias in WP8 
(XAML & Silverlight) naming scheme

### Stability

As this is still very alpha, feel free to make breaking change in the API surface. Just remember to
use `./gradlew apiDump` to record what's new
