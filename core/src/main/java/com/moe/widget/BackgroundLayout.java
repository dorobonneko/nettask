package com.moe.widget;
import android.widget.FrameLayout;
import android.util.AttributeSet;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.Rect;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import com.moe.utils.BitmapUtils;

public class BackgroundLayout extends FrameLayout
{
	public BackgroundLayout(Context context,AttributeSet attrs){
		super(context,attrs);
	}

	public Bitmap getBackground(Rect rect)
	{
		try{
		Bitmap bit=((BitmapDrawable)super.getBackground()).getBitmap();
		Bitmap newBit=BitmapUtils.scaleWithSize(bit,getMeasuredWidth(),getMeasuredHeight());
		Bitmap buffer=Bitmap.createBitmap(newBit,rect.left,rect.top,rect.right,rect.bottom);
		if(buffer!=newBit)
			newBit.recycle();
		return buffer;
		}catch(Exception e){}
		return null;
	}
	
}
