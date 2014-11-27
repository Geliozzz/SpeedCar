package ru.oxbao.sensor_test;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class SensorInput implements SensorEventListener {
<<<<<<< HEAD
    private Collector m_collectorOwner;
    private TestExecutorActivity m_ownerActivity;

    public SensorInput(TestExecutorActivity testExecutorActivity ,Collector collector) {
        m_collectorOwner = collector;
        m_ownerActivity = testExecutorActivity;
    }

    public void Start(){

=======
    Collector m_collector;
    public void Start(Collector collector){
         m_collector = collector;
>>>>>>> 66531d8cde43049e596bd73b4650f29978cb631b
    }

    public void Stop(){

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
<<<<<<< HEAD
           //  В датчике внутренний датчик реализован (он сам генерирует события выдачи новых данных)
            //В любом случае xxxInput циклично дёргает метод Collector’a
            m_collectorOwner.Amass();
=======
           m_collector.Collect();
>>>>>>> 66531d8cde43049e596bd73b4650f29978cb631b
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
