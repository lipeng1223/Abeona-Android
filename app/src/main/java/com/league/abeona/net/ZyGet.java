package com.league.abeona.net;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class ZyGet {
	private static final String CHARSET = "UTF-8";
	private static final String TAG = "Http";

	private static final int GET_STARTING = 0;
	private static final int GET_STOPED = 2;

	private HttpGet httpGet = null;
	private DefaultHttpClient defaultHttpClient = null;
	private int state = GET_STARTING;

	public void closeGet() {
		try {
			state = GET_STOPED;
			if (httpGet != null) {
				if (!httpGet.isAborted()) {
					httpGet.abort();
					if (defaultHttpClient != null) {
						defaultHttpClient.getConnectionManager().shutdown();
					}
				}
			}
			httpGet = null;
			defaultHttpClient = null;
		} catch (Exception e) {
			Log.e(TAG, "closePost error");
		}
	}

	public void startGet(final Context context, final String url,
			final Map<String, String> map, final INetCallBack callback) {
		closeGet();
		new Thread(new Runnable() {
			public void run() {
				state = GET_STARTING;
				final String result = doGet(url, map);
				if (state == GET_STARTING) {
					state = GET_STOPED;
					((Activity) context).runOnUiThread(new Runnable() {
						public void run() {
							callback.onComplete(result, null);
						}
					});
				}
			}
		}).start();
	}

	private String doGet(String url, Map<String, String> map) {
		Log.e("do post", "url:" + url);
		try {
			defaultHttpClient = new DefaultHttpClient();
			httpGet = new HttpGet(url);
			HttpParams httpParams = new BasicHttpParams();

			HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(httpParams, CHARSET);
			HttpProtocolParams.setUseExpectContinue(httpParams, false);
			
			HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
			HttpConnectionParams.setSoTimeout(httpParams, 10000);
			httpGet.setParams(httpParams);

			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory
					.getSocketFactory(), 443));
			ThreadSafeClientConnManager conMgr = new ThreadSafeClientConnManager(
					httpParams, schReg);
			defaultHttpClient = new DefaultHttpClient(conMgr, httpParams);

			List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			if (map != null) {
				for (Map.Entry<String, String> entry : map.entrySet()) {
					Log.e(TAG,  entry.getKey());
					Log.e(TAG,  entry.getValue());
					BasicNameValuePair valuePair = new BasicNameValuePair(
							entry.getKey(), entry.getValue());
					nameValuePairs.add(valuePair);
				}
			}
			UrlEncodedFormEntity  httpEntity = new UrlEncodedFormEntity(nameValuePairs,
					HTTP.UTF_8);
		
			httpEntity.setContentType("application/x-www-form-urlencoded");
//			httpGet.setEntity(httpEntity);

			HttpResponse httpResponse = defaultHttpClient.execute(httpGet);
			if (httpResponse == null) {
				Log.e(TAG, "http response result null");
				return null;
			}
			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(httpResponse.getEntity(),
						HTTP.UTF_8);
				Log.e(TAG, "http response sucessful");
				if (result == null || "".equals(result)) {
					Log.e(TAG,
							"request was sucessful, but paser value was null or empty");
				}
				Log.e(TAG, "respnse result:" + result);
				return result;
			} else {
				Log.e(TAG, "http response code:"
						+ httpResponse.getStatusLine().getStatusCode());
				return null;
			}
		} catch (ConnectTimeoutException e) {
			Log.e(TAG, "connection time out exception");
		} catch (ClientProtocolException e) {
			Log.e(TAG, "client protocol exception" + e.getMessage());
		} catch (Exception e) {
			Log.e(TAG, "exception" + e.getMessage());
		}
		return null;
	}

}
