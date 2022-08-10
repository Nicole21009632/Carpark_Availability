package sg.edu.rp.c346.id21009632.caparkavailability;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<String> carparkList;

    public CustomAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        carparkList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)
        parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvCarparkNumber = rowView.findViewById(R.id.tvCarparkNumber);

        String currentVersion = carparkList.get(position);

        tvCarparkNumber.setText(currentVersion);

        return rowView;
    }
}
