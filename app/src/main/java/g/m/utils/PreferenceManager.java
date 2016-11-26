package g.m.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by kushroxx on 26/11/16.
 */


public class PreferenceManager {
    public static final String PREF_COINS_COUNT = "coins_count";
    public static final String PREF_CORRECT_COUNT = "correct_count";
    public static final String PREF_CURRENT_LEVEL = "current_level";
    public static final String PREF_DAILY_REWARD = "daily_reward";
    public static final String PREF_FIRST_LAUNCH = "first_launch";
    public static final String PREF_LAUNCH_COUNT = "launch_count";
    public static final String PREF_LEVEL_DATA = "level_data";
    public static final String PREF_QUESTIONS_ORDER = "questions_order";
    public static final String PREF_RATE_ENABLED = "rate_enabled";
    public static final String PREF_SOUND_ENABLED = "sound_enabled";
    public static final String PREF_ALREADY_CACHED = "cached";
    private static PreferenceManager instance;
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;

    private PreferenceManager() {
    }

    public static PreferenceManager get() {
        if (instance == null) {
            instance = new PreferenceManager();
        }
        return instance;
    }

    public void init(Context context) {
        this.preferences = context.getSharedPreferences(context.getPackageName(), 0);
        this.editor = this.preferences.edit();
    }

    public void putInt(String key, int value) {
        this.editor.putInt(key, value);
        this.editor.commit();
    }

    public int getInt(String key, int defaultValue) {
        return this.preferences.getInt(key, defaultValue);
    }

    public void putString(String key, String value) {
        this.editor.putString(key, value);
        this.editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return this.preferences.getString(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        this.editor.putBoolean(key, value);
        this.editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return this.preferences.getBoolean(key, defaultValue);
    }

    public void putLong(String key, long value) {
        this.editor.putLong(key, value);
        this.editor.commit();
    }

    public long getLong(String key, long defaultValue) {
        return this.preferences.getLong(key, defaultValue);
    }
}
