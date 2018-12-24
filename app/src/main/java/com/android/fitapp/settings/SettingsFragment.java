package com.android.fitapp.settings;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.android.fitapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsFragment extends PreferenceFragment {


    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(SettingsActivity.PREF_THEME)) {
                    Preference themePref = findPreference(key);
                    themePref.setSummary(sharedPreferences.getString(key, ""));
                    ((SettingsActivity) getActivity()).restartApp();
                }
                if (key.equals(SettingsActivity.PREF_CHANGE_EMAIL)) {
                    if (mAuth.getCurrentUser() != null) {

                        EditTextPreference changeEmailPref = (EditTextPreference) findPreference(SettingsActivity.PREF_CHANGE_EMAIL);
                        String emailValue = changeEmailPref.getText();
                        if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
                            Toast.makeText(getActivity(), "Enter a valid email address", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            updateEmailAddress(emailValue);
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "You are not authorized", Toast.LENGTH_LONG).show();
                    }
                }
                if (key.equals(SettingsActivity.PREF_CHANGE_PASSWORD)) {
                    if (mAuth.getCurrentUser() != null) {
                        EditTextPreference changePassPref = (EditTextPreference) findPreference(SettingsActivity.PREF_CHANGE_PASSWORD);
                        String passValue = changePassPref.getText();
                        if (TextUtils.isEmpty(passValue)) {
                            Toast.makeText(getActivity(), "Enter a valid password", Toast.LENGTH_LONG).show();
                            return;
                        } else {
                            updatePass(passValue);
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "You are not authorized", Toast.LENGTH_LONG).show();
                    }
                }
            }
        };
    }

    private void updateEmailAddress(final String emailAddress) {
        FirebaseUser user = mAuth.getCurrentUser();
        final String uid = user.getUid();
        user.updateEmail(emailAddress).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    databaseReference.child("Users").child(uid).child("email").setValue(emailAddress);
                    Toast.makeText(getActivity(), "Email address was changed.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void updatePass(String password){
        FirebaseUser user = mAuth.getCurrentUser();
        final String uid = user.getUid();
        user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getActivity(), "Password was changed.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), "Error" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(preferenceChangeListener);
        Preference themePref = findPreference(SettingsActivity.PREF_THEME);
        themePref.setSummary(getPreferenceScreen().getSharedPreferences().getString(SettingsActivity.PREF_THEME, ""));
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
}
