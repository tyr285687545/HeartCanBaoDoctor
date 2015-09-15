package com.example.heartcanbaodocver.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.heartcanbaodocver.R;
import com.example.heartcanbaodocver.activity.ActivityAddFollowMain;
import com.example.heartcanbaodocver.adapter.AppoinTreatAdapter;
import com.example.heartcanbaodocver.adapter.FollowAdapter;
import com.example.heartcanbaodocver.bean.AppoinTreatBean;
import com.example.heartcanbaodocver.bean.FollowBean;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentFollow extends Fragment {
	private View view;
	private ListView lv_follow;

	private FollowAdapter adapter;
	private FollowBean bean;
	private List<FollowBean> mList = new ArrayList<FollowBean>();
	private TextView tv_top;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity().getApplicationContext())
				.inflate(R.layout.fragment_follow, null);
		initView();
		return view;
	}

	private void initView() {
		lv_follow = (ListView) view.findViewById(R.id.follow_treat_listview);

		if (mList.size() > 0) {
			mList.clear();
		}
		for (int i = 0; i < 20; i++) {
			bean = new FollowBean();
			bean.setName("李四" + i);
			bean.setOrder(i + "");
			bean.setState_info("状态:已确定");

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 上午");

			Date curDate = new Date(System.currentTimeMillis());// 获取当前时间

			String str = formatter.format(curDate);

			bean.setTime(str);

			mList.add(bean);
		}
		adapter = new FollowAdapter(mList, getActivity());

		lv_follow.setAdapter(adapter);

		/**
		 * 获取TabHostManager中头部添加按钮对象
		 * */
		tv_top = (TextView) getActivity().findViewById(R.id.layout_head)
				.findViewById(R.id.function_head_remine);

		tv_top.setOnClickListener(new OnClickListener() 
		{
			public void onClick(View v) 
			{
				Intent intent = new Intent(getActivity(),
						ActivityAddFollowMain.class);
				startActivity(intent);
			}
		});
	}
}
