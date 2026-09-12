# TapFlow Lite release checklist

This is an open-source GPL-3.0 Lite edition derived from Klick'r / Smart AutoClicker.

## Product decisions

- Keep click, swipe, scenario persistence, overlay, accessibility service, and image-detection workflows.
- Keep complex engine code in the tree until core behavior is stable.
- Keep NoOpAdProvider; it does not load a network SDK or collect data.
- Do not show advertising in automation overlays or any surface that an automation run could touch.
- Do not enable an advertising SDK, account system, cloud sync, or analytics service without a separate review.

## Before public distribution

- Publish the exact corresponding GPL source, including LICENSE, documentation/UPSTREAM.md, and this modification record.
- Use the TapFlow Lite application ID io.github.eei14wat.tapflow; do not claim compatibility or data migration from upstream installations.
- Complete the privacy policy with publisher name, contact, URL, and any future third-party SDK disclosures.
- Create a separate private release signing key; do not add it, its passwords, or local signing properties to Git.
- Run the manual regression checklist in MANUAL_BRANDING_REGRESSION_CHECKLIST.md.
- Test accessibility, overlay, MediaProjection, background execution, rotation, and image matching on supported Android versions.
- Verify that [PRIVACY.md](../PRIVACY.md) is the public policy URL and that the Settings links open it, the source repository, GPL-3.0, and upstream attribution in a browser.
- Confirm that the only active GitHub workflows are test workflows; inherited publishing workflows are retained as documentation only.
- Build an unsigned release first, then follow [RELEASE_SIGNING.md](RELEASE_SIGNING.md) for a manually signed artifact and its SHA-256 record.
- Verify the pinned NCNN source archive and checksum, or place the verified archive in the documented cache before an offline release build.
- Create and validate a signed release APK/AAB only after release authority and key custody are established.