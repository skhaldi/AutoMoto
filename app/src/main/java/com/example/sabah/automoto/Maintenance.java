package com.example.sabah.automoto;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.sabah.automoto.InfoCar;

/**
 * Created by Sabah on 5/2/2016.
 */
public class Maintenance extends Fragment {

    private TextView TV_last, TV_miles, TV_note, TV_next, TV_or;

    public Maintenance() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentHandle = inflater.inflate(R.layout.maintenance, container, false);

        TV_last  = (TextView) fragmentHandle.findViewById(R.id.V_last);
        TV_miles = (TextView) fragmentHandle.findViewById(R.id.V_miles);
        TV_note  = (TextView) fragmentHandle.findViewById(R.id.V_note);
        TV_next  = (TextView) fragmentHandle.findViewById(R.id.V_next);
        TV_or    = (TextView) fragmentHandle.findViewById(R.id.V_or);

        TV_last.setText(" 11/01/2015 ");
        TV_miles.setText(" 65,000");
        TV_note.setText(" Oil Change");
        TV_next.setText(" 3/23/2016 ");
        TV_or.setText(" 70,000");



        TableLayout tl = (TableLayout) fragmentHandle.findViewById(R.id.schedule_status_table);
        TableRow row = new TableRow(getActivity());
        TextView tv = new TextView(getActivity());
        TextView tv_status = new TextView(getActivity());
        tv.setText("10000 miles");
        tv_status.setText("done");
        //Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "assets/fonts/DroidSansFallback.ttf");

        //tv.setTypeface(tf);
        //tv_status.setTypeface(tf);
        tl.addView(row);
        row.addView(tv);
        row.addView(tv_status);

        return fragmentHandle;
    }
}
