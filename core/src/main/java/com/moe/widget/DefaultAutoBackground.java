package com.moe.widget;
import android.view.ViewTreeObserver;
import android.view.View;
import android.graphics.Rect;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import com.moe.utils.BitmapUtils;
import android.os.Build;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;

public class DefaultAutoBackground extends AutoBackground
{
	public DefaultAutoBackground(View v,Activity activity){
		super(v,activity);
	}

	@Override
	public void onBitmapCreated(Bitmap bit)
	{
		ViewCompat.setBackground(getView(),new BitmapDrawable(bit));
	}

	
}
