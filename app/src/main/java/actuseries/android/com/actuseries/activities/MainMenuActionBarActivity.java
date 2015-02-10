package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.locator.BetaSeriesCallerLocator;

/**
 *
 */
public abstract class MainMenuActionBarActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TaskManager.register(this);
    }

    @Override
    protected void onDestroy() {
        TaskManager.unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch(id) {
            case R.id.menu_settings:
                this.actionOpenSettings();
                return true;

            case R.id.menu_logout:
                this.actionLogout();
                return true;

            case R.id.menu_search:
                this.actionSearch();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void actionOpenSettings() {
        Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsActivityIntent);
    }

    private void actionLogout() {
        SharedPreferences.Editor settingsEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        settingsEditor.remove(getString(R.string.settingsKey_login));
        settingsEditor.remove(getString(R.string.settingsKey_token));
        settingsEditor.apply();
        new Thread(new Runnable() {
            @Override
            public void run() {
                TaskManager.cancelAllTasks();
                BetaSeriesCallerLocator.getService().memberLogout();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Intent j = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(j);
                        finish();
                    }
                });
            }
        }).start();
    }

    private void actionSearch() {
        Intent j = new Intent(getApplicationContext(), SearchActivity.class);
        startActivity(j);
    }
}
