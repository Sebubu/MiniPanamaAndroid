package ch.hsr.sevi.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
<<<<<<< HEAD
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.sevi.bl.Loan;
import ch.hsr.sevi.bl.Reservation;
import ch.hsr.sevi.library.*;
import ch.hsr.sevi.view.R;
=======

import ch.hsr.sevi.library.Callback;
import ch.hsr.sevi.library.LibraryService;
>>>>>>> 7cad6fcde9392ecafbaa06a0488f1de410971ec2

public class MainActivity extends Activity {

    private TableLayout tblReservations;
    private TableLayout tblLoans;
    private List<Reservation> itemsReservations = new ArrayList<Reservation>();
    private List<Loan> itemsLoans = new ArrayList<Loan>();
    private Callback<Boolean> registerCallback;
    private Callback<Boolean> loginCallback;
    private Callback<List<Reservation>> reservationsCallback;
    private Callback<List<Loan>> loansCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initCallbacks();

        //LibraryService.register("test@hsr.ch", "password", "name", "studentNumber", registerCallback);
        LibraryService.login("m@hsr.ch", "12345", loginCallback);

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
        registerCallback = new Callback<Boolean>() {
            @Override
            public void notfiy(Boolean input) {

            }
        };

        loginCallback = new Callback<Boolean>() {
            @Override
            public void notfiy(Boolean input) {
                if(LibraryService.IsLoggedIn()){
                    LibraryService.getReservationsForCustomer(reservationsCallback);
                    LibraryService.getLoansForCustomer(loansCallback);
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
        tblReservations = (TableLayout) findViewById(R.id.tblReservations);
        for(Reservation r: itemsReservations)
        {
            String gadget = r.getGadget().getName();
            String date = r.getReservationDate().toString();

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView tvGadget = new TextView(this);
            tvGadget.setText(gadget);
            tr.addView(tvGadget);

            TextView tvDate = new TextView(this);
            tvDate .setText(date);
            tr.addView(tvDate );

            tblReservations.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    private void fillLoans(){
        tblLoans = (TableLayout) findViewById(R.id.tblLoans);
        for(Loan l: itemsLoans)
        {
            String gadget = l.getGadget().getName();
            String date = l.getPickupDate().toString();
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.FILL_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            TextView tvGadget = new TextView(this);
            tvGadget.setText(gadget);
            tr.addView(tvGadget);

            TextView tvDate = new TextView(this);
            tvDate .setText(date);
            tr.addView(tvDate );

            tblLoans.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.FILL_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}
