package com.android.fitapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.fitapp.profile.ProfileFragment;
import com.android.fitapp.navHeader.NavHeaderAuthorized;
import com.android.fitapp.navHeader.NavHeaderNotAuthorized;
import com.android.fitapp.programs.ProgramsConstructor;
import com.android.fitapp.programs.ProgramsFragment;

import com.android.fitapp.settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        changeTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_programs);
        }
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        changeLoginStatus();
        switch (menuItem.getItemId()) {
            case R.id.nav_add_programs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProgramsConstructor()).commit();
                break;
            case R.id.nav_programs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProgramsFragment()).commit();
                break;
            case R.id.nav_journal:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new JournalFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment()).commit();
                break;
            case R.id.nav_progress:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProgressFragment()).commit();
                break;
            case R.id.nav_settings:
                //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_log_out:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                changeLoginStatus();
                restartApp();
                break;
            case R.id.nav_exit:
/*                Intent intent = new Intent(getApplicationContext(), Main.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
                finishAndRemoveTask();*/
                finishAffinity();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_send:
                Toast.makeText(this, "Send", Toast.LENGTH_SHORT).show();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void changeLoginStatus() {

        Fragment fragment = null;

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            fragment = new NavHeaderAuthorized();
        } else fragment = new NavHeaderNotAuthorized();

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fr_nav_header, fragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    public String readSettings() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return String.valueOf(sharedPreferences.getString(SettingsActivity.PREF_THEME, "0"));

    }

    public void changeTheme() {
        String themeName = readSettings();
        switch (themeName) {
            case "Dark":
                setTheme(R.style.DarkTheme);
                break;
            default:
                setTheme(R.style.AppTheme);
                break;
        }
    }

    public void restartApp() {
        finish();
        startActivity(getIntent());
    }

}
