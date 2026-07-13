# pokeemerald-multiplatform

An experimental Windows, Linux, and Android port of the [Pokemon Emerald decompilation](https://github.com/pret/pokeemerald).

The project runs the decompiled game code directly. It is not a bundled GBA emulator and does not include a commercial ROM.

## Platform Status

| Platform | Status | Output |
| --- | --- | --- |
| Windows | Working through the SDL2 backend | `pokeemerald.exe` |
| Linux | Working native 32-bit SDL2 build | `pokeemerald` |
| Android | Working experimental ARMv7 SDL2 build | `android/app/build/outputs/apk/debug/app-debug.apk` |
| GBA ROM | Upstream target | `pokeemerald.gba` |

## Port Changes

- Repaired the portable MP2K/M4A music player and sound mixer build.
- Added SDL2 float audio output at 42060 Hz.
- Sanitized invalid floating-point samples independently in the M4A and CGB audio paths, eliminating loud buzzing without discarding valid audio.
- Added output headroom and clipping protection.
- Fixed structure and pointer conversions required by the portable audio engine.
- Fixed portable BIOS, DMA, flash-save, trainer-card, and sound-related compilation errors.
- Added working save-file access through `pokeemerald.sav`.
- Added a Wine launcher for the Windows build.
- Added native 32-bit Linux compilation and SDL2 linkage.
- Added aspect-ratio-preserving 3:2 rendering, centered letterboxing, integer pixel scaling, and black borders.
- Added an experimental Android SDL2/Gradle project and an ARMv7 cross-compilation pipeline.
- Added Android rendering, frame pacing, audio output, writable save storage, and lifecycle handling.
- Added an Android-native labeled multitouch overlay and SDL game-controller input.
- Added launcher icons on Android and an embedded multi-resolution icon on Windows.

## Controls

| GBA control | Keyboard |
| --- | --- |
| A | `Z` |
| B | `X` |
| Start | `Enter` |
| Select | `Backslash` |
| L | `A` |
| R | `S` |
| D-pad | Arrow keys |
| Fast-forward | `Space` |
| Pause | `Ctrl+P` |
| Soft reset | `Ctrl+R` |

Windows XInput controllers are supported by the SDL2 backend. Android supports SDL-compatible gamepads, including D-pad and left analog-stick movement. Native Linux currently uses keyboard input.

## Windows Build

The Windows target uses the 32-bit MinGW toolchain and SDL2:

```sh
make -f Makefile_pc -j4
```

Place `SDL2.dll` beside `pokeemerald.exe`. On Linux, the Windows build can be launched through Wine with:

```sh
./launch.sh
```

## Linux Build

The game data contains 32-bit pointers, so the native Linux target must currently be built as a 32-bit executable. Install a multilib C toolchain and 32-bit SDL2 development files, then run:

```sh
make -f Makefile_pc linux -j4
./pokeemerald
```

Linux objects are kept separately under `build/linux`, so they do not interfere with the Windows build.

The resulting executable is `pokeemerald` in the repository root.

## Saving

Save data is read from and written to:

```text
pokeemerald.sav
```

Keep this file if you clean or move the build.

## Android Build

The Android project targets API 36 and `armeabi-v7a`. The 32-bit ABI is required by the game's current pointer layout. Android SDK 36, NDK `26.3.11579264`, CMake 3.22.1, and a compatible JDK are required.

Initialize SDL2 and apply the Android lifecycle patch once after cloning:

```sh
git submodule update --init --recursive
git -C android/SDL2 apply ../patches/sdl2-android-lifecycle.patch
```

Set `JAVA_HOME` and `ANDROID_HOME`, then build with SDL2's Gradle wrapper:

```sh
android/SDL2/android-project/gradlew -p android :app:assembleDebug
```

Install the debug APK on a connected device with:

```sh
adb install -r android/app/build/outputs/apk/debug/app-debug.apk
```

Android saves are stored in the app's writable private storage. Windows and Linux continue to use `pokeemerald.sav` in the working directory.

Android includes an always-visible, labeled multitouch overlay for the D-pad, A, B, Start, Select, L, and R. The controls occupy the widescreen side borders rather than covering the 3:2 gameplay viewport. Multiple buttons can be held simultaneously.

## Upstream Project

This repository is based on the Pokémon Emerald decompilation. The upstream project builds the following ROM:

- `pokeemerald.gba`
- SHA-1: `f3ae088181bf583e55daf962a92bb46f4f1d07b7`

See [INSTALL.md](INSTALL.md) for the original decompilation setup and [pret.github.io](https://pret.github.io/) for other pret projects.

## Legal

Pokémon and Pokémon Emerald are trademarks of Nintendo, Creatures Inc., and GAME FREAK inc. This is an unofficial fan project and is not affiliated with or endorsed by those companies.

The scoped license in [LICENSE](LICENSE) applies only to original multiplatform-port modifications contributed through this fork. It does not relicense upstream code, third-party components, or copyrighted game assets.
