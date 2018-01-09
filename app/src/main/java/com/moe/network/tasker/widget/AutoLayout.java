package com.moe.network.tasker.widget;
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
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec,height);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b)
	{
		int top=0,left=0,max=0;
		for(int i=0;i<getChildCount();i++){
			View child=getChildAt(i);
			max=Math.max(max,child.getMeasuredHeight());
			if(left+child.getMeasuredWidth()>getMeasuredWidth()){
				left=0;
				top+=max;
				max=0;
				}
			child.layout(left,top,left+=child.getMeasuredWidth(),top+child.getMeasuredHeight());
		}
		height=top+max;
		
	}
	
}
