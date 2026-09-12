# TapFlow Lite v0.1.0 manual release procedure

## Unsigned verification build

Use Android Studio's bundled JDK and run:

```powershell
$env:JAVA_HOME = 'C:\Program Files\Android\Android Studio\jbr'
.\.codex-gradle\gradle-9.5.1\bin\gradle.bat :smartautoclicker:assembleFDroidRelease --no-daemon --no-parallel -Dkotlin.compiler.execution.strategy=in-process --console=plain
```

Without explicit signing properties this produces an unsigned F-Droid Release. It never falls back to the Android debug key.

## NCNN verified cache

The Release build requires Tencent/ncnn version `20260113` from:

`https://github.com/Tencent/ncnn/releases/download/20260113/ncnn-20260113-full-source.zip`

Expected SHA-256:

`53696039ee8ba5c8db6446bdf12a576b8d7f7b0c33bb6749f94688bddf5a3d5c`

The project cache path is:

`core/smart/detection/build/intermediates/sourceDownload/downloadZip/ncnn-20260113-full-source.zip`

If the official URL is unavailable, manually obtain that exact official asset, verify its SHA-256 locally, and place it at the path above before running Gradle with `--offline`. Do not use an unofficial mirror or commit the archive.

## OpenCV verified cache

The Release build requires OpenCV version `4.12.0` from:

`https://github.com/opencv/opencv/archive/refs/tags/4.12.0.zip`

Required filename:

`opencv-4.12.0.zip`

Expected SHA-256:

`fa3faf7581f1fa943c9e670cf57dd6ba1c5b4178f363a188a2c8bff1eb28b7e4`

The project cache path is:

`core/smart/detection/build/intermediates/sourceDownload/downloadZip/opencv-4.12.0.zip`

Verify a manually downloaded archive before copying it into the project cache:

```powershell
Get-FileHash -Algorithm SHA256 'C:\path\to\opencv-4.12.0.zip'
```

The build validates the ZIP and SHA-256 before reusing this cache. In `--offline` mode, it fails with the required cache path when the verified archive is unavailable. Do not use an unofficial mirror or commit the archive.

## Later local signing

Keep the keystore outside this repository. Only a deliberate local environment/Gradle-property configuration may enable signing:

- `releaseSigningEnabled=true`
- `signingStoreFile` — absolute external keystore path
- `signingStorePassword`
- `signingKeyAlias`
- `signingKeyPassword`

After a signed build, verify it with the Android SDK `apksigner verify --verbose --print-certs <apk>`. Never commit a keystore, signing properties, passwords, or certificates.