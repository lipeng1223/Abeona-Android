package com.league.abeona.view;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;


@SuppressWarnings("deprecation")
public class ProgressDlg extends Application {

	
	@SuppressWarnings("deprecation")
	public static ProgressDialog pd;

	public static void showProcess(Context context, String message) {
		closeprocess(context);
		pd = new ProgressDialog(context);
		pd.setMessage(message);
		pd.setCancelable(false);
		if (pd != null) {
			pd.show();
		}
	}

	public static void closeprocess(Context context) {
		if (pd != null) {
			pd.dismiss();
			pd = null;
		}
	}
}
