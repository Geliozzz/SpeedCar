package ru.oxbao.sensor_test;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import java.util.List;

public class Sensors implements SensorEventListener {
    private List<Integer> m_requiredTypesOfSensors;
    private TestExecutorActivity m_owner;
    private SensorManager m_sensorManager;
    private boolean m_sensorIsWorking;

    Sensors(TestExecutorActivity testExecutorActivity, Context context, List<Integer> requiredTypesOfSensors) {
        m_owner = testExecutorActivity;
        m_sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        m_requiredTypesOfSensors = requiredTypesOfSensors;
        m_sensorIsWorking = false;
    }

    @Override
    protected void finalize() throws Throwable {
        Stop();
        super.finalize();
    }

    public void Start() {
        for (int i = 0; i < m_requiredTypesOfSensors.size(); i++) {
            Sensor s = m_sensorManager.getDefaultSensor(m_requiredTypesOfSensors.get(i));
            if (s != null) {
                m_sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_FASTEST);
            } else {
                m_requiredTypesOfSensors.remove(i);
            }
        }

        m_sensorIsWorking = true;
    }

    public void Stop() {
        m_sensorManager.unregisterListener(this);
        m_sensorIsWorking = false;
    }

    public boolean IsWorking() {
        return m_sensorIsWorking;
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            m_owner.Collector(sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2], System.currentTimeMillis());

            //m_owner.StoreShowSensorValues();
        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
          /*  m_sensorValues.SetGyroscopesValues(sensorEvent.values[0],
                sensorEvent.values[1], sensorEvent.values[2], sensorEvent.timestamp);*/
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public int NumberOfWorkingSensors() {
        return m_requiredTypesOfSensors.size();
    }


}
