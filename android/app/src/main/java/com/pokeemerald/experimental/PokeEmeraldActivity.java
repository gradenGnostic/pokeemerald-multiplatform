package com.pokeemerald.experimental;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import java.util.Arrays;

import org.libsdl.app.SDLActivity;

public class PokeEmeraldActivity extends SDLActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GbaControlsView controls = new GbaControlsView(this);
        mLayout.addView(controls, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    @Override
    public void setOrientationBis(int width, int height, boolean resizable, String hint) {
        // The manifest already keeps this activity in sensor landscape mode.
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!hasFocus) {
            return;
        }

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && mSurface != null) {
            mSurface.post(() -> {
                int width = mSurface.getWidth();
                int height = mSurface.getHeight();
                mSurface.setSystemGestureExclusionRects(Arrays.asList(
                        new Rect(0, height / 2, width / 5, height),
                        new Rect(width * 4 / 5, height / 2, width, height)));
            });
        }
    }

    @Override
    protected String[] getLibraries() {
        return new String[] { "SDL2", "main" };
    }
}
