package actuseries.android.com.actuseries.tasks;

import android.os.AsyncTask;

import actuseries.android.com.actuseries.betaseries.AccesBetaseries;

/**
 * Created by Clement on 23/01/2015.
 */
public class LogoutTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        AccesBetaseries.deconnexionMembre();
        return null;
    }
}
