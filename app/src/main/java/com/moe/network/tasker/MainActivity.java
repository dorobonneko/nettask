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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import com.moe.network.tasker.adapter.TaskListAdapter;
import com.moe.network.tasker.widget.CustonItemDecoration;
import com.moe.network.tasker.database.NetTaskerDataBase;
import com.moe.network.tasker.entity.Task;
import com.moe.network.tasker.widget.Toast;
import android.view.View.OnClickListener;
import com.moe.network.tasker.entity.Timer;
import com.moe.widget.MoeRecyclerView;
import com.moe.widget.MoeRecyclerView.ViewHolder;
import com.moe.network.tasker.app.TimerFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.content.DialogInterface;
import android.view.ViewTreeObserver;
import android.graphics.Rect;
import com.moe.widget.BackgroundLayout;
import com.moe.utils.BitmapUtils;
import android.graphics.drawable.BitmapDrawable;
import com.moe.widget.DefaultAutoBackground;
import android.view.WindowManager;



public class MainActivity extends AppCompatActivity implements View.OnClickListener,MoeRecyclerView.OnItemClickListener
{
	private ArrayList<Task> task_list,timer_list;
	private TaskListAdapter task_adapter,timer_adapter;
	private List<View> view_list;
	private ViewPager viewPager;
	private Snackbar exit;
	private final static int RESULT_NEWTASK=6466;
	private final static int RESULT_NEWTIMER=4994;
	private AlertDialog newTask;
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
		if(savedInstanceState!=null){
			task_list=savedInstanceState.getParcelableArrayList("task_list");
			timer_list=savedInstanceState.getParcelableArrayList("timer_list");
			}
		if(Build.VERSION.SDK_INT>18)
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        
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
		view_list.add(LayoutInflater.from(this).inflate(R.layout.recyclerview,viewPager,false));
		view_list.add(LayoutInflater.from(this).inflate(R.layout.recyclerview,viewPager,false));
		view_list.get(0).setTag(R.id.view_name,"任务");
		view_list.get(1).setTag(R.id.view_name,"计划任务");
		ViewPagerAdapter vpa=new ViewPagerAdapter(view_list);
		viewPager.setAdapter(vpa);
		MoeRecyclerView list0=(MoeRecyclerView) view_list.get(0).findViewById(R.id.recyclerview);
		MoeRecyclerView list1=(MoeRecyclerView) view_list.get(1).findViewById(R.id.recyclerview);
		list0.setLayoutManager(new LinearLayoutManager(this));
		list1.setLayoutManager(new LinearLayoutManager(this));
		list0.setAdapter(task_adapter=new TaskListAdapter(task_list==null?task_list=new ArrayList<>():task_list));
		list0.addItemDecoration(new CustonItemDecoration(getResources().getDimensionPixelSize(R.dimen.padding)));
		list1.setAdapter(timer_adapter=new TaskListAdapter(timer_list==null?timer_list=new ArrayList<>():timer_list));
		list1.addItemDecoration(new CustonItemDecoration(getResources().getDimensionPixelSize(R.dimen.padding)));
		list0.setOnItemClickListener(this);
		list1.setOnItemClickListener(this);
		viewPager.setOverScrollMode(viewPager.OVER_SCROLL_NEVER);
		if(task_list.size()==0){
			task_list.addAll(NetTaskerDataBase.getInstance(this).queryTask(true));
			task_adapter.notifyDataSetChanged();
			}
		if(timer_list.size()==0){
			timer_list.addAll(NetTaskerDataBase.getInstance(this).queryTask(false));
			timer_adapter.notifyDataSetChanged();
		}
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
						if(newTask==null){
							View v=LayoutInflater.from(this).inflate(R.layout.newtask,null);
							newTask = new AlertDialog.Builder(this).setTitle("创建任务").setView(v).setPositiveButton("取消",null).setNegativeButton("添加",new DialogInterface.OnClickListener(){

									@Override
									public void onClick(DialogInterface p1, int p2)
									{
										// TODO: Implement this method
									}
								}).create();
						}
						newTask.show();
						final View v=newTask.getWindow().getDecorView().findViewById(android.R.id.content);
						DefaultAutoBackground auto=new DefaultAutoBackground(v,this);
						auto.start();
						break;
					case 1:
						TimerFragment timer=(TimerFragment) getSupportFragmentManager().findFragmentByTag("timer");
						if(timer==null)timer=new TimerFragment();
						if(timer.isAdded())
							getSupportFragmentManager().beginTransaction().show(timer).commit();
							else
							getSupportFragmentManager().beginTransaction().add(android.R.id.background,timer,"timer").commit();
						/*try{startActivityForResult(new Intent(this,NewTimer.class),RESULT_NEWTIMER);}catch(Exception e){
							Snackbar.make(findViewById(R.id.coordinatorlayout),"组件被禁用",1500).show();
						}*/
						break;
				}
				break;
		}
		/*TimerFragment tf=(TimerFragment) getSupportFragmentManager().findFragmentByTag("timer");
		if(tf!=null)
			tf.onOptionsItemSelected(item);*/
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed()
	{
		TimerFragment timer=(TimerFragment) getSupportFragmentManager().findFragmentByTag("timer");
		if(timer!=null&&!timer.isHidden())
			timer.hide();
			else
		super.onBackPressed();
	}

	

	@Override
	public void finish()
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
				super.finish();
				break;
		}
	}

	@Override
	public void OnItemClick(MoeRecyclerView mrv, MoeRecyclerView.ViewHolder vh)
	{
		new Toast(this).setMessage(((Task)vh.getObject()).getName()).show();
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch(requestCode){
			case RESULT_NEWTASK:
				if(resultCode==RESULT_OK){
					Task task=(Task)data.getParcelableExtra(Intent.EXTRA_RETURN_RESULT);
					if(NetTaskerDataBase.getInstance(this).addTask(task)){
						task_list.add(task);
						task_adapter.notifyItemInserted(task_list.size()-1);
						}
					
					}
				break;
			case RESULT_NEWTIMER:
				break;
		}
		super.onActivityResult(requestCode,resultCode,data);
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
