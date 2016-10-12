package com.example.timerangetest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeRangeInfo implements Cloneable {
	protected Integer iBeginSecOfSec = null;
	protected Integer iBeginSecOfMin = null;
	protected Integer iBeginMinOfHour = null;
	protected Integer iBeginHourOfDay = null;
	
	protected Integer iEndSecOfSec = null;
	protected Integer iEndSecOfMin = null;
	protected Integer iEndMinOfHour = null;
	protected Integer iEndHourOfDay = null;
	
	public TimeRangeInfo()
	{
		iBeginSecOfSec = 0;
		iBeginSecOfMin = 0;
		iBeginMinOfHour = 0;
		iBeginHourOfDay = 0;
		
		iEndSecOfSec = 0;
		iEndSecOfMin = 0;
		iEndMinOfHour = 0;
		iEndHourOfDay = 0;
	}
	
	public void SetBegin(int D, int H, int M)
	{
		iBeginSecOfSec = 0;
		iBeginSecOfMin = M;
		iBeginMinOfHour = H;
		iBeginHourOfDay = D;
	}
	
	public void SetEnd(int D, int H, int M)
	{
		iEndSecOfSec = 0;
		iEndSecOfMin = M;
		iEndMinOfHour = H;
		iEndHourOfDay = D;
	}
	
	private long Different(int bHour, int bMin, int bSec, int eHour, int eMin, int eSec)
	{
		int totalSecB = (bHour*60*60 + bMin*60 + bSec); 
		int totalSecE = (eHour*60*60 + eMin*60 + eSec);
		
		int totalSec = 0;

		if (bHour>eHour)
		{
			totalSec = 86400 - totalSecB + totalSecE;		
		} else {
			totalSec = totalSecE - totalSecB;
		}

		
		int hours =  (int)Math.floor((double)totalSec / 3600);	
		int minutes =  (int)Math.floor((double)(totalSec / 60 % 60));
		int seconds = totalSec % 60;
		
		Calendar calCurrent = Calendar.getInstance();	
		calCurrent.set(Calendar.HOUR_OF_DAY, hours);
		calCurrent.set(Calendar.MINUTE, minutes);
		calCurrent.set(Calendar.SECOND, seconds);
		return calCurrent.getTimeInMillis();
	}
	
	public boolean IsOnRange()
	{
		if (iBeginHourOfDay == 0 && iEndHourOfDay == 0)
		{
			//every day
			Calendar calCurrent = Calendar.getInstance();	
			
			long lRange = Different(iBeginMinOfHour, iBeginSecOfMin, iBeginSecOfSec, iEndMinOfHour, iEndSecOfMin, iEndSecOfSec);
			long lCurRange = Different(iBeginMinOfHour, iBeginSecOfMin, iBeginSecOfSec, calCurrent.get(Calendar.HOUR_OF_DAY), calCurrent.get(Calendar.MINUTE), calCurrent.get(Calendar.SECOND));
			if ( lCurRange<lRange)
			{
				return true;
			}
			return false;
		}
		
		//every week
		
		Calendar calCurrent = Calendar.getInstance();				
		Calendar calB = Calendar.getInstance();
		Calendar calE = Calendar.getInstance();
		
		calB.set(Calendar.HOUR_OF_DAY, iBeginMinOfHour);
		calB.set(Calendar.MINUTE, iBeginSecOfMin);
		calB.set(Calendar.SECOND, iBeginSecOfSec);
		
		calE.set(Calendar.HOUR_OF_DAY, iEndMinOfHour);
		calE.set(Calendar.MINUTE, iEndSecOfMin);
		calE.set(Calendar.SECOND, iEndSecOfSec);
		
		int weekday, weekdayB, weekdayE;
		weekday = calCurrent.get(Calendar.DAY_OF_WEEK);
		weekdayB = iBeginHourOfDay;
		weekdayE = iEndHourOfDay;
	
		calB.add(Calendar.DAY_OF_MONTH, weekdayB-weekday);
		calE.add(Calendar.DAY_OF_MONTH, weekdayE-weekday);			
		/*
		1.SUNDAY
		2.MONDAY
		3.TUESDAY
		4.WEDNESDAY
		5.THURSDAY
		6.FRIDAY
		7.SATURDAY
		
		S  1  2  3  4  5  6 
		26 27 28 29 30 01 02
		03 04 05 06 07 08 09
		10 11 12 13 14 15 16
		
		today 04 index 2
		set S index 1
		set 1 index 2
		1-2 = -1
		2-2 = +0				
		*/
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String strB = df.format(calB.getTime());
		String strE = df.format(calE.getTime());
		String strC = df.format(calCurrent.getTime());
		
		String strDebug = "B:"+strB+"\nC:"+strC+"\nE:"+strE;
		
		long lbms = calB.getTimeInMillis();
		long lems = calE.getTimeInMillis();
		long lcms = calCurrent.getTimeInMillis();
		if ( lbms < lcms && lcms < lems)
		{
			return true;
		}		
		return false;
	}
}

