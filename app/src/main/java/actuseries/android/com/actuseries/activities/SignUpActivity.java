package actuseries.android.com.actuseries.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.betaseries.AccesBetaseries;

/**
 * Created by Clement on 08/01/2015.
 */
public class SignUpActivity extends ActionBarActivity implements View.OnClickListener {

    private EditText loginEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        this.loginEditText = (EditText) findViewById(R.id.user_editText);
        this.emailEditText = (EditText) findViewById(R.id.email_editText);
        this.passwordEditText = (EditText) findViewById(R.id.password_editText);
        this.passwordConfirmEditText = (EditText) findViewById(R.id.password_confirm_editText);

        findViewById(R.id.signup_button).setOnClickListener(this);
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if (!this.loginEditText.getText().toString().equals("") &&
                !this.emailEditText.getText().toString().equals("") &&
                !this.passwordEditText.getText().toString().equals("") &&
                this.passwordEditText.getText().toString().equals(this.passwordConfirmEditText.getText().toString())) {

            // TODO a faire dans une async task
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AccesBetaseries.creationCompte(loginEditText.getText().toString(), passwordEditText.getText().toString(), emailEditText.getText().toString());

                }
            }).start();

        }

        this.finish();
    }
}
