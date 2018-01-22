package com.moe.network.tasker.database;
import android.database.sqlite.*;
import com.moe.network.tasker.entity.*;

import android.content.Context;
import android.database.Cursor;
import com.moe.network.tasker.utils.NumberUtils;
import java.util.List;
import java.util.ArrayList;

public class NetTaskerDataBase extends SQLiteOpenHelper
{
	private static NetTaskerDataBase ntdb;
	private SQLiteDatabase sql;
	@Override
	public void onCreate(SQLiteDatabase p1)
	{
		p1.execSQL("create table tasks(id integer primary key autoincrement,url text,proxy integer default(0),useragent text,method text default('GET'),timer integer default(0),name text,random integer)");
		p1.execSQL("create table timers(id integer primary key autoincrement,time integer,summary text,task integer,foreign key (task) references tasks(id))");
		p1.execSQL("create table taskdatas(id integer primary key autoincrement,task integer,query text,cookie text,foreign key (task) references tasks(id))");
		
		}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
	}
	
	private NetTaskerDataBase(Context context){
		super(context.getApplicationContext(),"nettasker",null,3);
		sql=getReadableDatabase();
	}
	public static NetTaskerDataBase getInstance(Context context){
		if(ntdb==null)ntdb=new NetTaskerDataBase(context);
		return ntdb;
	}
	public boolean addTask(Task task){
		sql.acquireReference();
		sql.beginTransaction();
		SQLiteStatement state=sql.compileStatement("insert into tasks(url,proxy,useragent,method,timer,name,random) values(?,?,?,?,?,?,?)");
		state.bindString(1,task.getUrl());
		state.bindLong(2,NumberUtils.isProxy(task.getUse())?1:0);
		state.bindString(3,task.getUserAgent());
		state.bindString(4,task.getMethod());
		state.bindLong(5,task.getTimer());
		state.bindString(6,task.getName());
		state.bindLong(7,NumberUtils.isRandom(task.getUse())?1:0);
		try{
			state.executeInsert();
			sql.setTransactionSuccessful();
			return true;
		}catch(Exception e){
			
		}finally{
		sql.endTransaction();
		sql.releaseReference();
		}
		return false;
	}
	public void addTimer(Timer timer){
		int taskId=timer.getTask().getId();
		if(taskId<0){
			addTask(timer.getTask());
			Cursor cursor=sql.rawQuery("select max(id) from tasks where url=? and name=?",new String[]{timer.getTask().getUrl(),timer.getTask().getName()});
			if(cursor.moveToNext()){
				taskId=cursor.getInt(0);
				cursor.close();
			}else{ 
				cursor.close();
				throw new NullPointerException("task is null");
			}
		}else{
			SQLiteStatement state=sql.compileStatement("update tasks set timer=1 where id=?");
			state.bindLong(1,taskId);
			state.executeUpdateDelete();
		}
		sql.acquireReference();
		sql.beginTransaction();
		SQLiteStatement state=sql.compileStatement("insert into timers(time,summary,task) values(?,?,?)");
		state.bindLong(3,taskId);
		state.bindLong(1,timer.getTime());
		state.bindString(2,timer.getSummary());
		try{
			state.executeInsert();
			sql.setTransactionSuccessful();
		}catch(Exception e){

		}
		sql.endTransaction();
		sql.releaseReference();
	}
	public void addTaskData(TaskData td){
		sql.beginTransaction();
		SQLiteStatement state=sql.compileStatement("insert into taskdatas(task,query,cookie) values(?,?,?)");
		state.acquireReference();
		state.bindLong(1,td.getTask());
		state.bindString(2,td.getQuery());
		state.bindString(3,td.getQuery());
		try{
			state.executeInsert();
			sql.setTransactionSuccessful();
		}catch(Exception e){

		}
		sql.endTransaction();
		sql.releaseReference();
	}
	public void deleteTask(int id){
		sql.delete("tasks","id=?",new String[]{id+""});
		sql.delete("taskdatas","task=?",new String[]{id+""});
	}
	public void deleteTimer(int id,boolean withTask){
		if(withTask){
			Cursor c=sql.rawQuery("select task from timers where id=?",new String[]{id+""});
			if(c.moveToNext())
			deleteTask(c.getInt(0));
			c.close();
		}
		sql.delete("timers","id=?",new String[]{id+""});
		
	}
	public List<Task> queryTask(boolean normal){
		Cursor c=sql.rawQuery("select * from tasks where timer"+(normal?"=":"!=")+"0",null);
		List<Task> list=new ArrayList<>();
		while(c.moveToNext()){
			Task task=new Task();
			task.setId(c.getInt(c.getColumnIndex("id")));
			task.setMethod(c.getString(c.getColumnIndex("method")));
			task.setName(c.getString(c.getColumnIndex("name")));
			task.setUrl(c.getString(c.getColumnIndex("url")));
			task.setTimer(c.getInt(c.getColumnIndex("timer")));
			task.setUseProxy(c.getInt(c.getColumnIndex("proxy"))==1);
			task.setUseRandom(c.getInt(c.getColumnIndex("random"))==1);
			task.setUserAgent(c.getString(c.getColumnIndex("useragent")));
			list.add(task);
		}
		c.close();
		return list;
	}
	public Timer queryTimer(Task task){
		Cursor c=sql.rawQuery("select * from timers where task=?",new String[]{task.getId()+""});
		while(c.moveToNext()){
		Timer timer=new Timer();
		timer.setId(c.getInt(c.getColumnIndex("id")));
		timer.setSummary(c.getString(c.getColumnIndex("summary")));
		timer.setTask(task);
		timer.setTime(c.getInt(c.getColumnIndex("time")));
		c.close();
		return timer;
		}c.close();
		return null;
	}
	/*
	public Task queryTask(Timer t,int id){
		Cursor c=sql.rawQuery("select * from tasks where id=?",new String[]{id+""});
		if(c.moveToNext()){
			Task task=new Task();
			task.setId(id);
			task.setMethod(c.getString(c.getColumnIndex("method")));
			task.setName(c.getString(c.getColumnIndex("name")));
			task.setTimer(c.getInt(c.getColumnIndex("timer"));
			task.setUrl(c.getString(c.getColumnIndex("url")));
			task.setUse(NumberUtils.setUseProxy(task.getUse(),c.getInt(c.getColumnIndex("proxy"))==1));
			task.setUse(NumberUtils.setUseProxy(task.getUse(),c.getInt(c.getColumnIndex("random"))==1));
			task.setUserAgent(c.getString(c.getColumnIndex("useragent")));
			return task;
		}
		return null;
	}*/
}
