package com.example.myappfinal;

import com.example.myappfinal.Models.Jokes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class toJsonArrayJokes {

    //od String  vo  JSONArray
    public static ArrayList<Jokes> dataParsing(String data) {

        ArrayList<Jokes> arrayList = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String category = jsonObj.getString("category");
                String title = jsonObj.getString("title");
                String start = jsonObj.getString("start");
                String finish = jsonObj.getString("finish");

                Jokes joke = new Jokes();
                joke.setCategory(category);
                joke.setTitle(title);
                joke.setStart(start);
                joke.setFinish(finish);
                arrayList.add(joke);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
