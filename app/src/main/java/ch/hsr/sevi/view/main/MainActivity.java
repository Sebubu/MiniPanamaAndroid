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
                Item item = (Item) lvReservations.getAdapter().getItem(selectedItem);
                Reservation d = null;
                for(Reservation r: itemsReservations){
                    if(r.getReservationId() == item.getId()){
                        d = r;
                        break;
                    }
                }
                itemsReservations.remove(d);
                if(d != null)LibraryService.deleteReservation( d,deleteReservationCallback);
            }
        });


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
        ArrayList<Item> itemList = new ArrayList<Item>();
        itemList.add(new Item("Name", "Lent till"));
        for(Reservation r: itemsReservations)
        {
            String gadget = r.getGadget().getName();
            String date = r.getReservationDate().toString();
            String id = r.getReservationId();
            itemList.add(new Item(id, gadget, date));
        }

        RowAdapter dataAdapter = new RowAdapter(this, R.layout.tableview_row, itemList);
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
        ArrayList<Item> itemList = new ArrayList<Item>();
        itemList.add(new Item("Name", "Due date"));
        for(Loan l: itemsLoans)
        {
            String gadget = l.getGadget().getName();
            String date = l.getPickupDate().toString();
            itemList.add(new Item(gadget, date));
        }

        RowAdapter dataAdapter = new RowAdapter(this, R.layout.tableview_row, itemList);
        lvLoans = (ListView) findViewById(R.id.lvLoans);
        lvLoans.setAdapter(dataAdapter);

    }
}
