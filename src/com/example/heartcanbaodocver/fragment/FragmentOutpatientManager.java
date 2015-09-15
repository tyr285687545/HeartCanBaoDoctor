package com.example.heartcanbaodocver.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.heartcanbaodocver.R;

public class FragmentOutpatientManager extends Fragment 
{
	private View view;
	private CalendarView calendarView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = LayoutInflater.from(getActivity().getApplicationContext())
				.inflate(R.layout.fragment_outpatient_manager, null);
		initView();
		return view;
	}

	private void initView() {
		
	}
}
