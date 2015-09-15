package com.example.heartcanbaodocver.fragment;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heartcanbaodocver.R;
import com.example.heartcanbaodocver.adapter.AppoinTreatAdapter;
import com.example.heartcanbaodocver.bean.AppoinTreatBean;
import com.example.heartcanbaodocver.view.TabHostManager;

@SuppressLint("NewApi")
public class FragmentAppoin extends Fragment {
	private View view;

	private RadioButton btn_treat;
	private RadioButton btn_agree;
	private RadioButton btn_refuse;

	private ListView lv_treat;

	private AppoinTreatAdapter treatAdapter;

	public static List<AppoinTreatBean> agreeList = new ArrayList<AppoinTreatBean>();
	public static List<AppoinTreatBean> treatList = new ArrayList<AppoinTreatBean>();
	public static List<AppoinTreatBean> refuseList = new ArrayList<AppoinTreatBean>();

	private TextView tv_top;

	
	
	public static Handler appoinHandler = new Handler(){
	
		public void handleMessage(android.os.Message msg) {
			switch (msg.arg1)
			{
			case 0:
				reSetList();
				break;
			case 1:
				
				break;
			case 2:
				
				break;
			}
		};
	};
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity().getApplicationContext())
				.inflate(R.layout.fragment_appoin, null);
		initView();
		initValue();

		treatAdapter.notifyDataSetChanged();

		return view;
	}

	private void initView() {
		btn_treat = (RadioButton) view.findViewById(R.id.radio_treat);
		btn_agree = (RadioButton) view.findViewById(R.id.radio_agree);
		btn_refuse = (RadioButton) view.findViewById(R.id.radio_refused);

		lv_treat = (ListView) view.findViewById(R.id.appoin_treat_listview);

		virtualData();

		tv_top = (TextView) getActivity().findViewById(R.id.layout_head)
				.findViewById(R.id.function_head_remine);

		tv_top.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(getActivity(), "fragment_appoin",
						Toast.LENGTH_LONG).show();
			}
		});
	}

	private void initValue() {
		btn_treat.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				virtualData();
			}
		});

		btn_agree.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				reSetList();
				treatAdapter.setData(agreeList);
			}
		});

		btn_refuse.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				reSetList();
				treatAdapter.setData(refuseList);
			}
		});

	}

	private void virtualData() {
		reSetList();
		treatAdapter = new AppoinTreatAdapter(treatList, getActivity());
		lv_treat.setAdapter(treatAdapter);
	}

	@Override
	public void onResume() {
		super.onResume();
		treatAdapter.notifyDataSetChanged();
	}

	private static void reSetList() 
	{
		treatList.clear();
		agreeList.clear();
		refuseList.clear();
		
		for (int i = 0; i < TabHostManager.appoinTreatBeans.size(); i++) {
			if (TabHostManager.appoinTreatBeans.get(i).getStatus().equals("0")) {
				treatList.add(TabHostManager.appoinTreatBeans.get(i));
			} else if (TabHostManager.appoinTreatBeans.get(i).getStatus()
					.equals("1")) {
				agreeList.add(TabHostManager.appoinTreatBeans.get(i));
			} else if (TabHostManager.appoinTreatBeans.get(i).getStatus()
					.equals("2")) {
				refuseList.add(TabHostManager.appoinTreatBeans.get(i));
			}
		}
	}
}
