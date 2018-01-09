package com.moe.network.tasker.entity;
import android.os.Parcelable;
import android.os.Parcel;

public class Timer implements Parcelable
{
	private Task task;
	private int id,time;
	private String summary;
	public Timer(){}
	public Timer(Parcel p){
		task=p.readParcelable(Task.class.getClassLoader());
		summary=p.readString();
		time=p.readInt();
		id=p.readInt();
	}

	public void setTask(Task task)
	{
		this.task = task;
	}

	public Task getTask()
	{
		return task;
	}

	public void setSummary(String summary)
	{
		this.summary = summary;
	}

	public String getSummary()
	{
		return summary;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setTime(int time)
	{
		this.time = time;
	}

	public int getTime()
	{
		return time;
	}
	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p1, int p2)
	{
		p1.writeParcelable(task,p2);
		p1.writeString(summary);
		p1.writeInt(time);
		p1.writeInt(id);
	}
	public static Parcelable.Creator<? extends Timer> CREATOR=new Parcelable.Creator<Timer>(){

		@Override
		public Timer createFromParcel(Parcel p1)
		{
			return new Timer(p1);
		}

		@Override
		public Timer[] newArray(int p1)
		{
			return new Timer[p1];
		}

		};
}
