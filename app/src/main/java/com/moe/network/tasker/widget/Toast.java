package com.moe.network.tasker.widget;
import android.content.Context;
import android.widget.Toast;
import android.view.LayoutInflater;
import com.moe.network.tasker.R;
import android.widget.TextView;
import android.view.View.OnClickListener;
import java.lang.reflect.Field;
import android.view.WindowManager;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
public class Toast
{
	private Toast toast;
	private Object mTn;
	public Toast(Context context){
		toast=new Toast(context);
		toast.setView(LayoutInflater.from(context).inflate(R.layout.toast_view,null));
		/*try
		{
			Field mTn=toast.getClass().getDeclaredField("mTN");
			mTn.setAccessible(true);
			this.mTn = mTn.get(toast);
		}
		catch (Exception e)
		{}*/
		
		toast.setDuration(Toast.LENGTH_SHORT);
	}
	public com.moe.network.tasker.widget.Toast setMessage(CharSequence text){
		((TextView)toast.getView().findViewById(android.R.id.message)).setText(text);
		return this;
	}
	/*public com.moe.network.tasker.widget.Toast setButton(CharSequence text,OnClickListener ocl){
		TextView button=((TextView)toast.getView().findViewById(android.R.id.summary));
		button.setText(text);
		button.setOnClickListener(ocl);
		button.setVisibility(button.VISIBLE);
		toast.setDuration(Toast.LENGTH_LONG);
		return this;
	}*/
	public com.moe.network.tasker.widget.Toast show(){
		/*try
		{
			Field mParams=mTn.getClass().getDeclaredField("mParams");
			mParams.setAccessible(true);
			WindowManager.LayoutParams params=((WindowManager.LayoutParams)mParams.get(mTn));
			params.flags=WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
			params.windowAnimations=R.style.toastAnim;
		}
		catch (Exception e)
		{}*/
		toast.show();
		return this;
	}
	public void cancel(){
		toast.cancel();
	}
}
