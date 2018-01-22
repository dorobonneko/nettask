package com.moe.network.tasker.entity;
import android.os.Parcelable;
import android.os.Parcel;

public class TaskData implements Parcelable
{
	private int id,task;
	private String query,cookie;
	public TaskData(Parcel p){
		id=p.readInt();
		task=p.readInt();
		query=p.readString();
		cookie=p.readString();
	}
	public TaskData()
	{}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setTask(int task)
	{
		this.task = task;
	}

	public int getTask()
	{
		return task;
	}

	public void setQuery(String query)
	{
		this.query = query;
	}

	public String getQuery()
	{
		return query;
	}

	public void setCookie(String cookie)
	{
		this.cookie = cookie;
	}

	public String getCookie()
	{
		return cookie;
	}
	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p1, int p2)
	{
		p1.writeInt(id);
		p1.writeInt(task);
		p1.writeString(query);
		p1.writeString(cookie);
	}
	public static Parcelable.Creator<? extends TaskData> CREATOR=new Parcelable.Creator<TaskData>(){

		@Override
		public TaskData createFromParcel(Parcel p1)
		{
			return new TaskData(p1);
		}

		@Override
		public TaskData[] newArray(int p1)
		{
			return new TaskData[p1];
		}
	};
		
}
