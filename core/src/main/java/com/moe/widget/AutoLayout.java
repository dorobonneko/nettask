package com.moe.widget;
import android.widget.FrameLayout;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

public class AutoLayout extends FrameLayout
{
	private int height;
	public AutoLayout(Context context,AttributeSet attrs){
		super(context,attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		// TODO: Implement this method
		super.onMeasure(widthMeasureSpec, height);
		setMeasuredDimension(widthMeasureSpec,height);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		int top=0,left=0,max=0;
		for(int i=0;i<getChildCount();i++){
			View child=getChildAt(i);
			if(left+child.getMeasuredWidth()>getMeasuredWidth()){
				//换行
				left=0;
				top+=max;
				max=0;
				}
			max=Math.max(max,child.getMeasuredHeight());
			child.layout(left,top,left+=child.getMeasuredWidth(),top+child.getMeasuredHeight());
			//left+=child.getMeasuredWidth();
		}
		height=top+max;
		
	}
	
}
