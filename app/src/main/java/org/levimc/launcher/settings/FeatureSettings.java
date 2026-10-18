package org.levimc.launcher.settings;

import android.content.Context;

public class FeatureSettings {
    private static volatile FeatureSettings INSTANCE;
    private static Context appContext;
    private boolean versionIsolationEnabled = false;
    private boolean launcherManagedMcLoginEnabled = false;
    private boolean logcatOverlayEnabled = false;
    private boolean foregroundServiceEnabled = false;
    private Boolean crashUploadEnabled = true;
    private String mcPackageName = null;
    private String curseforgeApiKey = null;

    public static final String DEFAULT_MC_PACKAGE_NAME = "com.mojang.minecraftpe";

    public enum StorageType {
        INTERNAL,
        EXTERNAL,
        VERSION_ISOLATION,
        VERSION_ISOLATION_INTERNAL,
        VERSION_ISOLATION_EXTERNAL
    }

    public static void init(Context context) {
        appContext = context.getApplicationContext();
    }

    public static FeatureSettings getInstance() {
        if (INSTANCE == null) {
            synchronized (FeatureSettings.class) {
                if (INSTANCE == null) {
                    INSTANCE = SettingsStorage.load(appContext);
                    if (INSTANCE == null) {
                        INSTANCE = new FeatureSettings();
                    }
                }
            }
        }
        return INSTANCE;
    }

    public boolean isVersionIsolationEnabled() { return versionIsolationEnabled; }
    public void setVersionIsolationEnabled(boolean enabled) { this.versionIsolationEnabled = enabled; autoSave(); }

    public boolean isLauncherManagedMcLoginEnabled() { return launcherManagedMcLoginEnabled; }
    public void setLauncherManagedMcLoginEnabled(boolean enabled) { this.launcherManagedMcLoginEnabled = enabled; autoSave(); }

    public boolean isLogcatOverlayEnabled() { return logcatOverlayEnabled; }
    public void setLogcatOverlayEnabled(boolean enabled) { this.logcatOverlayEnabled = enabled; autoSave(); }

    public boolean isForegroundServiceEnabled() { return foregroundServiceEnabled; }
    public void setForegroundServiceEnabled(boolean enabled) { this.foregroundServiceEnabled = enabled; autoSave(); }

    public boolean isCrashUploadEnabled() { return crashUploadEnabled == null || crashUploadEnabled; }
    public void setCrashUploadEnabled(boolean enabled) { this.crashUploadEnabled = enabled; autoSave(); }

    public String getRawMcPackageName() { return mcPackageName; }

    public String getEffectiveMcPackageName() {
        if (mcPackageName == null) return DEFAULT_MC_PACKAGE_NAME;
        String trimmed = mcPackageName.trim();
        return trimmed.isEmpty() ? DEFAULT_MC_PACKAGE_NAME : trimmed;
    }

    public boolean isCustomMcPackage() {
        return mcPackageName != null && !mcPackageName.trim().isEmpty()
                && !DEFAULT_MC_PACKAGE_NAME.equals(mcPackageName.trim());
    }

    public void setMcPackageName(String packageName) {
        if (packageName == null) {
            this.mcPackageName = null;
        } else {
            String trimmed = packageName.trim();
            this.mcPackageName = trimmed.isEmpty() || DEFAULT_MC_PACKAGE_NAME.equals(trimmed) ? null : trimmed;
        }
        autoSave();
    }

    public void resetMcPackageName() {
        this.mcPackageName = null;
        autoSave();
    }

    public String getCurseforgeApiKey() {
        return curseforgeApiKey == null ? "" : curseforgeApiKey.trim();
    }

    public void setCurseforgeApiKey(String key) {
        this.curseforgeApiKey = key == null ? null : key.trim();
        autoSave();
    }


    private void autoSave() {
        if (appContext != null) {
            SettingsStorage.save(appContext, this);
        }
    }
}
