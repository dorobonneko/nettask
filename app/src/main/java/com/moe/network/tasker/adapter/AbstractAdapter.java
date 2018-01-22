package com.moe.network.tasker.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.moe.widget.MoeRecyclerView;

public abstract class AbstractAdapter<T extends AbstractAdapter.ViewHolder> extends MoeRecyclerView.Adapter<T>
{
	/*private OnItemClickListener oicl;

	public void setOnItemClickListener(OnItemClickListener oicl)
	{
		this.oicl = oicl;
	}

	private OnItemClickListener getOicl()
	{
		return oicl;
	}

	public static class ViewHolder extends MoeRecyclerView.ViewHolder implements View.OnClickListener{
		private AbstractAdapter adapter;
		public ViewHolder(AbstractAdapter adapter,View v){
			super(v);
			this.adapter=adapter;
			if(adapter.getOicl()!=null)
				v.setOnClickListener(this);
		}

		@Override
		public void onClick(View p1)
		{
			if(adapter.getOicl()!=null)
				adapter.getOicl().onItemClick(adapter,this);
		}

		
		
	}
	public abstract interface OnItemClickListener{
		void onItemClick(AbstractAdapter adapter,ViewHolder vh);
	}*/
	public static class ViewHolder extends MoeRecyclerView.ViewHolder{
		public ViewHolder(MoeRecyclerView mrv,View v){
			super(mrv,v);
		}
	}
		
}
