package com.example.myappfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.myappfinal.Models.Jokes;
import com.example.myappfinal.Models.Weather;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;
    AlarmManager alarmManager;
    TextView txt;
    TextView txt1;
    ArrayList<Jokes> arrayJokes = null;
    ArrayList<Weather> arrayWeather = null;
    private NotificationManager mNotificationManager;
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    Intent intent = new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = findViewById(R.id.tv);
        txt1 = findViewById(R.id.tv1);
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        //networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("PROCES", "Sega kje pochne loader id==1");
            LoaderManager.getInstance(this).initLoader(1, null, this).forceLoad();
            Log.d("PROCES", "Sega kje pochne loader id==0");
            LoaderManager.getInstance(this).initLoader(0, null, this).forceLoad();
            Log.d("PROCES", "Loader id==0 zavrshi");
        } else {
            // uredot ne e povrzan na internet
            ShowDialog.show(MainActivity.this);
        }

    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        // povlekuvanje na  podatocite od RestApi preku AsyncTaskLoader
        if(id==0) {
            Log.d("PROCES", "Povik LoaderAsyncJokes");
            return new LoaderAsyncJokes(this);
        }
        else {
            Log.d("PROCES", "Povik LoaderAsyncWeather");
            return new LoaderAsyncWeather(this);
        }
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        if(data.startsWith("[{\"category")) {
            Log.d("PROCES", "String arrayJokes + " + data);
            arrayJokes = toJsonArrayJokes.dataParsing(data);
            intent.putExtra("arrayJokes", data);
        }
        else if(data.startsWith("[{\"degrees")) {
            Log.d("PROCES", "String arrayWeather + " + data);
            arrayWeather = toJsonArrayWeather.dataParsing(data);
            intent.putExtra("arrayWeather", data);
        }

        if(arrayWeather!=null && arrayJokes!=null) {
            // primenite podatoci se vo json format


    //        Random r = new Random();
    //        Log.i("zdr",""+arrayJokes.size());
    //        int n = r.nextInt(arrayJokes.size());
    //        Log.i("zdr",""+n);
    //
    //
    //        Jokes joke = new Jokes();
    //        joke.setCategory(arrayJokes.get(n).getCategory());
    //        joke.setTitle(arrayJokes.get(n).getTitle());
    //        joke.setStart(arrayJokes.get(n).getStart());
    //        joke.setFinish(arrayJokes.get(n).getFinish());
    //
    //
    //        if(joke != null){
    //            txt.setText(arrayJokes.get(n).getStart());
    //            txt1.setText(arrayJokes.get(n).getFinish());
    //        }

            // Alarm za notifikacija sekoj chas
            alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            intent.setAction("NotifyRcvr");
            //intent.putExtra("start",txt.getText());
            //intent.putExtra("finish",txt1.getText());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR, 11); //HOUR_OF_DAY 16
            calendar.set(Calendar.MINUTE, 42);
            calendar.set(Calendar.SECOND, 0);
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_HOUR,pendingIntent);
            createNotificationChannel();

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }


    public void createNotificationChannel() {
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID, "Jokes notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.blue(1));
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notifies every hour");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }


}