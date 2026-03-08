package com.leocodes.relikd.util;

import javafx.scene.Scene;

public class ThemeManager {

    private static final ThemeManager instance = new ThemeManager();

    private static final String BASE = resource("/css/base.css");
    private static final String DARK = resource("/css/dark.css");
    private static final String LIGHT = resource("/css/light.css");

    private boolean darkMode = true;
    private Scene activeScene;
    private Runnable onThemeChanged;

    private ThemeManager() {}

    public static ThemeManager get() {
        return instance;
    }

    public void bind(Scene scene) {
        this.activeScene = scene;
        applyTheme();
    }

    public void toggleTheme() {
        darkMode = !darkMode;
        applyTheme();
        if (onThemeChanged != null) onThemeChanged.run();
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public String getToggleIcon() {
        return darkMode ? "\u2600" : "\u263E";
    }

    public void setOnThemeChanged(Runnable callback) {
        this.onThemeChanged = callback;
    }

    private void applyTheme() {
        if (activeScene == null) return;

        var sheets = activeScene.getStylesheets();
        sheets.clear();
        sheets.add(BASE);
        sheets.add(darkMode ? DARK : LIGHT);
    }

    private static String resource(String path) {
        return ThemeManager.class.getResource(path).toExternalForm();
    }
}
