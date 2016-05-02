package com.example.sabah.automoto;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Sabah on 4/18/2016.
 */
public class InfoCar extends Fragment {

    private TextView tv_vin;
    private TextView tv_make;
    private TextView tv_model;
    private TextView tv_year;
    private TextView tv_engineOilType;
    private String vin;
    private JsonTask task = new JsonTask();
    private JsonTask task2 = new JsonTask();

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */


    public InfoCar()  {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vin = getActivity().getIntent().getStringExtra("VIN");
                //"2g1fc3d33c9165616";
        //Log.d("VIN Value : ",vin);
        task.execute("https://api.edmunds.com/api/vehicle/v2/vins/" + vin + "?fmt=json&api_key=rp2xq63y4bf3nc2gusq9a2uy");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.info_car, container, false);
    }






    public class JsonTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                return finalJson;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            tv_vin = (TextView)  getView().findViewById(R.id.V_vin);
            tv_make = (TextView)  getView().findViewById(R.id.V_make);
            tv_model = (TextView)  getView().findViewById(R.id.V_model);
            tv_year = (TextView)  getView().findViewById(R.id.V_year);

            tv_engineOilType = (TextView)  getView().findViewById(R.id.engineOilType);
            JSONObject parentObject = null;
            try {
                String makeName = "";
                String modelName = "";
                String year = "";
                String style_id = "";
                String vin = "";

                if (result != null) {
                    parentObject = new JSONObject(result);
                    if (parentObject != null) {
                        if (parentObject.optJSONObject("make") != null) {
                            Log.d("parentObject make", parentObject.getJSONObject("make").toString());
                JSONObject makeObject = parentObject.getJSONObject("make");
                            if (makeObject != null) {
                                makeName = makeObject.getString("name");
                            }
                JSONObject modelObject = parentObject.getJSONObject("model");
                            if (modelObject != null) {
                                modelName = modelObject.getString("name");
                            }
                            vin = parentObject.getString("vin");
                JSONArray yearArray = parentObject.getJSONArray("years");
                            JSONObject yearObject = null;
                            if (yearArray != null) {
                                yearObject = yearArray.getJSONObject(0);
                                if (yearObject != null) {
                                    year = yearObject.getString("year");
                                    JSONArray stylesArray = yearObject.getJSONArray("styles");
                                    JSONObject stylesObject = stylesArray.getJSONObject(0);
                                    style_id = stylesObject.getString("id");
                                }
                            }
                tv_make.setText(makeName);
                tv_model.setText(modelName);
                tv_year.setText(year);
                tv_vin.setText(vin);
                        }
                        if (parentObject != null) {
                            if (parentObject.optJSONArray("engines") != null) {
                                Log.d("parentObject engines", parentObject.getJSONArray("engines").toString());
                                JSONArray enginesObject = parentObject.getJSONArray("engines");
                JSONObject engines  = enginesObject.getJSONObject(0);
                String type = engines.getString("type");
                tv_engineOilType.setText(type);
                            }
                        }
                    }
                }
                if (style_id != "") {
                    Log.d("style id", "done");
                    if (task.getStatus().toString() == "RUNNING") {
                        task.cancel(true);
                    }
                    task2.execute("https://api.edmunds.com/api/vehicle/v2/styles/" + style_id + "/engines?availability=standard&fmt=json&api_key=rp2xq63y4bf3nc2gusq9a2uy");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}