package com.example.data.javatest;

import android.util.Log;

import com.example.data.BuildConfig;
import com.example.data.reachability.ReachAbilityService;
import com.example.data.reachability.ReachAbilityServiceAdapter;

import static com.example.data.javatest.JavaInjectionExtensionsKt.inject;

public class CustomLog  {

    private ReachAbilityServiceAdapter serviceAdapter = inject(ReachAbilityServiceAdapter.class);

    public void wtf(String tag, Throwable throwable) {
        Log.wtf(tag, throwable);
    }

    public void i(String tag, String message) {
        i(tag, message, null);
    }

    public void i(String tag, String message, Throwable exception) {
        if (exception == null) {
            Log.i(tag, message);
        } else {
            Log.i(tag, message, exception);
        }
    }

    public void d(String tag, String message) {
        ReachAbilityService exampleVar = serviceAdapter.createReachAbilityService();

        d(tag, message, null);
    }

    public void d(String tag, String message, Throwable exception) {
        if (BuildConfig.DEBUG) {
            if (exception == null) {
                Log.d(tag, message);
            } else {
                Log.d(tag, message, exception);
            }
        }
    }

    public void w(String tag, String message) {
        w(tag, message, null);
    }

    public void w(String tag, String message, Throwable exception) {
        if (BuildConfig.DEBUG) {
            if (exception == null) {
                Log.w(tag, message);
            } else {
                Log.w(tag, message, exception);
            }
        }
    }

    public void e(String tag, String message) {
        e(tag, message, null);
    }

    public void e(String tag, String message, Throwable exception) {
        if (BuildConfig.DEBUG) {
            if (exception == null) {
                Log.e(tag, message);
            } else {
                Log.e(tag, message, exception);
            }
        }
    }
}
