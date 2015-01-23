package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.event.LoginResultEvent;
import actuseries.android.com.actuseries.tasks.LoginTask;

public class LoginActivity extends MainMenuActionBarActivity implements View.OnClickListener {

    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        usernameEditText = (EditText) findViewById(R.id.login_editText);
        passwordEditText = (EditText) findViewById(R.id.password_editText);
        findViewById(R.id.connect_button).setOnClickListener(this);
        findViewById(R.id.signup_button).setOnClickListener(this);
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
        //TODO: masquer le bouton de deconnexion (on n'est pas encore connecté... duh !)
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.connect_button) {
            String[] params = {usernameEditText.getText().toString(), passwordEditText.getText().toString()};
            TaskManager.launchTask(LoginTask.class, params);
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

    //on reçoit le message associé à l'évènement de connexion
    @Subscribe
    public void onLoginTaskResult(LoginResultEvent event) {
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
