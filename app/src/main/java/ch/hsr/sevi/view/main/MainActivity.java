package ch.hsr.sevi.view.main;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.sevi.bl.Loan;
import ch.hsr.sevi.bl.Reservation;
import ch.hsr.sevi.library.Callback;
import ch.hsr.sevi.library.LibraryService;
import ch.hsr.sevi.view.LoginActivity;
import ch.hsr.sevi.view.R;
import ch.hsr.sevi.view.ReservationActivity;


public class MainActivity extends Activity {

    private ListView lvReservations;
    private ListView lvLoans;
    private List<Reservation> itemsReservations = new ArrayList<Reservation>();
    private List<Loan> itemsLoans = new ArrayList<Loan>();
    private Callback<Boolean> logoutCallback;
    private Callback<List<Reservation>> reservationsCallback;
    private Callback<List<Loan>> loansCallback;
    private Callback<Boolean> deleteReservationCallback;
    private int selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCallbacks();


        //LibraryService.register("test@hsr.ch", "password", "name", "studentNumber", registerCallback);
        //LibraryService.login("m@hsr.ch", "12345", loginCallback);
        if(LibraryService.IsLoggedIn()){
            LibraryService.getReservationsForCustomer(reservationsCallback);
            LibraryService.getLoansForCustomer(loansCallback);
        }

        Button btnReservation = (Button) findViewById(R.id.btnReservation);
        btnReservation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReservationActivity.class);
                startActivity(intent);
            }
        });

        Button btnLogout = (Button) findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LibraryService.logout(logoutCallback);
            }
        });

        Button btnDeleteReservation = (Button) findViewById(R.id.btnDeleteReservation);
        btnDeleteReservation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Reservation r = (Reservation) lvReservations.getAdapter().getItem(selectedItem);
                itemsReservations.remove(r);
                if(r != null)LibraryService.deleteReservation( r,deleteReservationCallback);
            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(LibraryService.IsLoggedIn()){
            LibraryService.getReservationsForCustomer(reservationsCallback);
            LibraryService.getLoansForCustomer(loansCallback);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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


    private void initCallbacks(){
        logoutCallback = new Callback<Boolean>() {
            @Override
            public void notfiy(Boolean input) {
                if(!LibraryService.IsLoggedIn()) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };

        deleteReservationCallback = new Callback<Boolean>() {
            @Override
            public void notfiy(Boolean input) {
                if(LibraryService.IsLoggedIn()) {
                    fillReservations();
                }
            }
        };


        loansCallback = new Callback<List<Loan>>() {
            @Override
            public void notfiy(List<Loan> input) {
                if(!input.isEmpty()) {
                    itemsLoans = input;
                    fillLoans();
                }
            }
        };

        reservationsCallback = new Callback<List<Reservation>>() {
            @Override
            public void notfiy(List<Reservation> input) {
                if(!input.isEmpty()){
                    itemsReservations = input;
                    fillReservations();
                }
            }
        };
    }

    private void fillReservations(){
        ReservationAdapter dataAdapter = new ReservationAdapter(this, R.layout.tableview_row, itemsReservations);
        lvReservations = (ListView) findViewById(R.id.lvReservations);
        lvReservations.setAdapter(dataAdapter);

        lvReservations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i=0; i<parent.getChildCount(); i++){
                    if(i == position){
                        parent.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.blue));
                        selectedItem = i;
                    }else{
                        parent.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.white));
                    }
                }
            }
        });

    }

    private void fillLoans(){
        LoanAdapter dataAdapter = new LoanAdapter(this, R.layout.tableview_row, itemsLoans);
        lvLoans = (ListView) findViewById(R.id.lvLoans);
        lvLoans.setAdapter(dataAdapter);

    }
}
