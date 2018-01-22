package com.moe.graphics;
import android.graphics.*;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;

public class ProgressDrawable extends Drawable implements Animatable,ValueAnimator.AnimatorUpdateListener
{

	
	private ValueAnimator anime;
	private int state=State.SUCCESS;
	private Paint paint;
	private float progress;
	public ProgressDrawable(Context context,int Color){
		renderAnime();
		paint=new Paint();
		paint.setColor(Color);
		paint.setStyle(Paint.Style.STROKE);
		paint.setAntiAlias(true);
		paint.setDither(true);
		paint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,2,context.getResources().getDisplayMetrics()));
	}
	private void renderAnime(){
		anime=ObjectAnimator.ofFloat(new float[]{0,1});
		anime.setDuration(500);
		//anime.setInterpolator(new FastOutSlowInInterpolator());
		anime.addUpdateListener(this);
		anime.setRepeatCount(anime.INFINITE);
		anime.setRepeatMode(anime.RESTART);
	}
	public void setProgressState(int state)
	{
		this.state = state;
		if(getCallback()!=null)
			getCallback().invalidateDrawable(this);
			if(!isRunning())
				start();
	}

	public int getProgressState()
	{
		return state;
	}
	
	@Override
	public void draw(Canvas p1)
	{
		//p1.drawColor(0xff000000);
		Rect r=getBounds();
		if(r.right==0)r.right=getIntrinsicWidth();
		if(r.bottom==0)r.bottom=getIntrinsicHeight();
		int width=r.right-r.left;
		int height=r.bottom-r.top;
		switch(state){
			case State.PROGRESS:
				p1.save();
				p1.rotate(progress*360,r.left+width/3,r.top+height/2);
				Path refresh=new Path();
				//refresh.addArc(getIntrinsicWidth()*0.25f,getIntrinsicHeight()*0.25f,getIntrinsicWidth()*0.75f,getIntrinsicHeight()*0.75f,70,290);
				//refresh.arcTo(getIntrinsicWidth()*0.25f-3,getIntrinsicHeight()*0.25f-3,getIntrinsicWidth()*0.75f-3,getIntrinsicHeight()*0.75f-3,70,290,true);
				refresh.moveTo(r.left+width*0.5f+paint.getStrokeWidth()/2,r.top+height/2);
				refresh.rLineTo(width*0.25f,0);
				refresh.rLineTo(-width/8,height/8);
				//refresh.rLineTo(-width/4*0.5f,-height/4*0.5f);
				refresh.close();
				paint.setStyle(Paint.Style.FILL);
				p1.drawPath(refresh,paint);
				paint.setStyle(Paint.Style.STROKE);
				p1.drawArc(r.left+width*0.35f,r.top+height*0.35f,r.left+width*0.65f,r.top+height*0.65f,60,300,false,paint);
				//p1.drawRect(0,0,width/4,10,paint);
				p1.restore();
				break;
			case State.ERROR:
				paint.setStyle(Paint.Style.FILL);
				p1.drawLine(r.left+width*0.35f,r.top+height*0.35f,r.left+width*0.65f,r.top+height*0.65f,paint);
				p1.drawLine(r.left+width*0.65f,r.top+height*0.35f,r.left+width*0.35f,r.top+height*0.65f,paint);
				break;
			case State.SUCCESS:
				paint.setStyle(Paint.Style.FILL);
				p1.drawLine(r.left+width*0.35f,r.top+height*0.5f,r.left+width*0.5f,r.top+height*0.65f,paint);
				p1.drawLine(r.left+width*0.5f-paint.getStrokeWidth()/2,r.top+height*0.65f,r.left+width*0.8f,r.top+height*0.35f,paint);
				break;
		}
		
	}

	@Override
	public void setAlpha(int p1)
	{
		paint.setAlpha(p1);
	}
	@Override
	public void onAnimationUpdate(ValueAnimator p1)
	{
		progress=p1.getAnimatedValue();
		if(getCallback()!=null)
			getCallback().invalidateDrawable(this);
	}


	@Override
	public void start()
	{
		anime.start();
	}

	@Override
	public void stop()
	{
		anime.cancel();
	}

	@Override
	public boolean isRunning()
	{
		return anime!=null&&anime.isRunning();
	}
	@Override
	public void setColorFilter(ColorFilter p1)
	{
		paint.setColorFilter(p1);
	}

	@Override
	public int getOpacity()
	{
		return PixelFormat.TRANSLUCENT;
	}
	@Override
	public int getIntrinsicWidth()
	{
		return 100;
	}

	@Override
	public int getIntrinsicHeight()
	{
		return 100;
	}
	
	public static class State{
		public final static int PROGRESS=0;
		public final static int ERROR=1;
		public final static int SUCCESS=2;
	}
}
