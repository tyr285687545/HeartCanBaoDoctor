package com.example.heartcanbaodocver.adapter;

import java.util.List;

import org.json.JSONObject;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.heartcanbaodocver.R;
import com.example.heartcanbaodocver.activity.ActivityLogin;
import com.example.heartcanbaodocver.bean.AppoinTreatBean;
import com.example.heartcanbaodocver.fragment.FragmentAppoin;
import com.example.heartcanbaodocver.internet.InternetUrl;
import com.example.heartcanbaodocver.util.MyProgressDialog;
import com.example.heartcanbaodocver.view.TabHostManager;

public class AppoinTreatAdapter extends BaseAdapter {

	private List<AppoinTreatBean> beanList;
	private Context mContext;
	private LayoutInflater inflater;
	private MyProgressDialog myProgressDialog;
	private String status;
	private String msg;
	private int position_;
	
	public AppoinTreatAdapter(List<AppoinTreatBean> beanList, Context mContext) {
		this.beanList = beanList;
		this.mContext = mContext;
		inflater = LayoutInflater.from(mContext);
		myProgressDialog = new MyProgressDialog(mContext);
	}

	@Override
	public int getCount() {
		return beanList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return beanList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) 
	{
		convertView = inflater.inflate(R.layout.appoin_listview_item, null);

		TextView tv_order = (TextView) convertView
				.findViewById(R.id.item_tv_order);
		TextView tv_name = (TextView) convertView
				.findViewById(R.id.item_tv_name);
		TextView tv_time = (TextView) convertView
				.findViewById(R.id.item_tv_time);
		Button tv_state = (Button) convertView.findViewById(R.id.item_tv_state);

		tv_order.setText(String.valueOf(position+1));
		
		tv_name.setText(beanList.get(position).getPatname());
		tv_time.setText(beanList.get(position).getAddtimg());
		String stateStr = beanList.get(position).getStatus();

		if (Integer.parseInt(stateStr) == 0) 
		{
			tv_state.setText("处理");
		} else if (Integer.parseInt(stateStr) == 1)
		{
			tv_state.setText("取消");
			
		} else if(Integer.parseInt(stateStr) == 2)
		{
			tv_state.setText("同意");
		}

		tv_state.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v) {
				if (Integer.parseInt(beanList.get(position).getStatus()) == 0) 
				{
					//处理对话框
					final Dialog dialog = new Dialog(mContext);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.appoin_handle_treat_dialog);
					
					dialog.setCanceledOnTouchOutside(false);
					
					dialog.show();
					
					TextView btnDetermin =(TextView)dialog.findViewById(R.id.btn_agree); 
					
					TextView btnCancel =(TextView)dialog.findViewById(R.id.btn_refuse); 
					btnDetermin.setOnClickListener(new OnClickListener()
					{
						public void onClick(View v) 
						{
							position_ = position;
							try {
								new agreeTheOrder().execute();
							} catch (Exception e) 
							{
								Log.e("sky", e.toString());
							}
							dialog.dismiss();
						}
					});
					btnCancel.setOnClickListener(new OnClickListener()
					{
						public void onClick(View v) 
						{
							try
							{
								new DisagreeTheOrder().execute();
							} catch (Exception e) 
							{
								Log.e("sky", e.toString());
							}
							dialog.dismiss();
						}
					});
				} else if (Integer.parseInt(beanList.get(position).getStatus()) == 1)
				{
					//同意对话框
					final Dialog dialog = new Dialog(mContext);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.appoin_handle_agree_dialog);
					dialog.setCanceledOnTouchOutside(false);
					TextView tv_content = (TextView)dialog.findViewById(R.id.dialog_tv_content);
					tv_content.setText("您之前已经同意他的预约,现在要拒绝预约吗?");
					dialog.show();
					Button btnConfirm = (Button)dialog.findViewById(R.id.dialog_yes);
					btnConfirm.setOnClickListener(new OnClickListener() 
					{
						public void onClick(View v) 
						{
							position_ = position;
							new DisagreeTheOrder().execute();
							dialog.dismiss();
						}
					});
					
					Button btnCancel = (Button)dialog.findViewById(R.id.dialog_cancel);
					btnCancel.setOnClickListener(new OnClickListener() 
					{
						public void onClick(View v) 
						{
							dialog.dismiss();
						}
					});
					
				} else if(Integer.parseInt(beanList.get(position).getStatus()) == 2)
				{
					// 拒绝对话框
					final Dialog dialog = new Dialog(mContext);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.appoin_handle_agree_dialog);
					dialog.setCanceledOnTouchOutside(false);
					TextView tv_content = (TextView)dialog.findViewById(R.id.dialog_tv_content);
					tv_content.setText("您之前已经拒绝他的预约,现在要同意预约吗?");
					dialog.show();
					Button btnConfirm = (Button)dialog.findViewById(R.id.dialog_yes);
					btnConfirm.setOnClickListener(new OnClickListener() 
					{
						public void onClick(View v) 
						{
							position_ = position;
							new agreeTheOrder().execute();
							dialog.dismiss();
						}
					});
					
					Button btnCancel = (Button)dialog.findViewById(R.id.dialog_cancel);
					btnCancel.setOnClickListener(new OnClickListener() 
					{
						public void onClick(View v) 
						{
							dialog.dismiss();
						}
					});
					
				}
			}
		});
		return convertView;
	}

	public void setData(List<AppoinTreatBean> newBeans)
	{
		this.beanList = newBeans;
		this.notifyDataSetChanged();
	}
	
	class agreeTheOrder extends AsyncTask<Void, Void, Void>
	{
		
		@Override
		protected void onPreExecute()
		{
			myProgressDialog.showDialog("正在发送请求...");
		}
		@Override
		protected Void doInBackground(Void... params)
		{
			String s = ActivityLogin.internetUtil.getDate(InternetUrl.Order_Agree+beanList.get(position_).getId());
			try {
				JSONObject jsonObject = new JSONObject(s);
				
				status = jsonObject.getString("status");
				msg = jsonObject.getString("msg");
				
			} catch (Exception e)
			{
				Log.e("sky", "同意预约解析错误: "+e.toString());
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) 
		{
			myProgressDialog.dismissDiaolg();
			if (status.equals(0))
			{
				for (int i = 0; i < TabHostManager.appoinTreatBeans.size(); i++) 
				{
					if (TabHostManager.appoinTreatBeans.get(i).getId().equals(beanList.get(position_).getId())) 
					{
						TabHostManager.appoinTreatBeans.get(i).setStatus(1+"");
					}
				}
				Message message = new Message();
				message.arg1 = 0;
				FragmentAppoin.appoinHandler.sendMessage(message);
			}
			Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
		}
		
	}
	
	class DisagreeTheOrder extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected void onPreExecute()
		{
			myProgressDialog.showDialog("正在发送请求...");
		}
		@Override
		protected Void doInBackground(Void... params)
		{
			String s = ActivityLogin.internetUtil.getDate(InternetUrl.Order_Refuse+beanList.get(position_).getId());
			try {
				JSONObject jsonObject = new JSONObject(s);
				
				status = jsonObject.getString("status");
				msg = jsonObject.getString("msg");
				
			} catch (Exception e)
			{
				Log.e("sky", "同意预约解析错误: "+e.toString());
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result) 
		{
			myProgressDialog.dismissDiaolg();
			if (status.equals(0))
			{
				TabHostManager.appoinTreatBeans.get(position_).setStatus(3+"");
				setData(beanList);
			}
			Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
		}
		
	}
}
