package actuseries.android.com.actuseries.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;

public class LoginActivity extends ActionBarActivity implements View.OnClickListener, Observer {

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

        if (AccesBetaseries.estConnecte()) {
            this.passeAuth();
        } else {
            AccesBetaseries.addObs(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.connect_button) {
            AccesBetaseries.connexionMembre(usernameEditText.getText().toString(), passwordEditText.getText().toString());
        } else {
            Intent i = new Intent(this, SignUpActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void update(Observable observable, Object data) {

        if (AccesBetaseries.estConnecte()) {
            AccesBetaseries.removeObs(this);
            this.passeAuth();
        }
    }

    public void passeAuth() {
        Intent i = new Intent(this, ListSeriesActivity.class);
        startActivity(i);
    }
}