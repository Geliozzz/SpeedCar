package ru.oxbao.sensor_test;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorInput implements SensorEventListener {
    private Collector m_collectorOwner;
    private TestExecutorActivity m_ownerActivity;

    public SensorInput(TestExecutorActivity testExecutorActivity ,Collector collector) {
        m_collectorOwner = collector;
        m_ownerActivity = testExecutorActivity;
    }

    public void Start(){

    }

    public void Stop(){

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
           //  В датчике внутренний датчик реализован (он сам генерирует события выдачи новых данных)
            //В любом случае xxxInput циклично дёргает метод Collector’a
            m_collectorOwner.Amass();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
