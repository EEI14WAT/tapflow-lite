# TapFlow Lite manual branding regression checklist

Run this checklist against a clean install of the F-Droid debug APK. Record device model, Android version, APK hash, and result for each item.

## Identity and installation

- [ ] APK installs alongside an existing upstream Smart AutoClicker/Klick'r installation.
- [ ] Launcher and recent-apps label reads **TapFlow Lite**.
- [ ] Launcher icon is legible on light and dark launchers and is not cropped by circular, squircle, or rounded-square masks.
- [ ] Android Settings shows package io.github.eei14wat.tapflow.debug for the debug build.
- [ ] No upstream data migration prompt, shared data, or overwritten upstream app data appears.

## Permissions and core behavior

- [ ] Accessibility enable flow works; deny flow leaves the app usable and explains the missing permission.
- [ ] Overlay permission enable and deny flows work.
- [ ] Screen-capture consent works for image detection and denial does not crash the app.
- [ ] A single click action runs at the configured location.
- [ ] Multiple clicks, delays, swipes, and loop counts run as configured.
- [ ] Pause, resume, and stop controls work while a scenario is running.
- [ ] A saved scenario survives app restart.
- [ ] A basic image condition triggers when its target appears.
- [ ] Rotation or a resolution change does not crash an active or saved scenario.
- [ ] Background execution remains stable during a short manual run.

## Lite and privacy behavior

- [ ] Scenario list and settings placeholders are non-clickable and do not obstruct normal controls.
- [ ] No advertisement, analytics, login, account, or cloud-sync UI appears.
- [ ] No unexpected network prompt or traffic occurs during the tested local workflows.
- [ ] The Accessibility disclosure explains local processing before Android Settings opens; cancel does not enable the service.
- [ ] The first image-detection request shows the local screen-capture disclosure before Android's MediaProjection prompt; cancellation does not start capture.
- [ ] Optional OCR download shows its GitHub/network metadata disclosure before a download begins; cancellation starts no download.
- [ ] Privacy-policy, source, GPL, and upstream-attribution links open in a system browser and safely handle a device without a browser.
- [ ] Privacy-policy and GPL/source links are ready before any public release.