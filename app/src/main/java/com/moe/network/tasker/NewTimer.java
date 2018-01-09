package com.moe.network.tasker;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Menu;
import com.moe.network.tasker.widget.NumberPickerView;
import java.util.Calendar;
import com.moe.network.tasker.entity.Timer;
import com.moe.network.tasker.utils.NumberUtils;

public class NewTimer extends AppCompatActivity
{
	private NumberPickerView hour,minute,second;
	private Timer timer;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		if(savedInstanceState==null){
			timer=getIntent().getParcelableExtra("timer");
			if(timer==null){
				timer=new Timer();
				Calendar cal=Calendar.getInstance();
				timer.setTime(NumberUtils.setTimeHour(timer.getTime(),cal.get(cal.HOUR_OF_DAY)));
				timer.setTime(NumberUtils.setTimeMinute(timer.getTime(),cal.get(cal.MINUTE)));
				timer.setTime(NumberUtils.setTimeSecond(timer.getTime(),cal.get(cal.SECOND)));
			}
		}else
		timer=savedInstanceState.getParcelable("timer");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.newtimer);
		setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		hour=(NumberPickerView)findViewById(R.id.hour);
		minute=(NumberPickerView)findViewById(R.id.minute);
		second=(NumberPickerView)findViewById(R.id.second);
		hour.setMaxValue(23);
		hour.setValue(NumberUtils.getTimeHour(timer.getTime()));
		hour.setSummary("时");
		minute.setMaxValue(59);
		minute.setValue(NumberUtils.getTimeMinute(timer.getTime()));
		minute.setSummary("分");
		second.setMaxValue(59);
		second.setValue(NumberUtils.getTimeSecond(timer.getTime()));
		second.setSummary("秒");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.timer,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId()){
			case android.R.id.home:
				finish();
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		outState.putParcelable("timer",timer);
		super.onSaveInstanceState(outState);
	}
	
}
