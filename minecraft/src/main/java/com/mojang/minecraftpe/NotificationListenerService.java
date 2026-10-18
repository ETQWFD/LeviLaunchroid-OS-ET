package com.mojang.minecraftpe;

import android.content.Context;

public class NotificationListenerService {
    private static final String DEFAULT_SENDER_ID = "486187589451";
    private static volatile Context applicationContext;
    private static volatile boolean nativeReady;
    private static volatile String firebaseSenderId = DEFAULT_SENDER_ID;

    public native void nativePushNotificationReceived(int type, String title, String body, String payload);

    public static void initialize(Context context) {
        if (context != null) {
            applicationContext = context.getApplicationContext();
        }
    }

    public static void initialize(
            Context context,
            String appId,
            String apiKey,
            String projectId,
            String senderId
    ) {
        if (context != null) {
            applicationContext = context.getApplicationContext();
        }
        if (senderId != null && !senderId.trim().isEmpty()) {
            firebaseSenderId = senderId.trim();
        }
    }

    public static String getDeviceRegistrationToken() {
        return "";
    }

    public static void requestDeviceRegistrationToken(TokenCallback callback) {
        if (callback != null) {
            callback.onToken("");
        }
    }

    public static void setNativeReady(boolean ready) {
        nativeReady = ready;
    }

    public static void onMinecraftForeground(Context context) {
        if (context != null) {
            applicationContext = context.getApplicationContext();
        }
        nativeReady = true;
    }

    public static void refreshDeviceRegistrationToken(Context context) {
        if (context != null) {
            applicationContext = context.getApplicationContext();
        }
    }

    public interface TokenCallback {
        void onToken(String token);
    }
}
