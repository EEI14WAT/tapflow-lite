# TapFlow Lite Privacy Policy

**Effective date:** 2026-09-12
**Publisher:** EEI14WAT
**Contact:** EEIWAT@outlook.com
**Source:** https://github.com/EEI14WAT/tapflow-lite

## Local data and storage

TapFlow Lite stores scenarios, click and swipe coordinates, delays, loop settings, preferences, image-condition samples, manually exported backups, and temporary/debug reports in its Android application-private directory. Android backup is disabled (`android:allowBackup="false"`). Clearing app data or uninstalling normally removes private data. Exported backups remain wherever you saved them until you delete them. Deleting a scenario or image condition removes associated data according to the implemented deletion flow.

## AccessibilityService

After you enable it, Android AccessibilityService executes the taps, swipes, and automation controls you configure. It may access window content and dispatch gestures as required. Accessibility-derived content is processed locally and is not sent to EEI14WAT. Disabling the service prevents automation execution while scenario editing remains available where supported.

## Overlay and screen capture

Overlay permission displays TapFlow Lite controls and editing surfaces over other applications. You control it and can revoke it in the app or Android settings. It is not used for third-party advertising.

Android displays a system consent prompt before MediaProjection capture begins. Capture starts only after your affirmative authorization. Screen frames are processed locally for image detection and are not uploaded to EEI14WAT. User-saved image-condition samples may remain locally until deleted.

## Optional OCR-model download

Supplemental OCR models download only when you request them from the upstream Smart AutoClicker/Klick'r GitHub release URL: https://github.com/Nain57/Smart-AutoClicker/releases/download/recognition-models-1.0.0/ . No scenario, screenshot, click coordinate, or user-created file is uploaded. GitHub and normal network infrastructure may receive connection metadata such as IP address, request time, and request headers; GitHub's privacy terms apply. Downloaded ZIPs and extracted models are stored locally. Failed temporary ZIP downloads are deleted where implemented.

## No monetization or telemetry in v0.1.0

The F-Droid/GitHub edition contains no advertising SDK, analytics SDK, Firebase, Crashlytics, billing, account system, cloud sync, or developer-operated backend. INTERNET permission is retained only for the optional user-initiated OCR-model download.

## Changes and contact

Contact EEIWAT@outlook.com with privacy questions. Material policy changes will be published in the source repository before release. Future advertising or network SDK integration requires a new disclosure before release.

The publication URL is https://github.com/EEI14WAT/tapflow-lite/blob/main/PRIVACY.md. The repository is currently private; it becomes publicly accessible only when made public immediately before release.