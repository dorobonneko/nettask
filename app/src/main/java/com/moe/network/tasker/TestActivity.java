package com.moe.network.tasker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.moe.widget.SlideLayout;
import android.graphics.drawable.BitmapDrawable;

public class TestActivity extends AppCompatActivity implements SlideLayout.Callback
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		SlideLayout sl=((SlideLayout)findViewById(R.id.slide));
		sl.setBackground(((BitmapDrawable)getResources().getDrawable(R.drawable.test)).getBitmap());
		sl.setCallback(this);
	}

	@Override
	public void onEnter(SlideLayout s)
	{
		// TODO: Implement this method
	}

	@Override
	public void onExit(SlideLayout sl)
	{
		finish();
	}


	
}
