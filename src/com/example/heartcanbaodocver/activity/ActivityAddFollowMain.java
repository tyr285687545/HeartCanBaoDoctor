package com.example.heartcanbaodocver.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.heartcanbaodocver.R;
import com.example.heartcanbaodocver.adapter.AddFollowAdapter;
import com.example.heartcanbaodocver.bean.AddFollowBean;

public class ActivityAddFollowMain extends Activity
{
	private ListView lv_add_follow;
	
	private AddFollowAdapter followAdapter;
	
	private AddFollowBean addFollowBean;
	
	private List<AddFollowBean> mList = new ArrayList<AddFollowBean>();

	private List<AddFollowBean> mListSelect = new ArrayList<AddFollowBean>();
	
	private AutoCompleteTextView autoCompleteTextView;
	
	private ArrayAdapter<String> autoadapter;

	private String[] name;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_add_follow_main);
		initView();
	}

	private void initView() 
	{
		autoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.tv_add_follow);
		lv_add_follow = (ListView) findViewById(R.id.lv_add_follow);
		for (int i = 0; i < 11; i++) 
		{
			addFollowBean = new AddFollowBean();
			addFollowBean.setAge(i+"");
			if (i%2 == 0)
			{
				addFollowBean.setSex("ÄÐ");
				addFollowBean.setCheck(false);
			}else {
				addFollowBean.setSex("Å®");
				addFollowBean.setCheck(true);
			}
			addFollowBean.setName("ÕÔÁù"+i);
			
			mList.add(addFollowBean);
		}
		followAdapter = new AddFollowAdapter(mList, getApplicationContext());
		lv_add_follow.setAdapter(followAdapter);
		
		name = new String[mList.size()];
		for (int i = 0; i < mList.size(); i++)
		{
			name[i] = mList.get(i).getName();
		}
		
		autoadapter = new ArrayAdapter<String>(ActivityAddFollowMain.this,
				android.R.layout.simple_dropdown_item_1line, name);

		autoCompleteTextView.setAdapter(autoadapter);
		autoCompleteTextView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) 
			{
				for (int i = 0; i <mList.size(); i++)
				{
					if (autoCompleteTextView.getText().toString().equals(mList.get(i).getName())) 
					{
						mListSelect.add(mList.get(i));
					}
				}
				if (mListSelect.size()>0 )
				{
					followAdapter.setData(mListSelect);
				}
			}
		});
	}
}
