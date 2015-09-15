package com.example.heartcanbaodocver.util;

import android.app.ProgressDialog;
import android.content.Context;

public class MyProgressDialog 
{
	private Context mContext;
	private String title;
	private ProgressDialog dialog;
	
	
	public MyProgressDialog(Context mContext)
	{
		this.mContext = mContext;
	}
	
	public void showDialog(String msg)
	{
		dialog = new ProgressDialog(mContext);
		dialog.setMessage(msg);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
	}
	
	public void showDialogWithTitle(String msg,String title)
	{
		dialog = new ProgressDialog(mContext);
		dialog.setMessage(msg);
		dialog.setCancelable(true);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setTitle(title);
		dialog.show();
	}
	
	public void dismissDiaolg() 
	{
		if (dialog.isShowing()) 
		{
			dialog.dismiss();
		}
	}
}
