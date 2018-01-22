package com.moe.network.tasker.widget;
import android.widget.CompoundButton;
import android.content.Context;
import android.graphics.Canvas;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.Animator;
import android.graphics.Path;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.content.res.TypedArray;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Toast;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.animation.AnimatorSet;

public class ProgressButton extends CompoundButton implements ValueAnimator.AnimatorUpdateListener,Animator.AnimatorListener,OnClickListener
{
	private OnCheckedChangeListener occl;
	private Paint paint;
	private ValueAnimator anime,circleAnime;
	private float width=0.4f;
	public ProgressButton(Context context,AttributeSet attrs){
		super(context,attrs);
		anime=ObjectAnimator.ofFloat(new float[]{0,1});
		anime.setDuration(150);
		anime.setRepeatCount(0);
		anime.addUpdateListener(this);
		anime.addListener(this);
		circleAnime=ObjectAnimator.ofFloat(new float[]{0,1});
		circleAnime.setDuration(1000);
		circleAnime.addUpdateListener(this);
		circleAnime.setRepeatCount(-1);
		circleAnime.setRepeatMode(ValueAnimator.RESTART);
		paint=new Paint();
		TypedArray ta=context.obtainStyledAttributes(attrs,new int[]{android.support.v7.appcompat.R.attr.colorAccent});
		paint.setColor(ta.getColor(0,0xffffffff));
		paint.setAntiAlias(true);
		paint.setDither(true);
		setOnClickListener(this);
	}
	@Override
	protected void onDraw(Canvas canvas)
	{
		if(anime.isRunning()){
			float avalue=anime.getAnimatedFraction();
			canvas.drawPath(getImage(isChecked()?avalue:1-avalue),paint);
		}else{
			canvas.drawPath(getImage(isChecked()?1:0),paint);
		}
		if(circleAnime.isRunning()){
			paint.setAlpha(128);
			canvas.drawArc(getMeasuredWidth()*((1-width)/2-0.2f),getMeasuredHeight()*((1-width)/2-0.2f),getMeasuredWidth()*(1-((1-width)/2-0.2f)),getMeasuredHeight()*(1-((1-width)/2-0.2f)),-90f,360f*circleAnime.getAnimatedFraction(),true,paint);
			paint.setAlpha(255);
		}
	}
	private Path getImage(float value){
		Path p=new Path();
		p.setLastPoint(getMeasuredWidth()*(1-width)/2,getMeasuredHeight()*(1-width)/2);
		p.rLineTo(getMeasuredWidth()*width,(1-value)*getMeasuredHeight()*width/2);
		p.rLineTo(0,value*getMeasuredHeight()*width);
		p.lineTo(getMeasuredWidth()*(1-width)/2,getMeasuredHeight()*(1-(1-width)/2));
		p.close();
		return p;
	}
	public void setChecked(boolean checked,boolean anime)
	{
		
		if(this.anime==null)
			super.setChecked(checked);
		else
		if(!this.anime.isRunning()){
		super.setChecked(checked);
		if(anime)
			this.anime.start();
		else
		onAnimationEnd(this.anime);
		}
	}

	@Override
	public void setChecked(boolean checked)
	{
		//this.isChecked=checked;
		//onAnimationEnd(anime);
		setChecked(checked,true);
	}

	
	
	
	@Override
	public synchronized void onAnimationUpdate(ValueAnimator p1)
	{
		invalidate();
	}
	@Override
	public void onAnimationStart(Animator p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void onAnimationEnd(Animator p1)
	{
		//invalidate();
		if(occl!=null)
			occl.onCheckedChanged(this,isChecked());
		if(isChecked())
			circleAnime.start();
			else
			circleAnime.cancel();
	}

	@Override
	public void onAnimationCancel(Animator p1)
	{
		invalidate();
	}

	@Override
	public void onAnimationRepeat(Animator p1)
	{
		
	}

	@Override
	public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener)
	{
		occl=listener;
	}

	@Override
	public void onClick(View p1)
	{
		//setChecked(!isChecked(),true);
	}

	@Override
	public void onInitializeAccessibilityEvent(AccessibilityEvent event)
	{
		// TODO: Implement this method
		super.onInitializeAccessibilityEvent(event);
		event.setClassName(ProgressButton.class.getName());
	}

	@Override
	public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo info)
	{
		// TODO: Implement this method
		super.onInitializeAccessibilityNodeInfo(info);
		info.setClassName(ProgressButton.class.getName());
	}

	
	
	
}
