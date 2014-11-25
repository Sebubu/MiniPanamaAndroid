package ch.hsr.sevi.view.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

import ch.hsr.sevi.view.R;

/**
 * Created by SKU on 25.11.2014.
 */
public class RowAdapter extends ArrayAdapter<Item> {

    private final ArrayList<Item> itemlList;


    public RowAdapter(Context context, int textViewResourceId, ArrayList<Item> itemList) {
        super(context, textViewResourceId, itemList);
        this.itemlList = new ArrayList<Item>();
        this.itemlList.addAll(itemList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Item item = itemlList.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.tableview_row, null);
        }
        TextView textName = (TextView) convertView.findViewById(R.id.textName);
        TextView textDate = (TextView) convertView.findViewById(R.id.textDate);

        if(item.getDate() != null) textDate.setText(item.getDate());
        if(item.getName() != null) textName.setText(item.getName());


        return convertView;
    }
}
