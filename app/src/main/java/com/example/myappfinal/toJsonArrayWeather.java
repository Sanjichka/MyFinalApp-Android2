package com.example.myappfinal;

import com.example.myappfinal.Models.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class toJsonArrayWeather {

    //od String  vo  JSONArray
    public static ArrayList<Weather> dataParsing(String data) {

        ArrayList<Weather> arrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String degrees = jsonObj.getString("degrees");
                String time = jsonObj.getString("time");

                Weather weather = new Weather();
                weather.setDegrees(degrees);
                weather.setTime(time);
                arrayList.add(weather);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
