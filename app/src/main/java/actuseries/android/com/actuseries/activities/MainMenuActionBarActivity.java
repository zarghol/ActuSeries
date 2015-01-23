package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;

/**
 *
 */
public abstract class MainMenuActionBarActivity extends ActionBarActivity {

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

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void actionOpenSettings() {
        Intent settingsActivityIntent = new Intent(this, SettingsActivity.class);
        startActivity(settingsActivityIntent);
    }

    private void actionLogout() {
        //FIXME: utiliser une AsyncTask !!
        new Thread(new Runnable() {
            @Override
            public void run() {
                AccesBetaseries.deconnexionMembre();

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
}
