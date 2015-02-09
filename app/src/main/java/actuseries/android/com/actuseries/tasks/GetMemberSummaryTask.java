package actuseries.android.com.actuseries.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import actuseries.android.com.actuseries.activities.fragment.SeriesDisplay;
import actuseries.android.com.actuseries.event.GetMemberSummaryResultEvent;
import actuseries.android.com.actuseries.event.GetSerieResultEvent;
import actuseries.android.com.actuseries.event.LoginResultEvent;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.locator.BetaSeriesCallerLocator;
import actuseries.android.com.actuseries.metier.Member;
import actuseries.android.com.actuseries.metier.Serie;

/**
 * Created by Thomas on 09/02/2015.
 */
public class GetMemberSummaryTask extends AsyncTask<Void, Void, Member> {
    @Override
    protected Member doInBackground(Void... params) {
        // Debug the task thread name
        Log.d("actuseries", Thread.currentThread().getName() + " récupérations des infos");

        return BetaSeriesCallerLocator.getService().getMemberSummary();
    }

    @Override
    protected void onPostExecute(Member member) {
        TaskManager.post(new GetMemberSummaryResultEvent(member));
    }
}
