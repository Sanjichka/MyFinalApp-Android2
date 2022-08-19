package com.example.myappfinal;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

public class ShowDialog {

    public static void show(Context context) {
        AlertDialog.Builder alertbuilder = new AlertDialog.Builder(context);
        alertbuilder.setTitle("No Internet Connection ");
        alertbuilder.setMessage("Wifi connection is required to access this app. OK == exit !");
        alertbuilder.setCancelable(false);

        alertbuilder.setNegativeButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        ((Activity) context).finish();  // se povikuva onDestroy
                    }
                });

        AlertDialog alertdialog = alertbuilder.create();
        alertdialog.show();
    }
}
