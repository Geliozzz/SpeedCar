package ru.oxbao.sensor_test;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorInput implements SensorEventListener {
    public void Start(){

    }

    public void Stop(){

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
           Collector.Collect();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
