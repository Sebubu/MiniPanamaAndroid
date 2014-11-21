package ch.hsr.sevi.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ch.hsr.sevi.bl.Gadget;
import ch.hsr.sevi.library.Callback;
import ch.hsr.sevi.library.LibraryService;


public class ReservationActivity extends Activity {

    Button btnReservieren;
    EditText txtSearch;
    ListView lstItems;
    ListView lstHeader;
    TextView lblQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);


        btnReservieren = (Button) this.findViewById( R.id.res_btnReservieren);
        txtSearch = (EditText) this.findViewById(R.id.res_txtSearch);
        lstItems = (ListView) this.findViewById(R.id.res_listView);
        lstHeader = (ListView) this.findViewById(R.id.res_listViewHeader);
        lblQuantity = (TextView) this.findViewById(R.id.res_lblQuantity);


        btnReservieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        LibraryService.login("b@hsr.ch","12345", new Callback<Boolean>() {
            @Override
            public void notfiy(Boolean input) {
                LibraryService.getGadgets(new Callback<List<Gadget>>() {
                    @Override
                    public void notfiy(List<Gadget> input) {
                        ArrayList<HashMap<String,String>> headerList = new ArrayList<HashMap<String, String>>();
                        HashMap<String,String> header = new HashMap<String, String>();

                        header.put("gadget","Gadget");
                        header.put("lenttill","Lent till");

                        headerList.add(header);

                        SimpleAdapter headerAdapter = new SimpleAdapter(ReservationActivity.this,headerList,R.layout.listview_row,new String[] {"gadget","lenttill"},new int[]{R.id.row_name,R.id.row_date});
                        lstHeader.setAdapter(headerAdapter);

                        GadgetAdapter ga = new GadgetAdapter(ReservationActivity.this, input);
                        lstItems.setAdapter(ga);

                    }
                });
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
   public class GadgetAdapter extends BaseAdapter{
        public List<Gadget> gadgets;
       Activity activity;
       public GadgetAdapter(Activity activity, List<Gadget> g){
           super();
           this.activity = activity;
           this.gadgets = g;
       }

       @Override
       public int getCount() {
           return gadgets.size();
       }

       @Override
       public Object getItem(int pos) {
           return gadgets.get(pos);
       }
       @Override
       public long getItemId(int pos) {
           return Long.decode(gadgets.get(pos).getInventoryNumber());
       }

       private class ViewHolder {
           TextView txtGadgetName;
           TextView txtLentTill;
       }

       @Override
       public View getView(int pos, View convertView, ViewGroup parent) {
           ViewHolder holder;
           LayoutInflater inflater =  activity.getLayoutInflater();

           if (convertView == null)
           {
               convertView = inflater.inflate(R.layout.listview_row, null);
               holder = new ViewHolder();
               holder.txtGadgetName = (TextView) convertView.findViewById(R.id.row_name);
               holder.txtLentTill = (TextView) convertView.findViewById(R.id.row_date);
               convertView.setTag(holder);
           }
           else
           {
               holder = (ViewHolder) convertView.getTag();
           }

           Gadget g = gadgets.get(pos);
           holder.txtGadgetName.setText(g.getName());

           holder.txtLentTill.setText("5");
            return convertView;

       }
    }
}
