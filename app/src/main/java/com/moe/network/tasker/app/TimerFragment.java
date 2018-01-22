package com.moe.network.tasker.app;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.os.Bundle;
import android.view.ViewGroup;
import com.moe.network.tasker.R;
import com.moe.widget.SlideLayout;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import com.moe.app.FloatFragment;
import com.moe.network.tasker.widget.NumberPickerView;
import com.moe.network.tasker.entity.Timer;
import android.widget.TextView;
import android.widget.Spinner;
import com.moe.network.tasker.widget.Toast;
import java.util.Calendar;
import com.moe.network.tasker.utils.NumberUtils;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuInflater;

public class TimerFragment extends FloatFragment implements View.OnClickListener
{
	private NumberPickerView hour,minute,second;
	private Timer timer;
	private TextView name,summary,task;
	private Spinner mode;
	private Toast toast;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		return inflater.inflate(R.layout.newtimer,container,false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		if(savedInstanceState==null){
			if(timer==null){
				timer=new Timer();
				Calendar cal=Calendar.getInstance();
				timer.setTime(NumberUtils.setTimeHour(timer.getTime(),cal.get(cal.HOUR_OF_DAY)));
				timer.setTime(NumberUtils.setTimeMinute(timer.getTime(),cal.get(cal.MINUTE)));
				timer.setTime(NumberUtils.setTimeSecond(timer.getTime(),cal.get(cal.SECOND)));
			}
		}else
			timer=savedInstanceState.getParcelable("timer");
		hour=(NumberPickerView)view.findViewById(R.id.hour);
		minute=(NumberPickerView)view.findViewById(R.id.minute);
		second=(NumberPickerView)view.findViewById(R.id.second);
		hour.setMaxValue(23);
		hour.setValue(NumberUtils.getTimeHour(timer.getTime()));
		hour.setSummary("时");
		minute.setMaxValue(59);
		minute.setValue(NumberUtils.getTimeMinute(timer.getTime()));
		minute.setSummary("分");
		second.setMaxValue(59);
		second.setValue(NumberUtils.getTimeSecond(timer.getTime()));
		second.setSummary("秒");
		name=(TextView) view.findViewById(R.id.timer_name);
		summary=(TextView) view.findViewById(R.id.timer_summary);
		task=(TextView) view.findViewById(R.id.timer_selected_task);
		mode=(Spinner) view.findViewById(R.id.timer_mode);
		task.setOnClickListener(this);
		if(savedInstanceState!=null){
			summary.setText(savedInstanceState.getCharSequence("summary"));
			task.setText(timer.getTask()==null?null:timer.getTask().getName());
			mode.setSelection(savedInstanceState.getInt("mode"));
		}
		toast=new Toast(getContext());
		super.onViewCreated(view,savedInstanceState);
	}

	
	@Override
	public SlideLayout getSlideLayout(View parent)
	{
		SlideLayout sl= (SlideLayout)parent.findViewById(R.id.slide);
		//sl.setBackground(((BitmapDrawable)getResources().getDrawable(R.drawable.test)).getBitmap());
		return sl;
	}


	

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		setHasOptionsMenu(true);
		super.onActivityCreated(savedInstanceState);
		onHiddenChanged(false);
	}

	

	@Override
	public void onHiddenChanged(boolean hidden)
	{
		if(!hidden){
			Toolbar toolbar=(Toolbar)getView().findViewById(R.id.toolbar);
		((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
		((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}else{
		((AppCompatActivity)getActivity()).setSupportActionBar((Toolbar)getActivity().findViewById(R.id.toolbar));
		}
	}

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		outState.putCharSequence("summary",summary.getText());
		outState.putInt("mode",mode.getSelectedItemPosition());
		outState.putParcelable("timer",timer);
		super.onSaveInstanceState(outState);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId()){
			case android.R.id.home:
				hide();
				break;
			case R.id.save:
				boolean flag=true;
				if(timer.getTask()==null){
					flag=false;
					toast.setMessage("必须选择一个任务").show();
				}
				if(flag){
					timer.setSummary(summary.getText().toString());
					timer.setTime(NumberUtils.setTimeHour(timer.getTime(),hour.getValue()));
					timer.setTime(NumberUtils.setTimeMinute(timer.getTime(),minute.getValue()));
					timer.setTime(NumberUtils.setTimeSecond(timer.getTime(),second.getValue()));
					timer.setTime(NumberUtils.setModePosition(timer.getTime(),mode.getSelectedItemPosition()));
					//setResult(RESULT_OK,new Intent().putExtra(Intent.EXTRA_RETURN_RESULT,timer));
					//finish();
					hide();
				}
				break;
		}
		return true;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		menu.removeGroup(0);
		inflater.inflate(R.menu.timer,menu);
		}

	
}
