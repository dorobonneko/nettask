package com.moe.network.tasker;

import android.app.*;
import android.os.*;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import java.util.List;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import com.moe.network.tasker.adapter.ViewPagerAdapter;
import android.content.res.TypedArray;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import com.moe.network.tasker.adapter.TaskerListAdapter;
import android.view.MenuItem;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import com.moe.network.tasker.adapter.TaskListAdapter;
import com.moe.network.tasker.widget.CustonItemDecoration;


public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	private List<RecyclerView> view_list;
	private ViewPager viewPager;
	private Snackbar exit;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT)
		findViewById(R.id.coordinatorlayout).setPadding(0,getResources().getDimensionPixelSize(getResources().getIdentifier("status_bar_height","dimen","android")),0,0);
		else
		ViewCompat.setFitsSystemWindows(findViewById(R.id.coordinatorlayout),true);
		setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
		TabLayout tl=(TabLayout)findViewById(R.id.tablayout);
		viewPager=(ViewPager)findViewById(R.id.viewpager);
		tl.setupWithViewPager(viewPager,true);
		view_list=new ArrayList<>();
		view_list.add(new RecyclerView(this));
		view_list.add(new RecyclerView(this));
		view_list.get(0).setTag(R.id.view_name,"任务");
		view_list.get(1).setTag(R.id.view_name,"计划任务");
		ViewPagerAdapter vpa=new ViewPagerAdapter(view_list);
		viewPager.setAdapter(vpa);
		view_list.get(0).setLayoutManager(new LinearLayoutManager(this));
		view_list.get(1).setLayoutManager(new LinearLayoutManager(this));
		view_list.get(0).setAdapter(new TaskListAdapter());
		view_list.get(0).addItemDecoration(new CustonItemDecoration(getResources().getDimensionPixelSize(R.dimen.padding)));
		view_list.get(1).setAdapter(new TaskerListAdapter());
		view_list.get(1).addItemDecoration(new CustonItemDecoration(getResources().getDimensionPixelSize(R.dimen.padding)));
		viewPager.setOverScrollMode(viewPager.OVER_SCROLL_NEVER);
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu,menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch(item.getItemId()){
			case R.id.menu_add:
				switch(viewPager.getCurrentItem()){
					case 0:
						try{startActivity(new Intent(this,NewTask.class));}catch(Exception e){
							Snackbar.make(findViewById(R.id.coordinatorlayout),"组件被禁用",1500).show();
						}
						break;
					case 1:
						try{startActivity(new Intent(this,NewTimer.class));}catch(Exception e){
							Snackbar.make(findViewById(R.id.coordinatorlayout),"组件被禁用",1500).show();
						}
						break;
				}
				break;
		}
		return true;
	}

	@Override
	public void onBackPressed()
	{
		if(exit==null)
		exit=Snackbar.make(findViewById(R.id.coordinatorlayout),"确认退出？",1500).setAction("退出",this).addCallback(new SnackbarCallback());
		if(exit.isShown())
			exit.dismiss();
			else
			exit.show();
		
	}

	@Override
	public void onClick(View p1)
	{
		switch(p1.getId()){
			case android.support.v7.appcompat.R.id.snackbar_action:
				finish();
				break;
		}
	}

	class SnackbarCallback extends Snackbar.Callback
	{

		@Override
		public void onDismissed(Snackbar transientBottomBar, int event)
		{
			if(event==Snackbar.Callback.DISMISS_EVENT_SWIPE){
				exit.removeCallback(this);
				exit=Snackbar.make(findViewById(R.id.coordinatorlayout),"确认退出？",1500).setAction("退出",MainActivity.this).addCallback(this);
			}
			}
		
	}
}
