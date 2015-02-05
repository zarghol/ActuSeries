package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.event.LoginResultEvent;
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
        setContentView(R.layout.login_activity);
        usernameEditText = (EditText) findViewById(R.id.login_editText_login);
        passwordEditText = (EditText) findViewById(R.id.login_editText_password);
        loadingProgressBar = (ProgressBar) findViewById(R.id.login_progressBar_loading);
        connectButton = (Button) findViewById(R.id.login_button_connect);
        connectButton.setOnClickListener(this);
        findViewById(R.id.login_button_signup).setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        if(AccesBetaseries.estConnecte()) {
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
        if (!ConnectivityChecker.connectivityAvailable(this)) {
            Toast.makeText(getApplicationContext(), "Connexion réseau impossible", Toast.LENGTH_SHORT).show();
        }else if(v.getId() == R.id.login_button_connect) {
            String[] params = {usernameEditText.getText().toString(), passwordEditText.getText().toString()};
            TaskManager.launchTask(LoginTask.class, params);
            connectButton.setVisibility(View.INVISIBLE);
            loadingProgressBar.setVisibility(View.VISIBLE);

        } else {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        }
    }

    public void passeAuth() {
        Intent i = new Intent(this, SeriesListActivity.class);
        startActivity(i);
        this.finish();
    }

    @Subscribe
    public void onLoginTaskResult(LoginResultEvent event) {
        loadingProgressBar.setVisibility(View.GONE);
        connectButton.setVisibility(View.VISIBLE);
        if(event.getResult()) {
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
