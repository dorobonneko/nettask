package com.moe.widget;
import android.widget.FrameLayout;
import android.util.AttributeSet;
import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingChild;
import android.view.ViewGroup;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.NestedScrollingChildHelper;
import android.view.View;
import com.moe.graphics.ProgressDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.Canvas;
import android.widget.Scroller;
import android.support.v4.widget.ViewDragHelper;
import android.support.v7.widget.RecyclerView;
import android.content.res.TypedArray;
import android.util.TypedValue;

public class LoadMoreLayout extends ViewGroup implements NestedScrollingParent,NestedScrollingChild
{
	private NestedScrollingChildHelper childHelper;
	private NestedScrollingParentHelper parentHelper;
	private ProgressDrawable progress;
	private ViewDragHelper viewDragHelper;
	private int position,overSize;
	private boolean scroll=false;
	private View child;
	private String message;
	public LoadMoreLayout(Context context,AttributeSet attrs){
		super(context,attrs);
		childHelper=new NestedScrollingChildHelper(this);
		childHelper.setNestedScrollingEnabled(true);
		parentHelper=new NestedScrollingParentHelper(this);
		TypedArray ta=context.obtainStyledAttributes(attrs,new int[]{0x7f01008e});
		progress=new ProgressDrawable(context,ta.getColor(0,0xffff0000));
		ta.recycle();
		progress.setCallback(this);
		viewDragHelper=ViewDragHelper.create(this,new Callback());
		overSize=(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,42,context.getResources().getDisplayMetrics());
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		if(child!=null)
			child.measure(widthMeasureSpec,heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
	}
	
	@Override
	protected void onLayout(boolean p1, int p2, int p3, int p4, int p5)
	{
		if(child!=null)
		child.layout(0,position,child.getMeasuredWidth(),child.getMeasuredHeight()+position);
	}
	public void setMessage(String msg){
		this.message=msg;
	}
	public String getMessage(){
		return message;
	}
	@Override
	protected void onFinishInflate()
	{
		// TODO: Implement this method
		super.onFinishInflate();
		if(getChildCount()==1)
			child=getChildAt(0);
			else if(getChildCount()>1)
				throw new RuntimeException("只能加载一个布局");
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas)
	{
		super.dispatchDraw(canvas);
		progress.setBounds((getMeasuredWidth()-overSize)/2,getMeasuredHeight()+position,(getMeasuredWidth()+overSize)/2,getMeasuredHeight()+position+overSize);
		progress.draw(canvas);
	}
	
	public boolean onStartNestedScroll(android.view.View p1, android.view.View p2, int p3){
		//if(hasNestedScrollingParent()&&startNestedScroll(p3))
			//return true;
		return isNestedScrollingEnabled()&&isEnabled()&&p3==ViewCompat.SCROLL_AXIS_VERTICAL;
	};

    public void onNestedScrollAccepted(android.view.View p1, android.view.View p2, int p3){
		parentHelper.onNestedScrollAccepted(p1,p2,p3);
		startNestedScroll(p3);
	};

    public void onStopNestedScroll(android.view.View p1){
		stopNestedScroll();
		parentHelper.onStopNestedScroll(p1);
		if(scroll){
		if(position<=-overSize)
		viewDragHelper.smoothSlideViewTo(child,0,-overSize);
		else
		viewDragHelper.smoothSlideViewTo(child,0,0);
		ViewCompat.postOnAnimation(child,new Anime(child));
		scroll=false;
		}
	};

    public void onNestedScroll(android.view.View p1, int dxc, int dyc, int dxuc, int dyuc){
		int[] ci=new int[2];
		dispatchNestedScroll(dxc,dyc,dxuc,dyuc,ci);
		int dy=dyuc-ci[1];
		if(child!=null&&dy>0){
			position-=dy;
			//invalidate();
			child.layout(0,position,child.getMeasuredWidth(),child.getMeasuredHeight()+position);
			//getChildAt(0).scrollBy(0,dy);
			}
			
	};

    public void onNestedPreScroll(android.view.View p1, int dx, int dy, int[] c){
		scroll=true;
		int[] cc=new int[2];
		if(dispatchNestedPreScroll(dx,dy,cc,null)){
			c[0]+=cc[0];
			c[1]+=cc[1];
			dy-=cc[1];
		}
		if(child!=null&&position<0&&dy<0)
		{
			if(dy<position){
					c[1]+=position;
					position=0;
				}else{
					position-=dy;
					c[1]+=dy;
				}
				child.layout(0,position,child.getMeasuredWidth(),child.getMeasuredHeight()+position);
		}
	};

    public boolean onNestedFling(android.view.View p1, float p2, float p3, boolean p4){
		return dispatchNestedFling(p2,p3,p4);
	};

    public boolean onNestedPreFling(android.view.View p1, float p2, float p3){
		return dispatchNestedPreFling(p2,p3);
	};

    public int getNestedScrollAxes(){
		return parentHelper.getNestedScrollAxes();
	};
	public void setNestedScrollingEnabled(boolean p1){
		childHelper.setNestedScrollingEnabled(p1);
	}

    public boolean isNestedScrollingEnabled(){
		return childHelper.isNestedScrollingEnabled();
	}

    public boolean startNestedScroll(int p1){
		return childHelper.startNestedScroll(p1);
	}

    public void stopNestedScroll(){
		childHelper.stopNestedScroll();
	}

    public boolean hasNestedScrollingParent(){
		return childHelper.hasNestedScrollingParent();
	}

    public boolean dispatchNestedScroll(int p1, int p2, int p3, int p4, int[] p5){
		return childHelper.dispatchNestedScroll(p1,p2,p3,p4,p5);
	}

    public boolean dispatchNestedPreScroll(int p1, int p2, int[] p3, int[] p4){
		return childHelper.dispatchNestedPreScroll(p1,p2,p3,p4);
	}

    public boolean dispatchNestedFling(float p1, float p2, boolean p3){
		return childHelper.dispatchNestedFling(p1,p2,p3);
	}

    public boolean dispatchNestedPreFling(float p1, float p2){
		return childHelper.dispatchNestedPreFling(p1,p2);
	}
	public abstract interface OnLoadMoreListener{
		void onLoadMore();
	}
	private class Callback extends ViewDragHelper.Callback
	{

		@Override
		public boolean tryCaptureView(View p1, int p2)
		{
			// TODO: Implement this method
			return false;
		}

		@Override
		public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy)
		{
			position=top;
			invalidate();
			//getChildAt(0).layout(left,top,left+getMeasuredWidth(),top+getMeasuredHeight());
			}
	
}
class Anime implements Runnable{
	View v;
	public Anime(View v){
		this.v=v;
	}

	@Override
	public void run()
	{
		if(viewDragHelper.continueSettling(true))
		ViewCompat.postOnAnimation(v,this);
	}

	
}
	public static class State extends ProgressDrawable.State{}
}
