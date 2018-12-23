package com.android.fitapp.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.android.fitapp.R;

public class SettingsFragment extends PreferenceFragment {

    public static final String PREF_THEME = "pref_theme";

    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(PREF_THEME)){
                    Preference themePref = findPreference(key);
                    themePref.setSummary(sharedPreferences.getString(key, ""));
                    ((SettingsActivity)getActivity()).restartApp();

                }
            }
        };
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        Preference themePref = findPreference(PREF_THEME);
        themePref.setSummary(getPreferenceScreen().getSharedPreferences().getString(PREF_THEME, ""));
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
}
