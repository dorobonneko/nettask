package com.moe.network.tasker.entity;
import android.os.Parcelable;
import android.os.Parcel;
import com.moe.network.tasker.utils.NumberUtils;

public class Task implements Parcelable
{
	private String url,userAgent,method,name;
	private int use,id,timer;
	public Task()
	{}
	public Task(Parcel p)
	{
		url=p.readString();
		userAgent=p.readString();
		method=p.readString();
		use=p.readInt();
		id=p.readInt();
		timer=p.readInt();
		name=p.readString();
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}

	public String getUserAgent()
	{
		return userAgent;
	}

	public void setMethod(String method)
	{
		this.method = method;
	}

	public String getMethod()
	{
		return method;
	}

	public void setUseProxy(boolean use)
	{
		this.use = NumberUtils.setUseProxy(this.use,use);
	}
	public void setUseRandom(boolean use){
		this.use=NumberUtils.setUseRandom(this.use,use);
	}
	public int getUse()
	{
		return use;
	}
	public void setUse(int use){
		this.use=use;
	}
	public boolean isUseProxy(){
		return NumberUtils.isProxy(use);
	}
	public boolean isUseRandom(){
		return NumberUtils.isRandom(use);
	}
	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setTimer(int timer)
	{
		this.timer = timer;
	}

	public int getTimer()
	{
		return timer;
	}
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel p1, int p2)
	{
		p1.writeString(url);
		p1.writeString(userAgent);
		p1.writeString(method);
		p1.writeInt(use);
		p1.writeInt(id);
		p1.writeInt(timer);
		p1.writeString(name);
	}
	public static Parcelable.Creator<? extends Task> CREATOR=new Parcelable.Creator<Task>(){

		@Override
		public Task createFromParcel(Parcel p1)
		{
			return new Task(p1);
		}

		@Override
		public Task[] newArray(int p1)
		{
			return new Task[p1];
		}
	};

}
