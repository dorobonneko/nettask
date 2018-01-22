package com.moe.widget;
import android.widget.TextView;
import android.support.v4.view.TintableBackgroundView;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.content.Context;
import android.support.v7.widget.TintTypedArray;
import com.moe.framework.R;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.TintInfo;

public class MoeTextView extends TextView implements TintableBackgroundView
{
	public MoeTextView(Context context,AttributeSet attrs){
		super(context,attrs);
		TintTypedArray mTintTypedArray=TintTypedArray.obtainStyledAttributes(context,attrs,R.styleable.MoeTheme);
		mTintTypedArray.getInt(R.styleable.MoeTheme_textColor,1);
	}
	@Override
	public void setSupportBackgroundTintList(ColorStateList p1)
	{
		
	}

	@Override
	public ColorStateList getSupportBackgroundTintList()
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public void setSupportBackgroundTintMode(PorterDuff.Mode p1)
	{
		// TODO: Implement this method
	}

	@Override
	public PorterDuff.Mode getSupportBackgroundTintMode()
	{
		// TODO: Implement this method
		return null;
	}
	
}
