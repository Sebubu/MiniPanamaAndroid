package ch.hsr.sevi.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import ch.hsr.sevi.library.Callback;
import ch.hsr.sevi.library.LibraryService;


public class RegistrationActivity extends Activity {

    Button btnRegister;
    EditText txtName;
    EditText txtMail;
    EditText txtMatrikel;
    EditText txtPw1;
    EditText txtPw2;
    TextView txtValidate;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        btnRegister = (Button) this.findViewById( R.id.reg_btnRegistrieren);
        txtName = (EditText) this.findViewById(R.id.reg_txtName);
        txtMail = (EditText) this.findViewById(R.id.reg_txtMail);
        txtMatrikel = (EditText) this.findViewById(R.id.reg_txtMartrikelNummer);
        txtPw1 = (EditText) this.findViewById(R.id.reg_txtPw1);
        txtPw2 = (EditText) this.findViewById(R.id.reg_txtPw2);
        txtValidate = (TextView) this.findViewById(R.id.reg_lblValidate);
        pb = (ProgressBar) this.findViewById(R.id.reg_progressBar);
        pb.setVisibility(View.GONE);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtValidate.setText("");

                String name = txtName.getText().toString();
                if(name.length() < 3){
                    txtValidate.setText(getResources().getString(R.string.reg_InvalidName));
                }
                String mailText = txtMail.getText().toString();
                if(!mailText.contains("@") || !mailText.contains(".")) {
                    txtValidate.setText(getResources().getString(R.string.reg_InvalidMail));
                }
                String martrikel = txtMatrikel.getText().toString();
                if(!martrikel.contains("-")) {
                    txtValidate.setText(getResources().getString(R.string.reg_InvalidMartrikel));
                }
                String pw1 = txtPw1.getText().toString();
                String pw2 = txtPw2.getText().toString();
                if(!pw1.equals(pw2) || pw1.length() < 3) {
                    txtValidate.setText(getResources().getString(R.string.reg_InvalidPassword));
                }

                if(txtValidate.getText().toString().isEmpty()) {
                    Callback<Boolean> registerCallback = new Callback<Boolean>() {
                        @Override
                        public void notfiy(Boolean input) {
                            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    };
                    btnRegister.setEnabled(false);
                    pb.setVisibility(View.VISIBLE);
                   LibraryService.register(mailText,pw1,name,martrikel, registerCallback);



                }

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
