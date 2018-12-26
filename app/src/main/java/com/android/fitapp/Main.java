package com.android.fitapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.widget.Toast;
import com.android.fitapp.journal.JournalFragment;
import com.android.fitapp.profile.ProfileFragment;
import com.android.fitapp.navHeader.NavHeaderAuthorized;
import com.android.fitapp.navHeader.NavHeaderNotAuthorized;
import com.android.fitapp.programs.CreateProgramFragment;
import com.android.fitapp.programs.ProgramsFragment;
import com.android.fitapp.settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import java.io.IOException;


public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    public static Context contextOfApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        changeTheme();
        super.onCreate(savedInstanceState);
        contextOfApplication = getApplicationContext();
        changeTheme();
        setContentView(R.layout.activity_main);
        if (isInternetConnected()) {
            drawer = findViewById(R.id.drawer_layout);
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProgramsFragment()).commit();
                navigationView.setCheckedItem(R.id.nav_programs);

            }
        } else {
            Toast.makeText(contextOfApplication, "Internet connection is not available!", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        changeLoginStatus();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(!isInternetConnected()) {
            Toast.makeText(contextOfApplication, "Internet connection is not available!", Toast.LENGTH_SHORT).show();
        }

        changeLoginStatus();
        switch (menuItem.getItemId()) {
            case R.id.nav_add_programs:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CreateProgramFragment()).commit();
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
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new ProgressFragment()).commit();
                break;
            case R.id.nav_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_log_out:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                changeLoginStatus();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProgramsFragment()).commit();
                break;
            case R.id.nav_exit:
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
        changeLoginStatus();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!autoLogin()) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                FirebaseAuth.getInstance().signOut();
            }
        }
    }

    private boolean autoLogin(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return Boolean.valueOf(sharedPreferences.getBoolean(SettingsActivity.PREF_AUTOLOGIN, false));
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

    public static boolean isInternetConnected(){
        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process  mIpAddrProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int mExitValue = mIpAddrProcess.waitFor();
            System.out.println(" mExitValue "+mExitValue);
            if(mExitValue==0){
                return true;
            }else{
                return false;
            }
        }
        catch (InterruptedException ignore)
        {
            ignore.printStackTrace();
            System.out.println(" Exception:"+ignore);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println(" Exception:"+e);
        }
        return false;
    }

    public static int themeAttributeToColor(int themeAttributeId,
                                            Context context,
                                            int fallbackColorId) {
        TypedValue outValue = new TypedValue();
        Resources.Theme theme = context.getTheme();
        boolean wasResolved =
                theme.resolveAttribute(
                        themeAttributeId, outValue, true);
        if (wasResolved) {
            return ContextCompat.getColor(
                    context, outValue.resourceId);
        } else {
            return fallbackColorId;
        }
    }
}
