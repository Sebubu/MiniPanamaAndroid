package ch.hsr.sevi.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class RegistrationActivity extends Activity {

    Button btnRegister;
    TextView txtMail;
    TextView txtMatrikel;
    TextView txtPw1;
    TextView txtPw2;
    TextView txtValidate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        btnRegister = (Button) this.findViewById( R.id.buttonRegistration);
        txtMail = (EditText) this.findViewById(R.id.textEmail);
        txtMatrikel = (EditText) this.findViewById(R.id.textMartrikelNummer);
        txtPw1 = (EditText) this.findViewById(R.id.textPasswordReg);
        txtPw2 = (EditText) this.findViewById(R.id.textPasswordReg2);
txtValidate = (EditText) this.findViewById(R.id.text);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_registration, menu);
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
