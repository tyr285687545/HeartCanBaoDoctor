package com.example.heartcanbaodocver.adapter;

import java.util.List;

import com.example.heartcanbaodocver.R;
import com.example.heartcanbaodocver.bean.AddFollowBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class AddFollowAdapter extends BaseAdapter
{
	private List<AddFollowBean> mList;
	
	private Context mContext;
	
	private LayoutInflater inflater;
	public AddFollowAdapter(List<AddFollowBean> mList, Context mContext) 
	{
		this.mList = mList;
		this.mContext = mContext;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() 
	{
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) 
	{
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		convertView = inflater.inflate(R.layout.activity_add_follow_lv_item, null);
		
		ImageView img = (ImageView)convertView.findViewById(R.id.img_head);
		
		TextView tv_name = (TextView)convertView.findViewById(R.id.follow_add_lv_item_tv_name);
		
		TextView tv_sex = (TextView)convertView.findViewById(R.id.follow_add_lv_item_tv_sex);
		
		TextView tv_age = (TextView)convertView.findViewById(R.id.follow_add_lv_item_tv_age);
		
		CheckBox cb = (CheckBox)convertView.findViewById(R.id.follow_add_lv_item_cb);
		
		tv_name.setText(mList.get(position).getName());
		tv_sex.setText(mList.get(position).getSex()+",");
		tv_age.setText(mList.get(position).getAge()+"Ëê");
		
		if (mList.get(position).isCheck())
		{
			cb.setChecked(true);
		}else {
			cb.setChecked(false);
		}

		return convertView;
	}
	
	public void setData(List<AddFollowBean> mList)
	{
		this.mList = mList;
		this.notifyDataSetChanged();
	}
}
