package com.tongxin.map;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class MyOrientationListener implements SensorEventListener {
	 	private Context context;  
	    private SensorManager sensorManager;  
	    private Sensor sensor;  	      
	    private float lastX=0 ; 
	  
	    public MyOrientationListener(Context context)  
	    {  
	        this.context = context;  
	    }  
	  

	    public void start()  
	    {  

	        sensorManager = (SensorManager) context  
	                .getSystemService(Context.SENSOR_SERVICE);  
	        if (sensorManager != null)  
	        {
	            sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);  
	        }  

	        if (sensor != null)  
	        {//SensorManager.SENSOR_DELAY_UI  
	            sensorManager.registerListener(this, sensor,  
	                    SensorManager.SENSOR_DELAY_UI);  
	        } 
	  
	    }
	    public void stop()  
	    {  
	        sensorManager.unregisterListener(this);  
	    }
		@Override
		public void onAccuracyChanged(Sensor arg0, int arg1) {

			
		}
	
		@Override
		public void onSensorChanged(SensorEvent event) {

	        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION)    
	        {    

	            lastX = event.values[0]; 
	        } 
		}
		public float get_angel()
		{
			return lastX;
		}

}
