package com.example.timerangetest;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Vector;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

	class RangeTimeType
	{
		private int stDay;
		private int edDay;
		private Calendar stCal;
		private Calendar edCal;
		
		public void setStartTime(int day, int hour, int min)
		{		
			stDay = day;
			stCal = Calendar.getInstance();
		    stCal.set(Calendar.HOUR_OF_DAY, hour);
		    stCal.set(Calendar.MINUTE, min);
		}
		
		public void setEndTime(int day, int hour, int min)
		{		
			edDay = day;
			edCal = Calendar.getInstance();
			edCal.set(Calendar.HOUR_OF_DAY, hour);
			edCal.set(Calendar.MINUTE, min);
		}
		
		public String getPayload()
		{
			String result = "";
			
			DecimalFormat formatter = new DecimalFormat("00");
			String stHour = formatter.format(stCal.get(Calendar.HOUR_OF_DAY));
			String stMin = formatter.format(stCal.get(Calendar.MINUTE));
			String edHour = formatter.format(edCal.get(Calendar.HOUR_OF_DAY));
			String edMin = formatter.format(edCal.get(Calendar.MINUTE));
			
			result+= String.format("(66,%d%s%s%d%s%s)", stDay, stHour, stMin, edDay, edHour, edMin);
			return result;
		}
	}
	public String getTimeRangePayload(Vector<RangeTimeType> vecTimeWeeks)
	{
		String result = "";

		for (RangeTimeType item:vecTimeWeeks)
		{
			result+= item.getPayload();
		}					

		return result;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
       
        RangeTimeType rt1 = new RangeTimeType();
        rt1.setStartTime(1, 8, 30);
        rt1.setEndTime(1, 18, 45);
        
      
        RangeTimeType rt2 = new RangeTimeType();
        rt2.setStartTime(2, 3, 1);
        rt2.setEndTime(3, 22, 2);
        
        Vector<RangeTimeType> vec = new Vector<RangeTimeType>();
        vec.add(rt1);
        vec.add(rt2);
        
        Log.v("xxxxxx", getTimeRangePayload(vec));
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

