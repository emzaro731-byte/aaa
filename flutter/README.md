# AAA Mobile Studio — Flutter

This is the Flutter/Dart version of AAA Mobile Studio.

## Included

- Flutter + Dart application source
- Mobile dashboard
- Dart code editor
- Flutter project explorer
- GitHub Actions cloud APK build
- GitHub Actions cloud AAB build
- `flutter analyze` before packaging

## Build

Open GitHub Actions and run **AAA Flutter Build**. Choose `apk`, `aab`, or `both`.

The workflow generates the Android platform wrapper in CI, runs analysis, and uploads the release artifact.

## Security

Do not put GitHub tokens, Supabase service-role keys, Groq private keys, signing passwords, or other secrets inside Flutter source code. Use GitHub Secrets or a secure backend.
