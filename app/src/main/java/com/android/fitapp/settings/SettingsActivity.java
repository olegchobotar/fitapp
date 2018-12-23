package com.android.fitapp.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.widget.Switch;
import android.widget.Toast;

import com.android.fitapp.Main;
import com.android.fitapp.R;

public class SettingsActivity extends Activity {
    public static final String PREF_THEME = "pref_theme";
    private String themeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        changeTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        if (findViewById(R.id.settings_container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            getFragmentManager().beginTransaction().add(R.id.settings_container, new SettingsFragment()).commit();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(SettingsActivity.this, Main.class);
        startActivityForResult(intent, 2);
    }

    public void restartApp() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
        finish();
    }

    public String readSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return String.valueOf(sharedPreferences.getString(PREF_THEME, "0"));

    }

    public void changeTheme() {
        themeName = readSettings();
        switch (themeName) {
            case "Dark":
                setTheme(R.style.DarkTheme);
                break;
            default:
                setTheme(R.style.AppTheme);
                break;
        }
    }
}
