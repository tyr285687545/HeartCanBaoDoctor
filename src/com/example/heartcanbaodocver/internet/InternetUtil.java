package com.example.heartcanbaodocver.internet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.os.StrictMode;
import android.util.Log;

public class InternetUtil {

	private HttpClient httpClient;
	private String result;
	private static HttpParams params = null;
	private static SchemeRegistry schemeRegistry = null;

	public InternetUtil() {
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().penaltyDeath()
				.build());
		httpClient = getThreadSafeClient();
	}

	private HttpClient getThreadSafeClient() {
		if (InternetUtil.params == null || schemeRegistry == null) {
			DefaultHttpClient client = new DefaultHttpClient();
			params = client.getParams();
			ClientConnectionManager mgr = client.getConnectionManager();
			InternetUtil.schemeRegistry = mgr.getSchemeRegistry();
		}

		DefaultHttpClient client = new DefaultHttpClient(
				new ThreadSafeClientConnManager(params, schemeRegistry), params);
		return client;
	}

	/**
	 * 获取数据
	 * 
	 * @param Url
	 * @return String
	 * @throws Exception
	 */
	public String postDate(String strUrl, String usn,String psw)
	{
		HttpEntityEnclosingRequestBase httpRequest = new HttpPost(strUrl);

		NameValuePair pair_1 = new BasicNameValuePair("tel",usn);
		NameValuePair pair_2 = new BasicNameValuePair("password",psw);
		
		List<NameValuePair> pairList = new ArrayList<NameValuePair>();
		
		pairList.add(pair_1);
		pairList.add(pair_2);
		
		
		try {
			httpRequest.setEntity(new UrlEncodedFormEntity(pairList,
					HTTP.UTF_8));
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequest);

			if (httpResponse.getStatusLine().getStatusCode() == 200) 
			{
				result = EntityUtils.toString(httpResponse.getEntity());
				Log.v("sky", "result = " + result);
				
			} else {
				Log.v("sky", "error");
			}
		} catch (Exception e)
		{
			System.out.println("error occurs");
		}

		return result;
	}

	public String getDate(String strUrl) 
	{

		URL url = null;
		HttpURLConnection conn = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;
		try {
			url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				baos = new ByteArrayOutputStream();
				int len = -1;
				byte[] buf = new byte[128];

				while ((len = is.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				baos.flush();
				return baos.toString();
			} else {
				throw new RuntimeException(" responseCode is not 200 ... ");
			}

		} catch (Exception e) {
			Log.e("sky", e.toString());
			e.printStackTrace();
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
			}
			try {
				if (baos != null)
					baos.close();
			} catch (IOException e) {
			}
			conn.disconnect();
		}
		return null;

	}
}
