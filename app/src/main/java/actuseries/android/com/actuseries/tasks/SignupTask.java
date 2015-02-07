package actuseries.android.com.actuseries.tasks;

import android.os.AsyncTask;

import actuseries.android.com.actuseries.event.LoginResultEvent;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.locator.BetaSeriesCallerLocator;
import actuseries.android.com.actuseries.metier.Member;

/**
 * Created by Clement on 20/01/2015.
 */
public class SignupTask extends AsyncTask<String, Void, Member> {

    @Override
    protected Member doInBackground(String... params) {
        String login = params[0];
        String password = params[1];
        String email = params[2];
        return BetaSeriesCallerLocator.getService().accountCreation(login, password, email);

    }

    @Override
    protected void onPostExecute(Member member) {
        //on poste un évènement dans le bus d'évènement qui indique la connexion réussie
        TaskManager.post(new LoginResultEvent(member != null));
    }
}