package com.example.myappfinal;

import android.content.Context;
import android.util.Log;

import androidx.loader.content.AsyncTaskLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoaderAsyncWeather extends AsyncTaskLoader<String> {

    public LoaderAsyncWeather(Context context) {
        super(context);
    }

    @Override
    public String loadInBackground() {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String jsonStr = null;
        String line;
        try {
            URL url = new URL("https://mocki.io/v1/06dd99c9-242f-438e-992e-3db32feee96f");
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(20000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // InputStream to String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) return null;

            reader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = reader.readLine()) != null) buffer.append(line);

            if (buffer.length() == 0) return null;
            jsonStr = buffer.toString();

        } catch (IOException e) {
            Log.e("MainActivity", "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e("MainActivity", "Error closing stream", e);
                }
            }
        }
        return jsonStr;
    }
    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
    }

}