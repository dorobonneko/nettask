package com.moe.widget;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.support.v7.widget.RecyclerView.Adapter;
import java.util.List;
import java.util.ArrayList;
import android.view.ViewGroup;
import android.graphics.Canvas;

public class MoeRecyclerView extends RecyclerView
{
	private FastScrollBarManager fastScroll;
	private OnItemClickListener oicl;
	private OnItemLongClickListener oilcl;
	public MoeRecyclerView(Context context){
		super(context);
	}
	public MoeRecyclerView(Context context,AttributeSet attrs){
		super(context,attrs);
	}
	public void setFastScrollManager(FastScrollBarManager manager){
		fastScroll=manager;
		invalidate();
	}
	public FastScrollBarManager getFastScrollManager(){
		return fastScroll;
	}
	public void addHeaderView(View v){
		checkAdapter();
		((Adapter)getAdapter()).addHeaderView(v);
	}
	public void addFloorView(View v){
		checkAdapter();
		((Adapter)getAdapter()).addFloorView(v);
	}
	public View getHeaderView(int index){
		checkAdapter();
		return ((Adapter)getAdapter()).getHeaderView(index);
	}
	public View getFoorView(int index){
		checkAdapter();
		return ((Adapter)getAdapter()).getFloorView(index);
	}
	public void setEmptyView(View v){
		checkAdapter();
		((Adapter)getAdapter()).setEmptyView(v);
	}
	public View getEmptyView(){
		checkAdapter();
		return ((Adapter)getAdapter()).getEmptyView();
	}
	private void checkAdapter(){
		if(!(getAdapter() instanceof Adapter))
			throw new RuntimeException("Adapter must be extends MoeRecyclerView.Adapter");
	}
	public static abstract class Adapter <T extends MoeRecyclerView.ViewHolder> extends RecyclerView.Adapter<T>{
		private List<View> header=new ArrayList<>(),floor=new ArrayList<>();
		private View emptyView;

		public abstract Object getObject(int adapterPosition);
		private void setEmptyView(View v){
			emptyView=v;
		}
		private View getEmptyView(){
			return emptyView;
		}
		private void addHeaderView(View v){
			if(v.getParent()!=null)
				throw new RuntimeException(v.getClass().getName()+" has parent");
			header.add(v);
		}
		private View getHeaderView(int index){
			return header.get(index);
		}
		private View getFloorView(int index){
			return floor.get(index);
		}
		private void addFloorView(View v){
			if(v.getParent()!=null)
				throw new RuntimeException(v.getClass().getName()+" has parent");
			floor.add(v);
		}

		public abstract T onCreateViewHolder(MoeRecyclerView p1, int p2);

		@Override
		public final T onCreateViewHolder(ViewGroup p1, int p2)
		{
			if(p2>>31==1)
				return (T)new ViewHolder(header.get(p2&0x7fffffff));
			else if((p2&0x40000000)>>30==1)
				return (T)new ViewHolder(floor.get(p2&0x3fffffff));
			else if(getItemCount()==1&&emptyView!=null)
				return (T)new ViewHolder(emptyView);
			else return onCreateViewHolder((MoeRecyclerView)p1,p2);
		}

		
		public abstract int moeGetItemCount();
		@Override
		public final int getItemCount()
		{
			int count=moeGetItemCount()+header.size()+floor.size();
			return count==0?(emptyView!=null?1:0):count;
		}
		public int moeGetItemViewType(int position){
			return 0;
		}
		@Override
		public final int getItemViewType(int position)
		{
			return position<header.size()?position|0x80000000:(position>header.size()+moeGetItemCount()?(position-header.size()-moeGetItemCount())|0x40000000:moeGetItemViewType(position-header.size()));
		}
		
		private class ViewHolder extends MoeRecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
			}
		}
	}
	public static class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener,OnLongClickListener{
		private MoeRecyclerView mrv;
		public ViewHolder(View v){
			super(v);
		}
		public ViewHolder(MoeRecyclerView mrv,View v){
			super(v);
			this.mrv=mrv;
			if(mrv.getOnItemClickListener()!=null)
				v.setOnClickListener(this);
			if(mrv.getOnItemLongClickListener()!=null)
				v.setOnLongClickListener(this);
		}
		public Object getObject(){
			return ((Adapter)mrv.getAdapter()).getObject(getListPosition());
		}

		public int getListPosition(){
			return getAdapterPosition()-((Adapter)mrv.getAdapter()).header.size();
		}
		
		@Override
		public void onClick(View p1)
		{
			if(p1==itemView&&mrv.getOnItemClickListener()!=null)
				mrv.getOnItemClickListener().OnItemClick(mrv,this);
		}
		@Override
		public boolean onLongClick(View p1)
		{
			return p1==itemView&&mrv!=null&&mrv.getOnItemLongClickListener().OnItemLongClick(mrv,this);
		}
	}
	public void setOnItemClickListener(OnItemClickListener oicl){
		this.oicl=oicl;
	}
	public void setOnItemLongClickListener(OnItemLongClickListener oilcl){
		this.oilcl=oilcl;
	}
	private OnItemClickListener getOnItemClickListener(){
		return oicl;
	}
	private OnItemLongClickListener getOnItemLongClickListener(){
		return oilcl;
	}
	public abstract interface OnItemClickListener{
		void OnItemClick(MoeRecyclerView mrv,ViewHolder vh);
	}
	public abstract interface OnItemLongClickListener{
		boolean OnItemLongClick(MoeRecyclerView mrv,ViewHolder vh);
	}
	public static abstract class FastScrollBarManager{
		private RecyclerView view;
		private int embedMode;
		void setRecyclerView(RecyclerView view){
			this.view=view;
		}
		public abstract void onDraw(Canvas canvas);
		public void setEmbedMode(int mode){
			embedMode=mode==Embed.MODE_PADDING?Embed.MODE_PADDING:Embed.MODE_FLOAT;
		}
		public int getEmbedMode(){
			return embedMode;
		}
		public class Embed{
			public static final int MODE_PADDING=1;
			public static final int MODE_FLOAT=0;
		}
	}
}
