package org.levimc.launcher.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;

public class PlayStoreValidator {
    private static String mcPackage(Context context){ return org.levimc.launcher.core.minecraft.MinecraftLauncher.getTargetPackageName(context); }
    private static final String PLAY_STORE_INSTALLER = "com.android.vending";

    public static boolean isMinecraftFromPlayStore(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            try {
                packageManager.getPackageInfo(mcPackage(context), 0);
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }

            String installerPackageName;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                try {
                    installerPackageName = packageManager.getInstallSourceInfo(mcPackage(context))
                            .getInstallingPackageName();
                } catch (PackageManager.NameNotFoundException e) {
                    return false;
                }
            } else {
                installerPackageName = packageManager.getInstallerPackageName(mcPackage(context));
            }

            return PLAY_STORE_INSTALLER.equals(installerPackageName);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isMinecraftInstalled(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            packageManager.getPackageInfo(mcPackage(context), 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static boolean isLicenseVerified(Context context) {
        return isMinecraftFromPlayStore(context);
    }

}