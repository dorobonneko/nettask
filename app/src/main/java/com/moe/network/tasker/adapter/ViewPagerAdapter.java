package com.moe.network.tasker.adapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import java.util.List;
import android.view.ViewGroup;
import com.moe.network.tasker.R;

public class ViewPagerAdapter extends PagerAdapter
{
	private List<? extends View> list;
	public ViewPagerAdapter(List<? extends View> list){
		this.list=list;
	}
	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View p1, Object p2)
	{
		return p1==p2;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position)
	{
		View v=list.get(position);
		container.addView(v);
		return v;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object)
	{
		container.removeViewAt(position);
		}

	@Override
	public CharSequence getPageTitle(int position)
	{
		return list.get(position).getTag(R.id.view_name).toString();
	}
	
}
