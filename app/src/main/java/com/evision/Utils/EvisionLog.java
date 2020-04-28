package com.evision.Utils;

import android.util.Log;

public class EvisionLog {
    public static void D(String tag, String msg) {
        Log.d(tag, msg);
    }
    public static void E(String tag, String msg) {
        Log.e(tag, msg);
    }
}
