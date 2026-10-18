package org.levimc.launcher.core.minecraft

import android.os.Build

object LauncherAbis {
    @JvmStatic
    fun hasArm64(): Boolean {
        val abis = Build.SUPPORTED_ABIS ?: return false
        return abis.any { it.equals("arm64-v8a", ignoreCase = true) }
    }

    @JvmStatic
    fun is32BitOnly(): Boolean = !hasArm64()
}
