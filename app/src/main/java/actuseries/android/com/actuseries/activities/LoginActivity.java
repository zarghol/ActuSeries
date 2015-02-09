package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.caller.BaseBetaSeriesCaller;
import actuseries.android.com.actuseries.betaseries.caller.BetaSeriesCaller;
import actuseries.android.com.actuseries.event.GetMemberSummaryResultEvent;
import actuseries.android.com.actuseries.event.LoginResultEvent;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.locator.BetaSeriesCallerLocator;
import actuseries.android.com.actuseries.metier.Member;
import actuseries.android.com.actuseries.tasks.GetMemberSummaryTask;
import actuseries.android.com.actuseries.tasks.LoginTask;
import actuseries.android.com.actuseries.tools.ConnectivityChecker;

public class LoginActivity extends MainMenuActionBarActivity implements View.OnClickListener {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private ProgressBar loadingProgressBar;
    private Button connectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.autoLoginFromPersistence();

        // Initialize view
        setContentView(R.layout.login_activity);
        usernameEditText = (EditText) findViewById(R.id.login_editText_login);
        passwordEditText = (EditText) findViewById(R.id.login_editText_password);
        loadingProgressBar = (ProgressBar) findViewById(R.id.login_progressBar_loading);
        connectButton = (Button) findViewById(R.id.login_button_connect);
        connectButton.setOnClickListener(this);
        findViewById(R.id.login_button_signup).setOnClickListener(this);

    }

    private void autoLoginFromPersistence() {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean stayLoggedIn = settings.getBoolean(getString(R.string.settingsKey_stayLoggedIn), false);
        String token = settings.getString(getString(R.string.settingsKey_token), null);
        if(stayLoggedIn && token != null && !token.isEmpty()) {
            BetaSeriesCallerLocator.provide(new BaseBetaSeriesCaller(token));
            TaskManager.launchTask(GetMemberSummaryTask.class, null);
        } else {
            BetaSeriesCallerLocator.provide(new BaseBetaSeriesCaller());
        }
    }

    @Override
    protected void onResume() {
        BetaSeriesCaller betaSeriesCaller = BetaSeriesCallerLocator.getService();
        if(betaSeriesCaller.isLoggedIn()) {
            this.passeAuth();
        }
        super.onResume();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //on masque les boutons de déconnexion et de recherche (on n'est pas encore connecté)
        menu.findItem(R.id.menu_logout).setVisible(false);
        menu.findItem(R.id.menu_search).setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        if(!ConnectivityChecker.connectivityAvailable(this)) {
            Toast.makeText(getApplicationContext(), "Connexion réseau impossible", Toast.LENGTH_SHORT).show();
        } else if(v.getId() == R.id.login_button_connect) {
            if(inputsOk()) {
                String[] params = {usernameEditText.getText().toString(), passwordEditText.getText().toString()};
                TaskManager.launchTask(LoginTask.class, params);
                connectButton.setVisibility(View.INVISIBLE);
                loadingProgressBar.setVisibility(View.VISIBLE);
            }
        } else {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        }
    }

    private void passeAuth() {
        Intent i = new Intent(this, SeriesListActivity.class);
        this.startActivity(i);
        this.finish();
    }

    private boolean inputsOk() {
        if(!this.usernameEditText.getText().toString().isEmpty()) {
            if(!this.passwordEditText.getText().toString().isEmpty()) {
                return true;
            } else {
                passwordEditText.setError("Veuillez saisir votre mot de passe");
                usernameEditText.setError(null);
            }
        } else {
            usernameEditText.setError("Veuillez saisir votre identifiant");
            passwordEditText.setError(null);
        }
        return false;
    }

    @Subscribe
    public void onLoginTaskResult(LoginResultEvent event) {
        loadingProgressBar.setVisibility(View.GONE);
        connectButton.setVisibility(View.VISIBLE);
        Member member = event.getResult();
        if(member != null) {
            SharedPreferences.Editor settingsEditor = PreferenceManager.getDefaultSharedPreferences(this).edit();
            settingsEditor.putString(getString(R.string.settingsKey_login), member.getLogin());
            settingsEditor.putString(getString(R.string.settingsKey_token), member.getToken());
            settingsEditor.apply();
            this.passeAuth();
        } else {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Erreur d'authentification", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Subscribe
    public void onGetMemberSummaryTaskResult(GetMemberSummaryResultEvent event) {
        loadingProgressBar.setVisibility(View.GONE);
        connectButton.setVisibility(View.VISIBLE);
        Member member = event.getResult();
        if(member != null) {
            this.passeAuth();
        } else {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Erreur d'authentification", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
