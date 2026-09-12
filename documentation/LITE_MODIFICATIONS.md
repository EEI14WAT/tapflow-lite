# TapFlow Lite modifications

## Upstream and license

TapFlow Lite is an independent GPL-3.0 derivative of Klick'r / Smart AutoClicker. The upstream project, repository, version 4.0.5, and commit 947aef0 are recorded in [UPSTREAM.md](UPSTREAM.md). The upstream LICENSE remains at the repository root.

## Lite validation changes

- Added AdSlot, AdSlotProvider, and NoOpAdProvider under the app module.
- Added non-clickable reserved ad locations to the scenario list and settings screens; the task-completion identifier is reserved but not rendered.
- No advertising, analytics, account, cloud, or network SDK is active in this edition.
- Kept the existing accessibility, overlay, MediaProjection, click/swipe, scenario persistence, and image-detection paths.
- Lite mode hides legacy action/notification settings and the unused remove-ads entry while retaining compatibility code.

## TapFlow Lite branding changes

- Branded the user-visible app name as **TapFlow Lite**.
- Changed the installable application ID to io.github.eei14wat.tapflow and set version name 0.1.0.
- Retained the internal com.buzbuz.smartautoclicker source namespace and directly register the existing component classes with the runtime package name, avoiding a behavior-changing mass package rename.
- Added TapFlow launcher assets generated from branding/tapflow-lite-icon-source.png.
- Added adaptive-icon foreground padding so the supplied artwork remains inside Android's mask-safe area. The supplied raster is not a reliable monochrome source, so the new adaptive icon intentionally omits a themed monochrome layer.
- The supplied source icon is a PNG with only IHDR, sRGB, pHYs, IDAT, and IEND chunks; it contains no text, EXIF, or location metadata.

## v0.1.0 release-preparation changes

- Added the publication-ready [root privacy policy](../PRIVACY.md), an in-app system-browser link to it, and source/GPL/upstream links in Settings.
- Added explicit local-processing disclosures before Accessibility settings, first MediaProjection authorization, and optional OCR-model download.
- Moved inherited publication, Play Store, and nightly-obfuscation workflows to `documentation/upstream-workflows-disabled/`; only test workflows remain active.
- Added an explicit opt-in release-signing configuration. It produces an unsigned release unless all external signing properties are supplied; no debug signing fallback is used.
- Pinned NCNN source retrieval to the official `20260113` full-source ZIP, including its SHA-256, cache validation, partial-download cleanup, and offline-cache instructions.

## Before publishing

- Publish the exact corresponding source and this modification record under GPL-3.0.
- Complete and publish the privacy policy.
- Create and protect a release signing key outside this repository.
- Review any future advertising SDK for license, privacy, consent, store, and accessibility-policy implications before enabling it.