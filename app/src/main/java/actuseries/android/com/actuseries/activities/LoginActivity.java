package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;
import actuseries.android.com.actuseries.event.EventBus;
import actuseries.android.com.actuseries.event.LoginResultEvent;
import actuseries.android.com.actuseries.tasks.LoginTask;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

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
        //on s'abonne au bus d'évènements
        EventBus.getInstance().register(this);
    }

    @Override
    protected void onResume() {

        if (AccesBetaseries.estConnecte()) {
            this.passeAuth();
        }

        super.onResume();
    }
    @Override
    protected void onDestroy(){
        //on se désabonne du bus d'évènement
        EventBus.getInstance().unregister(this);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.menu_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.connect_button) {
            new LoginTask().execute(usernameEditText.getText().toString(),passwordEditText.getText().toString());
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
        if (event.getResult()) {
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
