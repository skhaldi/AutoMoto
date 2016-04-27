package com.example.sabah.automoto;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
public class InfoCar extends Activity {

    private TextView tv_vin;
    private TextView tv_make;
    private TextView tv_model;
    private TextView tv_year;
    private TextView tv_engineOilType;
    private String vin;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_car);
        vin = getIntent().getStringExtra("VIN");
        AsyncTask<String, String, String> json = new JsonTask().execute("https://api.edmunds.com/api/vehicle/v2/vins/" + vin + "?fmt=json&api_key=rp2xq63y4bf3nc2gusq9a2uy");

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "InfoCar Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sabah.automoto/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "InfoCar Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.sabah.automoto/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
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
            tv_vin = (TextView) findViewById(R.id.V_vin);
            tv_make = (TextView) findViewById(R.id.V_make);
            tv_model = (TextView) findViewById(R.id.V_model);
            tv_year = (TextView) findViewById(R.id.V_year);

            tv_engineOilType = (TextView) findViewById(R.id.engineOilType);

            try {
                JSONObject parentObject = new JSONObject(result);
                JSONObject makeObject = parentObject.getJSONObject("make");
                String makeName = makeObject.getString("name");
                JSONObject modelObject = parentObject.getJSONObject("model");
                String modelName = modelObject.getString("name");
                String vin = parentObject.getString("vin");
                JSONArray yearArray = parentObject.getJSONArray("years");
                JSONObject yearObject = yearArray.getJSONObject(0);
                String year = yearObject.getString("year");

                tv_make.setText(makeName);
                tv_model.setText(modelName);
                tv_year.setText(year);
                tv_vin.setText(vin);

                JSONArray stylesArray = yearObject.getJSONArray("styles");
                JSONObject stylesObject = stylesArray.getJSONObject(0);
                String style_id = stylesObject.getString("id");


                AsyncTask<String, String, String> json = new JsonTask().execute("https://api.edmunds.com/api/vehicle/v2/styles/" + style_id + "/engines?availability=standard&fmt=json&api_key=rp2xq63y4bf3nc2gusq9a2uy");
                String finalJson = json.toString();
                JSONObject engObject = new JSONObject(finalJson);
                JSONArray enginesObject = engObject.getJSONArray("engines");
                JSONObject engines  = enginesObject.getJSONObject(0);

                String type = engines.getString("type");



                tv_engineOilType.setText(type);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}