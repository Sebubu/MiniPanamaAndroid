package ch.hsr.sevi.view;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.sevi.bl.Gadget;
import ch.hsr.sevi.library.Callback;
import ch.hsr.sevi.library.LibraryService;


public class ReservationActivity extends Activity {

    Button btnReservieren;
    EditText txtSearch;
    ListView lstItems;
    TextView lblQuantity;
    private int callbacksCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);


        btnReservieren = (Button) this.findViewById( R.id.res_btnReservieren);
        txtSearch = (EditText) this.findViewById(R.id.res_txtSearch);
        lstItems = (ListView) this.findViewById(R.id.res_listView);
        lblQuantity = (TextView) this.findViewById(R.id.res_lblQuantity);


        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
               GadgetAdapter ga = (GadgetAdapter)   lstItems.getAdapter();
                ga.setFilter(editable.toString());
                ReservationActivity.this.onItemsChanged();
            }
        });

        btnReservieren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnReservieren.setEnabled(false);
                txtSearch.setEnabled(false);
                lstItems.setEnabled(false);
                GadgetAdapter ga = (GadgetAdapter) lstItems.getAdapter();
                List<Gadget> gadgets = ga.getSelected();
                callbacksCount = gadgets.size();
                   for(Gadget g: gadgets) {
                       LibraryService.reserveGadget(g,new Callback<Boolean>() {
                           @Override
                           public void notfiy(Boolean input) {
                               callbacksCount--;
                               if(callbacksCount == 0) {
                                   ReservationActivity.this.finish();
                               }
                           }
                       });
                   }

            }
        });


        LibraryService.login("b@hsr.ch","12345", new Callback<Boolean>() {
            @Override
            public void notfiy(Boolean input) {
                LibraryService.getGadgets(new Callback<List<Gadget>>() {
                    @Override
                    public void notfiy(List<Gadget> input) {
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

    public void onItemsChanged() {
        GadgetAdapter ga = (GadgetAdapter) lstItems.getAdapter();
        lblQuantity.setText(ga.getSelected().size() + "/3");
    }


   public class GadgetAdapter extends BaseAdapter{
        private List<Gadget> gadgets;
       private List <RowController> filteredModels = new ArrayList<RowController>();
       private List <RowController> baseModels = new ArrayList<RowController>();
       ReservationActivity activity;



       private String filter;
       public GadgetAdapter(Activity activity, List<Gadget> g){
           super();
           this.activity =(ReservationActivity) activity;
           this.gadgets = g;
           for(Gadget ga: gadgets) {
               baseModels.add(new RowController(ga));
           }
           this.setFilter("");
       }

       @Override
       public int getCount() {
           return filteredModels.size();
       }

       @Override
       public Object getItem(int pos) {
           return filteredModels.get(pos);
       }
       @Override
       public long getItemId(int pos) {
           return Long.decode(gadgets.get(pos).getInventoryNumber());
       }
       public String getFilter() {
           return filter;
       }

       public void setFilter(String filter) {
           this.filter = filter;

           List<RowController> list = new ArrayList<RowController>();
           for(RowController rc: baseModels) {
               if(rc.getGadget().getName().toLowerCase().contains(filter.toLowerCase())) {
                   list.add(rc);
               }
           }
           this.filteredModels = list;

           this.notifyDataSetChanged();
         }



       @Override
       public View getView(int pos, View convertView, ViewGroup parent) {
           LayoutInflater inflater =  activity.getLayoutInflater();

           if (convertView == null)
           {
               convertView = inflater.inflate(R.layout.listview_row, null);
           }
           CheckBox chkGadget = (CheckBox) convertView.findViewById(R.id.row_checkBox);

           chkGadget.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                  if( GadgetAdapter.this.getSelected().size() > 3) {
                      compoundButton.setChecked(false);
                  }
                   GadgetAdapter.this.activity.onItemsChanged();
               }
           });
           RowController g = filteredModels.get(pos);
           g.setChkView(chkGadget);
           chkGadget.setText(g.getGadget().getName());

           return convertView;

       }
       public List<Gadget> getSelected() {
           List<Gadget> ret = new ArrayList<Gadget>();
           for(RowController rc: filteredModels) {
               if(rc.getChkView().isChecked()) ret.add(rc.getGadget());
           }
           return ret;
       }
       private class RowController {
           private Gadget gadget;


           public CheckBox getChkView() {
               return chkView;
           }

           public void setChkView(CheckBox chkView) {
               this.chkView = chkView;
           }

           private CheckBox chkView;
           public RowController(Gadget g) {
                this.gadget = g;
           }
           public Gadget getGadget() {
               return gadget;
           }

       }
    }
}
