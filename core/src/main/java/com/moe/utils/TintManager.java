package com.moe.utils;
import com.moe.core.MoeTheme;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.PorterDuff;
import android.support.v4.graphics.drawable.DrawableCompat;

public class TintManager
{
	public static void setTint(MoeTheme mt,ColorStateList state,Drawable drawable){
		
		drawable=DrawableCompat.wrap(drawable);
		//覆盖原色
		drawable.setTintMode(PorterDuff.Mode.SRC_ATOP);
		drawable.setTintList(state);
	}
}
