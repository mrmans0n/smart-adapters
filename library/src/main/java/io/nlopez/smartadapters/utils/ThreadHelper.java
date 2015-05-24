package io.nlopez.smartadapters.utils;

import android.os.Looper;

import io.nlopez.smartadapters.BuildConfig;

/**
 * Small class to fail first check if we are executing stuff in an unwanted thread
 */
public class ThreadHelper {

    /**
     * Provoke an exception if we are in the main thread
     */
    public static void crashIfMainThread() {
        if (BuildConfig.DEBUG) {
            if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
                throw new IllegalStateException("This method should NOT be called from the Main Thread");
            }
        }
    }

    /**
     * Provoke an exception if we are in a background thread
     */
    public static void crashIfBackgroundThread() {
        if (BuildConfig.DEBUG) {
            if (Thread.currentThread() != Looper.getMainLooper().getThread()) {
                throw new IllegalStateException("This method should be called from the Main Thread");
            }
        }
    }
}
