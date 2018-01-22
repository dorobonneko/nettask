package com.moe.network.tasker.utils;

public class NumberUtils
{
	public static final int NONE=0,MONDAY=0x10000,TUESDAY=0x20000,WEDNESDAY=0x40000,THURSDAY=0x80000,FRIDAY=0x100000,SATURDAY=0x200000,SUNDAY=0x400000;
	public static int getTimeHour(int time){
		return (time>>12)&0x1f;
	}
	public static int getTimeMinute(int time){
		return (time>>6)&0x3f;
	}
	public static int getTimeSecond(int time){
		return time&0x3f;
	}
	public static int setTimeHour(int data,int time){
		return (data&0xffff0fff)|((time&0x1f)<<12);
	}
	public static int setTimeMinute(int data,int time){
		return (data&0xfffff03f)|((time&0x3f)<<6);
	}
	public static int setTimeSecond(int data,int time){
		return (data&0xffffffc0)|(time&0x3f);
	}
	//后16位存时间,17～24位存星期数
	public static int addDays(int data,int days){
		if((days&0xff80ffff)!=0)
			throw new RuntimeException("only support monday,tuesday...");
		return data|days;
	}
	public static int removeDays(int data,int days){
		if((days&0xff80ffffff)!=0)
			throw new RuntimeException("only support monday,tuesday...");
		
		return data&(~days);
	}
	public static int setDays(int data,int days){
		if((days&0xff80ffff)!=0)
			throw new RuntimeException("only support monday,tuesday...");
		return (data&0xff80ffff)|(days&(~0xff80ffff));
	}
	//25-32存mode序号
	public static int setModePosition(int data,int position){
		return (data&0x1ffffff)|(position<<25);
	}
	public static int getModePosition(int data){
		return data>>25;
	}
	//第一位代理标记，第二位随机标记！3～32位顺序标记
	public static int setUseProxy(int data,boolean proxy){
		return proxy?(data|1):(data&(~1));
	}
	public static int setUseRandom(int data,boolean random){
		return random?(data|2):(data&(~2));
	}
	public static boolean isProxy(int data){
		return (data&1)==1;
	}
	public static boolean isRandom(int data){
		return (data&2)==2;
	}
	public static int setUserAgentNumber(int data,int number){
		return (data&3)|(number<<2);
	}
	public static int getUserAgentNumber(int data){
		return data>>2;
	}
}
