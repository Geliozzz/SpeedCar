package ru.oxbao.sensor_test;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorInput implements SensorEventListener {
    Collector m_collector;
    public void Start(Collector collector){
         m_collector = collector;
    }

    public void Stop(){

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
           m_collector.Collect();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
