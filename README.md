# pokeemerald-multiplatform

An experimental Windows and Linux PC port of the [Pokémon Emerald decompilation](https://github.com/pret/pokeemerald), with early Android porting work.

The project runs the decompiled game code directly. It is not a bundled GBA emulator and does not include a commercial ROM.

## Platform Status

| Platform | Status | Output |
| --- | --- | --- |
| Windows | Working through the SDL2 backend | `pokeemerald.exe` |
| Linux | Working native 32-bit SDL2 build | `pokeemerald` |
| Android | Experimental, not currently linkable | APK pending |
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

Windows XInput controllers are also supported by the SDL2 backend. Native Linux currently uses keyboard input.

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

## Saving

Save data is read from and written to:

```text
pokeemerald.sav
```

Keep this file if you clean or move the build.

## Android Status

The experimental Android project is under `android/` and currently targets `armeabi-v7a` to preserve the required 32-bit pointer layout.

The C sources and SDL2 library cross-compile successfully. Final linking is blocked because generated game data contains fixed-address 16-bit and 32-bit relocations, while Android requires a position-independent `libmain.so`. A proper Android port must convert those embedded references into runtime-relative or otherwise position-independent representations.

No touch controls have been implemented.

## Upstream Project

This repository is based on the Pokémon Emerald decompilation. The upstream project builds the following ROM:

- `pokeemerald.gba`
- SHA-1: `f3ae088181bf583e55daf962a92bb46f4f1d07b7`

See [INSTALL.md](INSTALL.md) for the original decompilation setup and [pret.github.io](https://pret.github.io/) for other pret projects.

## Legal

Pokémon and Pokémon Emerald are trademarks of Nintendo, Creatures Inc., and GAME FREAK inc. This is an unofficial fan project and is not affiliated with or endorsed by those companies.

The scoped license in [LICENSE](LICENSE) applies only to original multiplatform-port modifications contributed through this fork. It does not relicense upstream code, third-party components, or copyrighted game assets.
