package sg.edu.rp.c346.id21009632.caparkavailability;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    EditText etSearch;
    Button btnShowAll, btnSearch;
    TextView tvShow;

    ListView lvCarparkName;
    ArrayList<Carpark> alCarparkInfo;
    ArrayList<String> alCarparkName;
    ArrayList<String> alCarparkSearch;
    CustomAdapter caCarpark;
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etSearch = findViewById(R.id.editTextSearch);
        btnShowAll = findViewById(R.id.buttonShowAll);
        btnSearch = findViewById(R.id.buttonSearch);
        tvShow = findViewById(R.id.tvShow);
        lvCarparkName = findViewById(R.id.lvCarpark);
        alCarparkName = new ArrayList<String>();
        alCarparkSearch = new ArrayList<String>();
        alCarparkInfo = new ArrayList<>();
        caCarpark = new CustomAdapter(this, R.layout.row, alCarparkName);

        client = new AsyncHttpClient();

        btnShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                etSearch.setText("");
                tvShow.setText("SHOWING ALL CARPARKS");

                caCarpark = new CustomAdapter(MainActivity.this, R.layout.row, alCarparkName);
                lvCarparkName.setAdapter(caCarpark);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search = etSearch.getText().toString();
                String message = "";

                if (search.equals("")) {
                    message = "SHOWING ALL CARPARKS";
                    caCarpark = new CustomAdapter(MainActivity.this, R.layout.row, alCarparkName);
                } else {
                    boolean empty = true;
                    alCarparkSearch.clear();

                    for (int i = 0; i < alCarparkName.size(); i++) {
                        if (alCarparkName.get(i).contains(search)) {
                            alCarparkSearch.add(alCarparkName.get(i));
                            empty = false;
                        }
                    }

                    if (empty) {
                        message = "NO RESULTS FOUND";
                    } else {
                        message = "SHOWING RESULTS FOR '" + search + "'";
                    }

                    caCarpark = new CustomAdapter(MainActivity.this, R.layout.row, alCarparkSearch);
                }
                lvCarparkName.setAdapter(caCarpark);
                tvShow.setText(message);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        client.get("https://api.data.gov.sg/v1/transport/carpark-availability", new JsonHttpResponseHandler() {

            String carpark_number;
            String lots_available;
            String lot_type;
            String total_lots;

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArrItems = response.getJSONArray("items");
                    JSONObject firstObj = jsonArrItems.getJSONObject(0);
                    JSONArray jsonArrCarparkData = firstObj.getJSONArray("carpark_data");

                    for (int i = 0; i < jsonArrCarparkData.length(); i++) {
                        JSONObject jsonObjCarparkData = jsonArrCarparkData.getJSONObject(i);
                        JSONArray jsonArrCarparkInfo = jsonObjCarparkData.getJSONArray("carpark_info");
                        JSONObject secondObject = jsonArrCarparkInfo.getJSONObject(0);

                        carpark_number = jsonObjCarparkData.getString("carpark_number");
                        total_lots = secondObject.getString("total_lots");
                        lot_type = secondObject.getString("lot_type");
                        lots_available = secondObject.getString("lots_available");

                        Carpark carpark = new Carpark(carpark_number, total_lots, lot_type, lots_available);
                        alCarparkName.add(carpark_number);
                        alCarparkInfo.add(carpark);

                    }
                } catch (JSONException e) {
                    Log.d("exception", e.toString());
                }

                lvCarparkName.setAdapter(caCarpark);
            }
        });

        lvCarparkName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long identity) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                String data = alCarparkName.get(position);

                int indexPosition = 0;
                for (int i = 0; i < alCarparkInfo.size(); i++) {
                    if (alCarparkInfo.get(i).getCarpark_number() == data) {
                        indexPosition = i;
                        break;
                    }
                }

                String message = "Total Lots: " + alCarparkInfo.get(indexPosition).getTotal_lots();
                message += "\nLot Type: " + alCarparkInfo.get(indexPosition).getLot_type();
                message += "\nLots Available: " + alCarparkInfo.get(indexPosition).getLots_available();

                myBuilder.setTitle("Carpark Number: " + alCarparkInfo.get(indexPosition).getCarpark_number());
                myBuilder.setMessage(message);

                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("Close", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
    }
}