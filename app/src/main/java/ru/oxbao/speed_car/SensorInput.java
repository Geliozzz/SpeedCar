package ru.oxbao.speed_car;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class SensorInput implements SensorEventListener
{
    //Objects
    private InputOwnerInterface m_inputOwnerInterface;
    private SensorManager m_sensorManager;
    //Variable
    private boolean m_sensorIsWorking;
    private final String SENSOR_INPUT = "SensorInput";

    public SensorInput(InputOwnerInterface inputOwnerInterface, SensorManager sensorManager)
    {
        m_inputOwnerInterface = inputOwnerInterface;
        m_sensorManager = sensorManager;
        m_sensorIsWorking = false;
    }

    public void Start()
    {
        Sensor s = m_sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (s != null)
        {
            m_sensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_FASTEST);
            m_sensorIsWorking = true;
        } else
        {
            m_inputOwnerInterface.ShowFailSensor();
            m_inputOwnerInterface.Stop();
        }
    }

    public void Stop()
    {
        m_sensorManager.unregisterListener(this);
        m_sensorIsWorking = false;
        Log.d(SENSOR_INPUT, "SensorInput stopped");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent)
    {
        m_inputOwnerInterface.Amass(sensorEvent.values[0], sensorEvent.values[1],
                sensorEvent.values[2], sensorEvent.timestamp);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {

    }

    public boolean IsWorking()
    {
        return m_sensorIsWorking;
    }

    @Override
    protected void finalize() throws Throwable
    {
        Stop();
        super.finalize();
    }
}
