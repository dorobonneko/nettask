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
import com.moe.network.tasker.utils.NumberUtils;
import android.content.Intent;

public class NewTask extends DialogActivity implements View.OnClickListener,Spinner.OnItemSelectedListener
{

	private Spinner userAgent,method;
	private EditText url,custom_userAgent,name;
	private CheckBox useProxy,useRandom;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtask);
		findViewById(R.id.cancel).setOnClickListener(this);
		findViewById(R.id.add).setOnClickListener(this);
		userAgent=(Spinner) findViewById(R.id.newtask_userAgent);
		userAgent.setOnItemSelectedListener(this);
		custom_userAgent=(EditText) findViewById(R.id.newtask_customUserAgent);
		method=(Spinner) findViewById(R.id.newtask_method);
		url=(EditText) findViewById(R.id.newtask_url);
		useProxy=(CheckBox) findViewById(R.id.newtask_useProxy);
		useRandom=(CheckBox)findViewById(R.id.newtask_useRandom);
		name=(EditText) findViewById(R.id.newtask_name);
		if(savedInstanceState!=null){
			method.setSelection(savedInstanceState.getInt("method"));
			userAgent.setSelection(savedInstanceState.getInt("useragent"));
			url.setText(savedInstanceState.getCharSequence("url"));
			useProxy.setChecked(savedInstanceState.getBoolean("proxy"));
			custom_userAgent.setText(savedInstanceState.getCharSequence("custom_useragent"));
			name.setText(savedInstanceState.getCharSequence("name"));
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
		outState.putCharSequence("name",name.getText());
	}
	

	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case R.id.cancel:
				setResult(RESULT_CANCELED);
				finish();
				break;
			case R.id.add:
				Task task=new Task();
				task.setMethod(method.getSelectedItem().toString());
				task.setName(name.getText().toString());
				task.setUrl(url.getText().toString());
				task.setUseProxy(useProxy.isChecked());
				task.setUseRandom(useRandom.isChecked());
				task.setUserAgent((userAgent.getSelectedItemPosition()==4?custom_userAgent.getText().toString():getResources().getTextArray(R.array.useragent)[userAgent.getSelectedItemPosition()]).toString());
				setResult(RESULT_OK,new Intent().putExtra(Intent.EXTRA_RETURN_RESULT,task));
				finish();
				break;
		}
	}

	
}
