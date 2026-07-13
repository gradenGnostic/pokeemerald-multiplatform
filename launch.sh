#!/usr/bin/env bash
set -e
cd "$(dirname "$0")"
export SDL_AUDIODRIVER=pulseaudio
export WINEDLLOVERRIDES="dsound=n"
exec wine pokeemerald.exe "$@"
