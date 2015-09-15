package com.example.heartcanbaodocver.adapter;

import java.util.List;

import com.example.heartcanbaodocver.R;
import com.example.heartcanbaodocver.bean.FollowBean;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class FollowAdapter extends BaseAdapter
{

	private List<FollowBean> mList;
	private Context mContext;
	
	LayoutInflater inflater;
	
	public FollowAdapter(List<FollowBean> mList, Context mContext) {
		this.mList = mList;
		this.mContext = mContext;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount()
	{
		return mList.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView = inflater.inflate(R.layout.follow_lv_item, null);
		
		TextView tv_order = (TextView)convertView.findViewById(R.id.follow_item_tv_order);
		TextView tv_name = (TextView)convertView.findViewById(R.id.follow_item_tv_name);
		TextView tv_time = (TextView)convertView.findViewById(R.id.follow_item_tv_time);
		TextView tv_state = (TextView)convertView.findViewById(R.id.follow_item_tv_state);
		
		tv_order.setText(mList.get(position).getOrder());
		tv_name.setText(mList.get(position).getName());
		tv_time.setText(mList.get(position).getTime());
		tv_state.setText(mList.get(position).getState_info());
		
		return convertView;
	}

}
