package com.example.heartcanbaodocver.view;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.heartcanbaodocver.R;
import com.example.heartcanbaodocver.activity.ActivityLogin;
import com.example.heartcanbaodocver.bean.AppoinTreatBean;
import com.example.heartcanbaodocver.fragment.FragmentAppoin;
import com.example.heartcanbaodocver.fragment.FragmentFollow;
import com.example.heartcanbaodocver.fragment.FragmentOutpatientManager;
import com.example.heartcanbaodocver.internet.InternetUrl;
import com.example.heartcanbaodocver.util.MyProgressDialog;

public class TabHostManager extends FragmentActivity {
	private TextView back_up;
	private TextView title;
	private TextView add;

	private FragmentManager fragmentManager;
	private FragmentTransaction transaction;

	private RadioButton map_footer_btn1;
	private RadioButton map_footer_btn2;
	private RadioButton map_footer_btn3;
	private RadioButton map_footer_btn4;
	private RadioButton map_footer_btn5;

	private FragmentFollow fragmentFollow;
	private FragmentAppoin fragmentAppoin;
	private FragmentOutpatientManager outpatientManager;

	public static List<AppoinTreatBean> appoinTreatBeans = new ArrayList<AppoinTreatBean>();

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.tabhost_main);
		appoinTreatBeans.clear();
		initView();
		new getList().execute();
	}

	public TabHostManager getIntance() 
	{
		return this;
	}

	private void initView() {
		back_up = (TextView) findViewById(R.id.layout_head).findViewById(
				R.id.function_head_menu);
		title = (TextView) findViewById(R.id.layout_head).findViewById(
				R.id.function_head_tv);
		add = (TextView) findViewById(R.id.layout_head).findViewById(
				R.id.function_head_remine);
		back_up.setVisibility(View.GONE);

		title.setText("预约管理");

		add.setText("门诊时间");
		map_footer_btn1 = (RadioButton) findViewById(R.id.layout_bottom)
				.findViewById(R.id.map_footer_btn1);
		map_footer_btn2 = (RadioButton) findViewById(R.id.layout_bottom)
				.findViewById(R.id.map_footer_btn2);
		map_footer_btn3 = (RadioButton) findViewById(R.id.layout_bottom)
				.findViewById(R.id.map_footer_btn3);
		map_footer_btn4 = (RadioButton) findViewById(R.id.layout_bottom)
				.findViewById(R.id.map_footer_btn4);
		map_footer_btn5 = (RadioButton) findViewById(R.id.layout_bottom)
				.findViewById(R.id.map_footer_btn5);

		fragmentAppoin = new FragmentAppoin();
		fragmentFollow = new FragmentFollow();
		outpatientManager = new FragmentOutpatientManager();

		map_footer_btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				fragmentManager = getSupportFragmentManager();

				transaction = fragmentManager.beginTransaction();

				transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

				transaction.replace(R.id.fragment_content, fragmentAppoin);

				back_up.setVisibility(View.GONE);

				title.setText("预约管理");

				add.setText("门诊时间");

				transaction.commit();
			}
		});
		map_footer_btn2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				fragmentManager = getSupportFragmentManager();

				transaction = fragmentManager.beginTransaction();

				transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

				transaction.replace(R.id.fragment_content, fragmentFollow);

				back_up.setVisibility(View.GONE);

				title.setText("随访记录");

				add.setText("添加");

				transaction.commit();

			}
		});
		map_footer_btn3.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				fragmentManager = getSupportFragmentManager();

				transaction = fragmentManager.beginTransaction();

				transaction
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

				transaction.replace(R.id.fragment_content, outpatientManager);

				back_up.setVisibility(View.GONE);

				title.setText("门诊管理");

				add.setVisibility(View.GONE);
				;

				transaction.commit();

			}
		});
	}

	class getList extends AsyncTask<Void, Void, Void> 
	{
		@Override
		protected void onPreExecute() 
		{
			ActivityLogin.myProgressDialog.showDialog("正在获取数据");
		}

		@Override
		protected Void doInBackground(Void... params) 
		{
			String result = ActivityLogin.internetUtil
					.getDate(InternetUrl.Order_Treat
							+ ActivityLogin.user.getUserName());
			try {
				JSONObject resultObject = new JSONObject(result);
				JSONArray array = resultObject.getJSONArray("list");
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = array.getJSONObject(i);
					String id = jsonObject.getString("id");
					String patientid = jsonObject.getString("patientid");
					String doctorid = jsonObject.getString("doctorid");
					String status = jsonObject.getString("status");
					String appdatetime = jsonObject.getString("appdatetime");
					String updatetime = jsonObject.getString("updatetime");
					String docname = jsonObject.getString("docname");
					String dochospital = jsonObject.getString("dochospital");
					String doctel = jsonObject.getString("doctel");
					String patname = jsonObject.getString("patname");
					String pattel = jsonObject.getString("pattel");
					AppoinTreatBean appoinTreatBean = new AppoinTreatBean();

					appoinTreatBean.setId(id);
					appoinTreatBean.setPatname(patientid);
					appoinTreatBean.setDoctorid(doctorid);
					appoinTreatBean.setStatus(status);
					appoinTreatBean.setAddtimg(appdatetime);
					appoinTreatBean.setUpdatetime(updatetime);
					appoinTreatBean.setDocname(docname);
					appoinTreatBean.setDochospital(dochospital);
					appoinTreatBean.setDoctel(doctel);
					appoinTreatBean.setPatname(patname);
					appoinTreatBean.setPattel(pattel);

					appoinTreatBeans.add(appoinTreatBean);
				}
			} catch (Exception e) 
			{
				
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) 
		{
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.fragment_content, new FragmentAppoin())
			.commit();
			ActivityLogin.myProgressDialog.dismissDiaolg();
			
		}
	}
}
