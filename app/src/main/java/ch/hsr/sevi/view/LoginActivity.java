package ch.hsr.sevi.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ch.hsr.sevi.library.Callback;
import ch.hsr.sevi.library.LibraryService;
import ch.hsr.sevi.view.main.MainActivity;


public class LoginActivity extends Activity {
    private Callback<Boolean> loginCallback;
    private Button buttonLogin;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress = new ProgressDialog(this);
        setContentView(R.layout.activity_login);
        final Button buttonRegistration = (Button) findViewById(R.id.buttonRegistration);
        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RegistrationActivity.class);
                startActivity(intent);
            }
        });

        loginCallback = new Callback<Boolean>() {
            @Override
            public void notfiy(Boolean input) {
                if(LibraryService.IsLoggedIn()){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }else{
                    progress.dismiss();
                    TextView textValidation = (TextView) findViewById(R.id.textLoginValidation);
                    textValidation.setText("Login wrong, try it again!");
                    buttonLogin.setEnabled(true);
                }
            }
        };

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonLogin.setEnabled(false);
                progress.setMessage("wating for server response...");
                progress.setIndeterminate(true);
                progress.show();
                EditText editEmail = (EditText) findViewById(R.id.editEmail);
                EditText editPassword = (EditText) findViewById(R.id.editPassword);
                LibraryService.login(editEmail.getText().toString(),editPassword.getText().toString(), loginCallback);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
