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
import android.view.View;
import android.widget.TextView;
import android.widget.Spinner;
import com.moe.network.tasker.widget.Toast;
import android.content.Intent;

public class NewTimer extends AppCompatActivity implements View.OnClickListener
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.timer,menu);
		return true;
	}
;
	

	@Override
	public void onClick(View p1)
	{
		
	}


	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
	}
	
}
