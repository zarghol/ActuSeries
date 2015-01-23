package actuseries.android.com.actuseries.activities;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import actuseries.android.com.actuseries.activities.fragment.SettingsFragment;

public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
              .replace(android.R.id.content, new SettingsFragment())
              .commit();
    }
}
