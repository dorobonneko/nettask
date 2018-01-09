package com.moe.network.tasker;
import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;
import com.moe.network.tasker.app.DialogActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.CheckBox;
import com.moe.network.tasker.entity.Task;

public class NewTask extends DialogActivity implements View.OnClickListener,Spinner.OnItemSelectedListener
{

	private Spinner userAgent,method;
	private EditText url,custom_userAgent;
	private CheckBox useProxy,useRandom;
	private Task task;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtask);
		findViewById(R.id.cancel).setOnClickListener(this);
		userAgent=(Spinner) findViewById(R.id.newtask_userAgent);
		userAgent.setOnItemSelectedListener(this);
		custom_userAgent=(EditText) findViewById(R.id.newtask_customUserAgent);
		method=(Spinner) findViewById(R.id.newtask_method);
		url=(EditText) findViewById(R.id.newtask_url);
		useProxy=(CheckBox) findViewById(R.id.newtask_useProxy);
		useRandom=(CheckBox)findViewById(R.id.newtask_useRandom);
		if(savedInstanceState!=null){
			method.setSelection(savedInstanceState.getInt("method"));
			userAgent.setSelection(savedInstanceState.getInt("useragent"));
			url.setText(savedInstanceState.getCharSequence("url"));
			useProxy.setChecked(savedInstanceState.getBoolean("proxy"));
			custom_userAgent.setText(savedInstanceState.getCharSequence("custom_useragent"));
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
	{
		switch(p1.getId()){
			case R.id.newtask_userAgent:
			if(p3==4){
				custom_userAgent.setVisibility(View.VISIBLE);
			}else{
				custom_userAgent.setVisibility(View.GONE);
			}
			break;
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> p1)
	{
		
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putInt("method",method.getSelectedItemPosition());
		outState.putInt("useragent",userAgent.getSelectedItemPosition());
		outState.putCharSequence("url",url.getText());
		outState.putCharSequence("custom_useragent",custom_userAgent.getText());
		outState.putBoolean("proxy",useProxy.isChecked());
	}
	

	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.cancel:
				finish();
				break;
			case R.id.add:
				break;
		}
	}

	
}
