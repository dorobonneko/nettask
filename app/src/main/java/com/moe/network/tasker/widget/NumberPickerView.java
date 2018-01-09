package com.moe.network.tasker.widget;
import android.widget.NumberPicker;
import android.content.Context;
import android.util.AttributeSet;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.TypedValue;
import android.content.res.TypedArray;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class NumberPickerView extends NumberPicker
{
	private String summary;
	private Paint paint;
	private float textSize;
	public NumberPickerView(Context context,AttributeSet attrs){
		super(context,attrs);
		setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		paint=new Paint();
		TypedArray ta=context.obtainStyledAttributes(attrs,new int[]{android.support.v7.appcompat.R.attr.colorControlNormal});
		paint.setColor(ta.getColor(0,0xff000000));
		ta.recycle();
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setTextSize(textSize);
	}

	@Override
	public void addView(View child, ViewGroup.LayoutParams params)
	{
		// TODO: Implement this method
		super.addView(child, params);
		if(child instanceof EditText)
			textSize=((EditText)child).getTextSize();
	}

	public void setSummary(String p0)
	{
		summary=p0;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if(summary!=null)
		canvas.drawText(summary,canvas.getWidth()/2-(paint.descent()+paint.ascent()),(canvas.getHeight()-paint.descent()-paint.ascent())/2,paint);
	}
	
}
