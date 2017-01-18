package com.league.abeona.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by ZFeng on 6/16/2015.
 */
public class MessageDialog {

    public static void showAlertMsg(Context context, String Title, String Content)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        // Setting Dialog Title
        alertDialog.setTitle(Title);

        // Setting Dialog Message
        alertDialog.setMessage(Content);

        // Setting OK Button
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to execute after dialog closed
                //Do nothing
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }
}
