package com.moe.network.tasker.app;
import android.app.Activity;
import android.view.MotionEvent;
import android.os.Bundle;
import android.view.ViewGroup;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import com.moe.network.tasker.R;
public class DialogActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//setFinishOnTouchOutside(true);
				
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onPostCreate(savedInstanceState);
		getWindow().setLayout((int)(getWindowManager().getDefaultDisplay().getWidth()-getResources().getDimensionPixelSize(R.dimen.dialogSpace)*2),WindowManager.LayoutParams.WRAP_CONTENT);
		
	}
	
	
}
