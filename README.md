# TapFlow Lite

TapFlow Lite is an open-source Android automation app for user-configured taps, swipes, loops, saved scenarios, and basic image-triggered automation. It is a GPL-3.0 derivative of Klick'r / Smart AutoClicker, not an official upstream release.

## Current scope

- User-configured click, swipe, delay, and loop actions.
- Accessibility-service and overlay permission flows required to run automations.
- Scenario save/restore and basic image detection.
- No account system, cloud sync, analytics SDK, or active advertising SDK.
- Reserved Lite advertising interfaces are no-op placeholders only; no network requests are made.

This repository is a validation edition. It is not published to Google Play, F-Droid, or any app store.

## Build

Use Android Studio's bundled JDK and the checked-in local Gradle launcher:

    $env:JAVA_HOME = 'C:\Program Files\Android\Android Studio\jbr'
    .\.codex-gradle\gradle-9.5.1\bin\gradle.bat :smartautoclicker:assembleFDroidDebug --no-daemon

The F-Droid debug APK uses application ID io.github.eei14wat.tapflow.debug; release variants use io.github.eei14wat.tapflow. The source namespace remains com.buzbuz.smartautoclicker intentionally to avoid an unsafe mass refactor. Existing Smart AutoClicker/Klick'r installations are not migrated or modified.

## License and attribution

TapFlow Lite is distributed under [GNU GPL-3.0](LICENSE). The complete upstream attribution, version, and source commit are recorded in [documentation/UPSTREAM.md](documentation/UPSTREAM.md). Lite and branding changes are recorded in [documentation/LITE_MODIFICATIONS.md](documentation/LITE_MODIFICATIONS.md).

For issues specific to this derivative, use this repository's issue tracker. Do not report TapFlow Lite issues to the upstream project.

## Privacy and release preparation

The authoritative v0.1.0 privacy policy is [PRIVACY.md](PRIVACY.md). The in-app Settings screen opens the privacy policy, source repository, GPL-3.0 license, and upstream-attribution record in the system browser. Manual signing and the verified NCNN source-cache procedure are documented in [documentation/RELEASE_SIGNING.md](documentation/RELEASE_SIGNING.md).

## Before any public release

Follow [documentation/LITE_RELEASE_CHECKLIST.md](documentation/LITE_RELEASE_CHECKLIST.md), publish the exact corresponding GPL source, provide a completed privacy policy, and use a separately managed release-signing key. No signing material is included in this repository.