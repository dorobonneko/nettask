package com.moe.network.tasker.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import com.moe.network.tasker.R;

public class TaskerListAdapter extends RecyclerView.Adapter<TaskerListAdapter.ViewHolder>
{

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup p1, int p2)
	{
		// TODO: Implement this method
		return new ViewHolder(LayoutInflater.from(p1.getContext()).inflate(R.layout.list_tasker_item,p1,false));
	}

	@Override
	public void onBindViewHolder(ViewHolder p1, int p2)
	{
		// TODO: Implement this method
	}

	@Override
	public int getItemCount()
	{
		// TODO: Implement this method
		return 5;
	}
	public class ViewHolder extends RecyclerView.ViewHolder{
		public ViewHolder(View v){
			super(v);
		}
	}
}
