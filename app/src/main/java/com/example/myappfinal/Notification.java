package com.example.myappfinal;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myappfinal.Models.Jokes;
import com.example.myappfinal.Models.Weather;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Notification extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationManagerCompat notificationManager =  NotificationManagerCompat.from(context);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID);
        String array = intent.getStringExtra("arrayJokes");
        String array1 = intent.getStringExtra("arrayWeather");
        ArrayList<Jokes> arrayJokes = toJsonArrayJokes.dataParsing(array);
        ArrayList<Weather> arrayWeather = toJsonArrayWeather.dataParsing(array1);
        ArrayList<Weather> SelectedArrayWeather = new ArrayList<>();
        Weather selectedWeather = new Weather();

        Random r = new Random();
        int n = r.nextInt(arrayJokes.size());
        int n1 = r.nextInt(4);

        Jokes joke = new Jokes();
        joke.setCategory(arrayJokes.get(n).getCategory());
        joke.setTitle(arrayJokes.get(n).getTitle());
        joke.setStart(arrayJokes.get(n).getStart());
        joke.setFinish(arrayJokes.get(n).getFinish());

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTime = sdf.format(new Date());
        Log.i("VREME", currentTime);

        int goodWeather = 0;

        String chas = currentTime.charAt(0) + "" + currentTime.charAt(1);

        if((Integer.parseInt(chas)>=10 && Integer.parseInt(chas)<=12) || (Integer.parseInt(chas)>=16 && Integer.parseInt(chas)<=21)) {
            int k = 0;
            for(int i=0; i<arrayWeather.size(); i++) {
                if(arrayWeather.get(i).getTime().equals(chas)) {
                    Weather weather = new Weather();
                    weather.setTime(arrayWeather.get(i).getTime());
                    weather.setDegrees(arrayWeather.get(i).getDegrees());
                    SelectedArrayWeather.add(weather);
                    k++;
                }
                if(k==4) break;
            }

            selectedWeather = SelectedArrayWeather.get(n1);
            Log.i("VREME","" + selectedWeather.getDegrees());
            if(Integer.parseInt(selectedWeather.getDegrees())>=20 && Integer.parseInt(selectedWeather.getDegrees())<=30) {
                goodWeather = 1;
            }

        }

        if(Integer.parseInt(chas)<8) {
            Log.i("VREME","vreme za spienje, nema jokes");
        }
        else {
            Intent contentIntent = new Intent(context,ClickNotificationActivity.class);
            contentIntent.putExtra("start", joke.getStart());
            contentIntent.putExtra("finish", joke.getFinish());
            if(goodWeather == 1)
                contentIntent.putExtra("weather", "yes");
            else
                contentIntent.putExtra("weather", "no");
            PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            if(goodWeather == 1) {
                notification.setContentTitle("Go out ^_^");
                notification.setContentText("Weather is nice, it's "+selectedWeather.getDegrees()+" degrees outside");
            }
            else {
                notification.setContentTitle(joke.getStart());
                notification.setContentText(joke.getFinish());
            }

            notification.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
            notification.setPriority(NotificationCompat.PRIORITY_HIGH);
            notification.setDefaults(NotificationCompat.DEFAULT_ALL);

            notification.setContentIntent(pendingIntent);
            notification.setAutoCancel(true);
            notificationManager.notify(NOTIFICATION_ID,notification.build());
        }


    }

}