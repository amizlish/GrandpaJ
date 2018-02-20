/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.elna.grandpaj;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * @author arash
 */
@SuppressWarnings("WeakerAccess")
public class Prefs {
    private static volatile Prefs mSingleton = null;
    private SharedPreferences mPrefs = null;

    private static final String PREFERENCES_FILE_NAME = "PrayerBookPreferences";
    private static final String PREFERENCE_DATABASE_VERSION = "DatabaseVersion";
    private static final String PREFERENCE_BOOK_TEXT_SCALAR = "PrayerTextScalar";
    private static final String PREFERENCE_USE_CLASSIC_THEME = "UseClassicTheme";

    private Prefs(Context ctx) {
        mPrefs = ctx.getSharedPreferences(PREFERENCES_FILE_NAME, Context.MODE_PRIVATE);
    }

    public static Prefs get(Application app) {
        if (mSingleton == null) {
            synchronized (Prefs.class) {
                if (mSingleton == null) {
                    mSingleton = new Prefs(app.getApplicationContext());
                }
            }
        }

        return mSingleton;
    }

    public int getDatabaseVersion() {
        return mPrefs.getInt(PREFERENCE_DATABASE_VERSION, 1);
    }


    public void setDatabaseVersion(int version) {
        mPrefs.edit().putInt(PREFERENCE_DATABASE_VERSION, version).apply();
    }


    public float getBookTextScalar() {
        return mPrefs.getFloat(PREFERENCE_BOOK_TEXT_SCALAR, 1.0f);
    }

    public void setPrayerTextScalar(float scalar) {
        mPrefs.edit().putFloat(PREFERENCE_BOOK_TEXT_SCALAR, scalar).apply();
    }

    public boolean useClassicTheme() {
        return mPrefs.getBoolean(PREFERENCE_USE_CLASSIC_THEME, false);
    }

    public void setUseClassicTheme(boolean useClassicTheme) {
        mPrefs.edit().putBoolean(PREFERENCE_USE_CLASSIC_THEME, useClassicTheme).apply();
    }
}
