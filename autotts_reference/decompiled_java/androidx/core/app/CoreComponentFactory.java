/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Service
 *  android.content.BroadcastReceiver
 *  android.content.ContentProvider
 *  android.content.Intent
 */
package androidx.core.app;

import android.app.Activity;
import android.app.AppComponentFactory;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Intent;

public class CoreComponentFactory
extends AppComponentFactory {
    public static Object a(Object object) {
        return object;
    }

    public Activity instantiateActivity(ClassLoader classLoader, String string, Intent intent) {
        return (Activity)CoreComponentFactory.a(super.instantiateActivity(classLoader, string, intent));
    }

    public Application instantiateApplication(ClassLoader classLoader, String string) {
        return (Application)CoreComponentFactory.a(super.instantiateApplication(classLoader, string));
    }

    public ContentProvider instantiateProvider(ClassLoader classLoader, String string) {
        return (ContentProvider)CoreComponentFactory.a(super.instantiateProvider(classLoader, string));
    }

    public BroadcastReceiver instantiateReceiver(ClassLoader classLoader, String string, Intent intent) {
        return (BroadcastReceiver)CoreComponentFactory.a(super.instantiateReceiver(classLoader, string, intent));
    }

    public Service instantiateService(ClassLoader classLoader, String string, Intent intent) {
        return (Service)CoreComponentFactory.a(super.instantiateService(classLoader, string, intent));
    }
}

