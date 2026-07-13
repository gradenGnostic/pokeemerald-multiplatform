package com.pokeemerald.experimental;

import org.libsdl.app.SDLActivity;

public class PokeEmeraldActivity extends SDLActivity {
    @Override
    protected String[] getLibraries() {
        return new String[] { "SDL2", "main" };
    }
}
