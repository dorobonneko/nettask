package com.moe.network.tasker.widget;
import android.support.v7.widget.RecyclerView;
import android.graphics.Rect;
import android.graphics.Canvas;

public class CustonItemDecoration extends RecyclerView.ItemDecoration
{
	private int padding;
	public CustonItemDecoration(int padding){
		this.padding=padding;
	}
	@Override
	public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent)
	{
		int count=parent.getAdapter().getItemCount();
		switch(itemPosition){
			case 0:
				outRect.set(padding,padding/2,padding,padding);
				break;
			default:
				outRect.set(padding,0,padding,padding);
			break;
		}
		if(itemPosition==count)
			outRect.bottom=0;
		}

	@Override
	public void onDraw(Canvas c, RecyclerView parent)
	{
		// TODO: Implement this method
		super.onDraw(c, parent);
	}
	
}
