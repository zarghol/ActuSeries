package actuseries.android.com.actuseries.tasks;

import android.os.AsyncTask;
import android.util.Log;

import actuseries.android.com.actuseries.event.LogoutResultEvent;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.locator.BetaSeriesCallerLocator;

/**
 * Created by Clement on 23/01/2015.
 */
public class LogoutTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {
        BetaSeriesCallerLocator.getService().memberLogout();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Log.d("actuseries", "onPostExecute");
        TaskManager.post(new LogoutResultEvent());
    }
}
