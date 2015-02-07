package actuseries.android.com.actuseries.activities;

import android.os.Bundle;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import actuseries.android.com.actuseries.R;
import actuseries.android.com.actuseries.event.LoginResultEvent;
import actuseries.android.com.actuseries.event.TaskManager;
import actuseries.android.com.actuseries.tasks.SignupTask;

/**
 * Created by Clement on 08/01/2015.
 */
public class SignUpActivity extends MainMenuActionBarActivity implements View.OnClickListener {

    private EditText loginEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText passwordConfirmEditText;
    private ProgressBar loadingProgressBar;
    private Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        this.loginEditText = (EditText) findViewById(R.id.signup_editText_login);
        this.emailEditText = (EditText) findViewById(R.id.signup_editText_email);
        this.passwordEditText = (EditText) findViewById(R.id.signup_editText_password);
        this.passwordConfirmEditText = (EditText) findViewById(R.id.signup_editText_passwordConfirm);
        this.loadingProgressBar = (ProgressBar) findViewById(R.id.signup_progressBar_loading);
        this.signupButton = (Button) findViewById(R.id.signup_button_signup);
        signupButton.setOnClickListener(this);
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
        if(id == R.id.menu_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(inputsOk()) {
            String[] params = {loginEditText.getText().toString(), passwordEditText.getText().toString(), emailEditText.getText().toString().replace("+", "%2b")};
            TaskManager.launchTask(SignupTask.class, params);
            signupButton.setVisibility(View.INVISIBLE);
            loadingProgressBar.setVisibility(View.VISIBLE);
        }
    }

    private boolean inputsOk() {
        if(!this.loginEditText.getText().toString().isEmpty()) {
            if(!this.emailEditText.getText().toString().isEmpty() && Patterns.EMAIL_ADDRESS.matcher(this.emailEditText.getText().toString()).matches()) {
                if(!this.passwordEditText.getText().toString().isEmpty()) {
                    if(this.passwordEditText.getText().toString().equals(this.passwordConfirmEditText.getText().toString())) {
                        return true;
                    } else {
                        passwordConfirmEditText.setError("Veuillez saisir à nouveau le mot de passe");
                        passwordEditText.setError(null);
                        loginEditText.setError(null);
                        emailEditText.setError(null);
                    }
                } else {
                    passwordEditText.setError("Veuillez saisir le mot de passe");
                    loginEditText.setError(null);
                    emailEditText.setError(null);
                    passwordConfirmEditText.setError(null);

                }
            } else {
                emailEditText.setError("Veuillez saisir une adresse email valide");
                loginEditText.setError(null);
                passwordEditText.setError(null);
                passwordConfirmEditText.setError(null);
            }
        } else {
            loginEditText.setError("Veuillez saisir un identifiant");
            passwordEditText.setError(null);
            emailEditText.setError(null);
            passwordConfirmEditText.setError(null);
        }
        return false;
    }

    @Subscribe
    public void onSignupTaskResult(LoginResultEvent event) {
        signupButton.setVisibility(View.VISIBLE);
        loadingProgressBar.setVisibility(View.GONE);
        if(event.getResult()) {
            this.finish();
        } else {
            this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Erreur de création de compte. Identifiant déjà utilisé, ou pas.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
