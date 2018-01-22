package com.moe.widget;
import android.widget.FrameLayout;
import android.util.AttributeSet;
import android.content.Context;
import android.view.ViewGroup.LayoutParams;
import android.view.View;
import android.view.MotionEvent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.Rect;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import android.graphics.drawable.BitmapDrawable;
import android.renderscript.RenderScript;
import android.renderscript.Allocation;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.widget.ViewDragHelper;
import android.view.ViewGroup;
import android.graphics.Point;
import android.view.Gravity;
import android.renderscript.Element;
import android.widget.Toast;
import android.graphics.Matrix;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.graphics.Paint;
import com.moe.utils.BitmapUtils;
import android.app.Activity;

public class SlideLayout extends FrameLayout
{
	private boolean default_,animeEnter;
	private boolean close;
	private Paint shadowPaint;
	private Callback callback;
	private Point point;
	private ViewDragHelper viewDragHelper;
	private int edgeFlags;
	private Bitmap background,customBackground,shadow;
	private Background backBit;
	public SlideLayout(Context context, AttributeSet attrs)
	{
		super(context,attrs);
		viewDragHelper = ViewDragHelper.create(this,new ViewDragCallback());
		point = new Point();
		shadowPaint = new Paint();
		shadowPaint.setAntiAlias(true);
		shadowPaint.setDither(true);
		backBit=new Background();
	}
	public void setAnimeEnter(boolean anime){
		animeEnter=anime;
	}
	public boolean isClose(){
		return close;
	}
	@Override
	public void addView(View child, int index, ViewGroup.LayoutParams params)
	{
		if (getChildCount()>0)throw new RuntimeException("该布局只能添加一个视图");
		int gravity=((SlideLayout.LayoutParams)params).gravity;
		viewDragHelper.setEdgeTrackingEnabled(edgeFlags=(gravity==Gravity.START||gravity==Gravity.LEFT?ViewDragHelper.EDGE_LEFT:(gravity==Gravity.RIGHT||gravity==Gravity.END?ViewDragHelper.EDGE_RIGHT:(ViewDragHelper.EDGE_LEFT|ViewDragHelper.EDGE_RIGHT))));
		super.addView(child,index,params);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec),MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec),MeasureSpec.EXACTLY);
		for (int i=0;i<getChildCount();i++)
		{
			View v=getChildAt(i);
			v.measure(widthMeasureSpec,heightMeasureSpec);
		}
		setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
		
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		if(animeEnter&&!default_){//默认退出
			default_=true;
			if(edgeFlags==ViewDragHelper.EDGE_RIGHT)
				point.x=-right+left;
				else
				point.x=right-left;
			}
		View child=getChildAt(0);
		if (child!=null)
		{
			child.layout(point.x,point.y,point.x+child.getMeasuredWidth(),point.y+child.getMeasuredHeight());
		}
	}

	@Override
	protected void onAttachedToWindow()
	{
		// TODO: Implement this method
		super.onAttachedToWindow();
		backBit.start();
	}

	public void setBackground(Bitmap bit)
	{
		customBackground = BitmapUtils.blur(getContext(),bit);
	}
	private Bitmap reSizeBitmap(Bitmap bit)
	{
		Bitmap buffer= BitmapUtils.scaleWithSize(bit,getMeasuredWidth(),getMeasuredHeight());
		if(buffer==null)return null;
		if (!buffer.equals(bit))bit.recycle();
		return buffer;
	}



	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		//绘制深色背景
		canvas.drawColor(Color.argb((int)((1-Math.abs((float)point.x)/getMeasuredWidth())*0x80),0,0,0));
		//绘制背景
		if (background!=null||customBackground!=null)
		{
			canvas.save();
			Rect clip=new Rect(0,0,canvas.getWidth(),canvas.getHeight());
			if (point.x>=0)
				clip.left = point.x;
			else
				clip.right = clip.right+point.x;
			canvas.clipRect(clip);
			canvas.drawBitmap(customBackground==null?background:customBackground,0,0,null);
			//getBackground().draw(canvas);
			canvas.restore();
		}
		//绘制阴影
		if(point.x<-getMeasuredWidth()&&point.x<getMeasuredWidth())
		canvas.drawBitmap(shadow,edgeFlags==ViewDragHelper.EDGE_LEFT?point.x-shadow.getWidth():point.x+getMeasuredWidth(),0,null);
//		canvas.save();
//		Rect shadowRect=new Rect(point.x-50,0,point.x,getMeasuredHeight());
//		if(edgeFlags==ViewDragHelper.EDGE_RIGHT)
//		{
//			shadowRect.left=point.x+getMeasuredWidth();
//			shadowRect.right=shadowRect.left+50;
//		}
//		canvas.clipRect(shadowRect);
//		canvas.drawRect(shadowRect,shadowPaint);
//		canvas.restore();
		super.dispatchDraw(canvas);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		// TODO: Implement this method
		super.onSizeChanged(w,h,oldw,oldh);
		/*if(customBackground!=null)
		customBackground = reSizeBitmap(customBackground);
		else {
			BackgroundLayout bl=(BackgroundLayout)getRootView().findViewById(android.R.id.background);
			if(bl!=null)
			{
				int[] vi=new int[2];
				getLocationOnScreen(vi);
				Rect visible=new Rect(vi[0],vi[1],getMeasuredWidth(),getMeasuredHeight());
				Bitmap buffer=reSizeBitmap(bl.getBackground(visible));
				if(buffer!=null)
					background=BitmapUtils.blur(getContext(),buffer);
				if(background!=buffer)
					buffer.recycle();
					background=reSizeBitmap(background);

			}
		}*/
		if (shadow!=null)shadow.recycle();
		shadow = Bitmap.createBitmap(50,h,Bitmap.Config.ARGB_8888);
		boolean flag=edgeFlags==ViewDragHelper.EDGE_LEFT;
		LinearGradient linearShadow=new LinearGradient(flag?shadow.getWidth():0,0,flag?0:shadow.getWidth(),0,new int[]{0x2d000000,0x1d000000,0x00000000},null,Shader.TileMode.REPEAT);
		shadowPaint.setShader(linearShadow);
		Canvas canvas=new Canvas(shadow);
		canvas.drawRect(0,0,shadow.getWidth(),shadow.getHeight(),shadowPaint);
	}



	@Override
	public boolean onInterceptTouchEvent(MotionEvent event)
	{
		if (!isEnabled())return false;
		if (event.getAction()==event.ACTION_CANCEL||event.getAction()==event.ACTION_UP)
		{
			viewDragHelper.cancel();
			return false;
		}
		return viewDragHelper.shouldInterceptTouchEvent(event);
	}


	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		viewDragHelper.processTouchEvent(event);
		return true;
	}

	@Override
	protected void onFinishInflate()
	{
		// TODO: Implement this method
		super.onFinishInflate();
		if (getChildCount()>1)throw new RuntimeException("子view只能有一个");
		
	}

	
	
	public void enter()
	{
		if(edgeFlags==ViewDragHelper.EDGE_RIGHT)
			point.x=-getMeasuredWidth();
		else
			point.x=getMeasuredWidth();
		onLayout(false,0,0,0,0);
		viewDragHelper.smoothSlideViewTo(getChildAt(0),0,0);
		ViewCompat.postOnAnimation(getChildAt(0),new StateAnime(getChildAt(0)));
		
	}
	public void exit()
	{
		viewDragHelper.smoothSlideViewTo(getChildAt(0),edgeFlags==ViewDragHelper.EDGE_RIGHT?-getMeasuredWidth():getMeasuredWidth(),0);
		ViewCompat.postOnAnimation(getChildAt(0),new StateAnime(getChildAt(0)));
		
		}
	private class ViewDragCallback extends ViewDragHelper.Callback
	{

		@Override
		public boolean tryCaptureView(View p1, int p2)
		{
			// TODO: Implement this method
			return false;
		}

		@Override
		public void onEdgeDragStarted(int edgeFlags, int pointerId)
		{
			viewDragHelper.captureChildView(getChildAt(0),pointerId);
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy)
		{
			point.x = left;
			invalidate();
		}

		@Override
		public int clampViewPositionHorizontal(View child, int left, int dx)
		{
			if (edgeFlags==ViewDragHelper.EDGE_LEFT)
				return Math.max(Math.min(left,getMeasuredWidth()),0);
			else if (edgeFlags==ViewDragHelper.EDGE_RIGHT)
				return left>=0?0:left<=-getMeasuredWidth()?0:left;
			else
				return left<=-getMeasuredWidth()?0:left>=getMeasuredWidth()?0:left;
		}

		@Override
		public int getViewHorizontalDragRange(View child)
		{
			return getMeasuredWidth();
		}

		@Override
		public void onViewReleased(View releasedChild, float xvel, float yvel)
		{
			int width=getMeasuredWidth()/2;
			switch (edgeFlags)
			{
				case ViewDragHelper.EDGE_LEFT:
					if (xvel<-1000)
					{
						viewDragHelper.settleCapturedViewAt(0,0);
					}
					else if (xvel>1000)
					{
						viewDragHelper.settleCapturedViewAt(getMeasuredWidth(),0);
					}
					else
					{
						if (point.x>width)
							viewDragHelper.settleCapturedViewAt(getMeasuredWidth(),0);
						else
							viewDragHelper.settleCapturedViewAt(0,0);
					}
					break;
				case ViewDragHelper.EDGE_RIGHT:
					if (xvel<-1000)
					{
						viewDragHelper.settleCapturedViewAt(-getMeasuredWidth(),0);
					}
					else if (xvel>1000)
					{
						viewDragHelper.settleCapturedViewAt(0,0);
					}
					else
					{
						if (point.x<-width)
							viewDragHelper.settleCapturedViewAt(-getMeasuredWidth(),0);
						else
							viewDragHelper.settleCapturedViewAt(0,0);
					}
					break;
				default:
					if (xvel<-1000)
					{
						viewDragHelper.settleCapturedViewAt(-getMeasuredWidth(),0);
					}
					else if (xvel>1000)
					{
						viewDragHelper.settleCapturedViewAt(getMeasuredWidth(),0);
					}
					else
					{
						if (point.x<-width)
							viewDragHelper.settleCapturedViewAt(-getMeasuredWidth(),0);
						else if (point.x>width)
							viewDragHelper.settleCapturedViewAt(getMeasuredWidth(),0);
						else
							viewDragHelper.settleCapturedViewAt(0,0);
					}
					break;
			}
			ViewCompat.postOnAnimation(releasedChild,new StateAnime(releasedChild));
		}

	}
	class StateAnime implements Runnable
	{
		View v;
		public StateAnime(View v)
		{
			this.v = v;
		}
		@Override
		public void run()
		{
			if (viewDragHelper.continueSettling(true))
			{
				ViewCompat.postOnAnimation(v,this);
			}
			else if (callback!=null)
			{
				close=point.x!=0;
				if (!close)
					callback.onEnter(SlideLayout.this);
				else
					callback.onExit(SlideLayout.this);
					if(!animeEnter)
					point.x=0;//矫正视图
			}
		}
	}
	public void setCallback(Callback call)
	{
		callback = call;
	}
	public abstract interface Callback
	{
		void onExit(SlideLayout sl);
		void onEnter(SlideLayout s);
	}
	private class Background extends AutoBackground{
		public Background(){
			super(SlideLayout.this,(Activity)getContext());
		}

		@Override
		public void onBitmapCreated(Bitmap bit)
		{
			if(customBackground!=null)
				customBackground = reSizeBitmap(customBackground);
				else
			background=reSizeBitmap(bit);
			invalidate();
		}

		
	}
}
