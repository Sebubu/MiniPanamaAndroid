package ch.hsr.sevi.view;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import ch.hsr.sevi.bl.Gadget;
import ch.hsr.sevi.library.Callback;
import ch.hsr.sevi.library.LibraryService;


public class ReservationActivity extends Activity {

    Button btnReservieren;
    EditText txtSearch;
    ListView lstItems;
    TextView lblQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);


        btnReservieren = (Button) this.findViewById( R.id.res_btnReservieren);
        txtSearch = (EditText) this.findViewById(R.id.res_txtSearch);
        lstItems = (ListView) this.findViewById(R.id.res_listView);
        lblQuantity = (TextView) this.findViewById(R.id.res_lblQuantity);


        btnReservieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                txtSearch.setText("Junge");

            }
        });

        LibraryService.getGadgets(new Callback<List<Gadget>>() {
            @Override
            public void notfiy(List<Gadget> input) {

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_reservation, menu);
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
    public class Ada extends ArrayAdapter<Gadget> {
        private final Context context;
        private final String[] values;

        public Ada(Context context, String[] values) {
            super(context, R.layout.rowlayout, values);
            this.context = context;
            this.values = values;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
            textView.setText(values[position]);
            // change the icon for Windows and iPhone
            String s = values[position];
            if (s.startsWith("iPhone")) {
                imageView.setImageResource(R.drawable.no);
            } else {
                imageView.setImageResource(R.drawable.ok);
            }

            return rowView;
        }
    }
}
