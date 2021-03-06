package actuseries.android.com.actuseries.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.event.GetSerieResultEvent;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.locator.BetaSeriesCallerLocator;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by charly on 14/01/2015.
 */
public class GetSeriesTask extends AsyncTask<Void, Serie, Void> {
    @Override
    protected Void doInBackground(Void... params) {
        // Debug the task thread name
        Log.d("actuseries", Thread.currentThread().getName() + " récupérations des infos");

        List<Serie> series = BetaSeriesCallerLocator.getService().getSeries(SeriesDisplay.ALL);

        if (series.size() == 0) {
            series = BetaSeriesCallerLocator.getService().retrieveSeries();
        }

        for(Serie s : series) {
            if(isCancelled()) {
                break;
            }

            if(s.getBanner() == null) {
                BetaSeriesCallerLocator.getService().getSerieWithBanner(s);
            }

            this.publishProgress(s);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Serie... values) {
        super.onProgressUpdate(values);
        TaskManager.post(new GetSerieResultEvent(values[0]));
    }
}
