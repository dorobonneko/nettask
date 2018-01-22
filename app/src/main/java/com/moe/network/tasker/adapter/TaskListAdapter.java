package com.moe.network.tasker.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import com.moe.network.tasker.R;
import java.util.List;
import com.moe.network.tasker.entity.Task;
import android.widget.TextView;
import com.moe.widget.MoeRecyclerView;
import com.moe.widget.AutoBackground;
import com.moe.widget.DefaultAutoBackground;
import android.app.Activity;

public class TaskListAdapter extends AbstractAdapter<AbstractAdapter.ViewHolder>
{
	public List<Task> list;
	public TaskListAdapter(List<? extends Task> list){
		this.list=(List<Task>) list;
	}

	
//太卡了，不使用
	/*@Override
	public void onViewAttachedToWindow(AbstractAdapter.ViewHolder holder)
	{
		// TODO: Implement this method
		super.onViewAttachedToWindow(holder);
		if(holder instanceof ViewHolder)
			((ViewHolder)holder).auto.start();
			else
			((ViewHolder2)holder).auto.start();
	}

	@Override
	public void onViewDetachedFromWindow(AbstractAdapter.ViewHolder holder)
	{
		// TODO: Implement this method
		super.onViewDetachedFromWindow(holder);
		if(holder instanceof ViewHolder)
			((ViewHolder)holder).auto.stop();
		else
			((ViewHolder2)holder).auto.stop();
	}*/
	
	@Override
	public AbstractAdapter.ViewHolder onCreateViewHolder(MoeRecyclerView p1, int p2)
	{
		return p2==0?new ViewHolder(p1,LayoutInflater.from(p1.getContext()).inflate(R.layout.list_task_item,p1,false)):new ViewHolder2(p1,LayoutInflater.from(p1.getContext()).inflate(R.layout.list_timer_item,p1,false));
	}

	@Override
	public Object getObject(int adapterPosition)
	{
		return list.get(adapterPosition);
	}


	@Override
	public void onBindViewHolder(AbstractAdapter.ViewHolder p1, int p2)
	{
		/*if(p1.getItemViewType()==0){
		((ViewHolder)p1).title.setText(list.get(p2).getName());
		}else{
		((ViewHolder2)p1).title.setText(list.get(p2).getName());
		}*/
	}

	@Override
	public int moeGetItemCount()
	{
		return list.size()+20;
	}

	/*@Override
	public int moeGetItemViewType(int position)
	{
		return list.get(position).getTimer();
	}*/
	
	public class ViewHolder extends AbstractAdapter.ViewHolder{
		TextView title;
		private AutoBackground auto;
		public ViewHolder(MoeRecyclerView mrv,View v){
			super(mrv,v);
			auto=new DefaultAutoBackground(v,(Activity)v.getContext());
			title=(TextView) v.findViewById(android.R.id.title);
		}
	}
	public class ViewHolder2 extends AbstractAdapter.ViewHolder{
		TextView title;
		private AutoBackground auto;
		public ViewHolder2(MoeRecyclerView mrv,View v){
			super(mrv,v);
			auto=new DefaultAutoBackground(v,(Activity)v.getContext());
			title=(TextView) v.findViewById(android.R.id.title);
		}
	}
}
