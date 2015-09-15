package com.example.heartcanbaodocver.activity;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.heartcanbaodocver.R;
import com.example.heartcanbaodocver.bean.User;
import com.example.heartcanbaodocver.internet.InternetUrl;
import com.example.heartcanbaodocver.internet.InternetUtil;
import com.example.heartcanbaodocver.util.MyProgressDialog;
import com.example.heartcanbaodocver.view.TabHostManager;

public class ActivityLogin extends Activity {
	private Button btn_login;
	private EditText edit_userName;
	private EditText edit_psw;

	// private SPUtils spUtils;

	private SharedPreferences mySharedPreferences;

	public static MyProgressDialog myProgressDialog;

	public static InternetUtil internetUtil;

	private String userName;
	private String psw;

	private String status;
	private String loginErrorMsg;
	private String loginResult;
	public static User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);

		initView();
		initClickEvent();

		internetUtil = new InternetUtil();
	}

	private void initView() {
		btn_login = (Button) findViewById(R.id.login_btn);
		edit_userName = (EditText) findViewById(R.id.et_login_id);
		edit_psw = (EditText) findViewById(R.id.et_login_password);

		mySharedPreferences = getSharedPreferences("SHARE_DATA",
				Activity.MODE_PRIVATE);

		edit_userName.setText(mySharedPreferences.getString("userName", "")
				.toString());
		edit_psw.setText(mySharedPreferences.getString("psw", "").toString());

		myProgressDialog = new MyProgressDialog(ActivityLogin.this);
	}

	private void initClickEvent() {
		btn_login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new thread().execute();
			}
		});
	}

	class thread extends AsyncTask<Void, Void, String> {
		@Override
		protected void onPreExecute() {
			myProgressDialog.showDialog("’˝‘⁄µ«¬Ω");
		}

		@Override
		protected String doInBackground(Void... params) {
			user = new User();

			if ((edit_userName.getText().toString().trim().length() > 0)
					&& (edit_psw.getText().toString().trim().length() > 0)) {
				userName = edit_userName.getText().toString().trim();
				psw = edit_psw.getText().toString().trim();

				SharedPreferences mySharedPreferences = getSharedPreferences(
						"SHARE_DATA", Activity.MODE_PRIVATE);
				SharedPreferences.Editor editor = mySharedPreferences.edit();

				editor.putString("userName", userName);
				editor.putString("psw", psw);
				editor.commit();

				user.setUserName(userName);
				user.setPsw(psw);

				ArrayList<BasicNameValuePair> replyParam = new ArrayList<BasicNameValuePair>();

				BasicNameValuePair nameValuePair_usN = new BasicNameValuePair(
						"tel", userName);
				BasicNameValuePair nameValuePair_psw = new BasicNameValuePair(
						"password", psw);

				replyParam.add(nameValuePair_usN);
				replyParam.add(nameValuePair_psw);

				loginResult = internetUtil.postDate(InternetUrl.Login,
						userName, psw);
				return null;

			} else {
				Toast.makeText(ActivityLogin.this, "«Î ‰»Î’À∫≈ªÚ√‹¬Î!!",
						Toast.LENGTH_LONG).show();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			try {
				JSONObject msgObject = new JSONObject(loginResult);

				status = msgObject.getString("status");

				loginErrorMsg = msgObject.getString("msg");

				user.setId(msgObject.getString("id"));

			} catch (Exception e) {
				Log.e("sky", "e.toString() = " + e.toString());
			}
			if (status.equals("0")) {
				startActivity(new Intent(ActivityLogin.this,
						TabHostManager.class));
			} else {
				Toast.makeText(ActivityLogin.this, loginErrorMsg,
						Toast.LENGTH_LONG).show();
			}
			myProgressDialog.dismissDiaolg();
		}

	}
}
