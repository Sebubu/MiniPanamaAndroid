package ch.hsr.sevi.view.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.sevi.view.R;
import ch.hsr.sevi.bl.Loan;

/**
 * Created by SKU on 02.12.2014.
 */
public class LoanAdapter extends ArrayAdapter<Loan> {
    private final ArrayList<Loan> loanList;

    public LoanAdapter(Context context, int textViewResourceId, List<Loan> loanList) {
        super(context, textViewResourceId, loanList);
        this.loanList = new ArrayList<Loan>();
        this.loanList.addAll(loanList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Loan loan = loanList.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.tableview_row, null);
        }
        TextView textName = (TextView) convertView.findViewById(R.id.textName);
        TextView textDate = (TextView) convertView.findViewById(R.id.textDate);

        if(loan.getPickupDate().toString() != null) textDate.setText(loan.getPickupDate().toString());
        if(loan.getGadget().getName() != null) textName.setText(loan.getGadget().getName());


        return convertView;
    }
}
