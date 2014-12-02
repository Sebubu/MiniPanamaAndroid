package ch.hsr.sevi.view.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.hsr.sevi.bl.Reservation;
import ch.hsr.sevi.view.R;

/**
 * Created by SKU on 02.12.2014.
 */
public class ReservationAdapter extends ArrayAdapter<Reservation> {
    private final ArrayList<Reservation> reservationList;

    public ReservationAdapter(Context context, int textViewResourceId, List<Reservation> reservationList) {
        super(context, textViewResourceId, reservationList);
        this.reservationList = new ArrayList<Reservation>();
        this.reservationList.addAll(reservationList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Reservation reservation = reservationList.get(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.tableview_row, null);
        }
        TextView textName = (TextView) convertView.findViewById(R.id.textName);
        TextView textDate = (TextView) convertView.findViewById(R.id.textDate);

        if(reservation.getReservationDate().toString() != null) textDate.setText(reservation.getReservationDate().toString());
        if(reservation.getGadget().getName() != null) textName.setText(reservation.getGadget().getName());


        return convertView;
    }
}
