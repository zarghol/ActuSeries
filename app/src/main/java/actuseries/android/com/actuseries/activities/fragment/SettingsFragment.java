package actuseries.android.com.actuseries.activities.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import actuseries.android.com.actuseries.R;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
