package com.moe.network.tasker.database;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import com.moe.network.tasker.entity.Task;
import com.moe.network.tasker.entity.Timer;
import android.database.sqlite.SQLiteStatement;
import android.database.Cursor;

public class NetTaskerDataBase extends SQLiteOpenHelper
{
	private SQLiteDatabase sql;
	@Override
	public void onCreate(SQLiteDatabase p1)
	{
		p1.execSQL("create table tasks(id integer autoincrement primary key,url text,proxy integer default(0),useragent text,method text default('GET'),timer integer,name text)");
		p1.execSQL("create table taskdatas(id integer autoincrement,task integer,query text,cookie text,primary key(id,task),foreign key (task) references tasks(id))");
		p1.execSQL("create table timers(id integer autoincrement primary key,time integer,summary text,task integer,foreign key (task) references tasks(id))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
	}
	
	private NetTaskerDataBase(Context context){
		super(context.getApplicationContext(),"nettasker",null,3);
		sql=getReadableDatabase();
	}
	public void addTask(Task task){
		sql.acquireReference();
		sql.beginTransaction();
		SQLiteStatement state=sql.compileStatement("insert into tasks(url,proxy,useragent,method,timer,name) values(?,?,?,?,?,?)");
		state.bindString(1,task.getUrl());
		state.bindLong(2,task.isUseProxy()?1:0);
		state.bindString(3,task.getUserAgent());
		state.bindString(4,task.getMethod());
		state.bindLong(5,task.isTimer()?1:0);
		state.bindString(6,task.getName());
		try{
			state.executeInsert();
			sql.setTransactionSuccessful();
		}catch(Exception e){
			
		}
		sql.endTransaction();
		sql.releaseReference();
	}
	public void addTimer(Timer timer){
		int taskId=timer.getTask().getId();
		if(taskId==-1){
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
	public void deleteTask(int id){
		sql.delete("tasks","id=?",new String[]{id+""});
	}
	public void deleteTimer(int id,boolean withTask){
		Timer timer=queryTimer(id);
		if(withTask){
			
		}else{
			
		}
		sql.delete("timers","id=?",new String[]{id+""});
		
	}
	public Timer queryTimer(int id){
		return null;
	}
	public Task queryTask(int id){
		return null;
	}
}
