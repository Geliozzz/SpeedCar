package ru.oxbao.sensor_test;


public class TestExecutor {
    private boolean m_fromSensor = true;
    public void Start(){
        Collector collector = new Collector();
        collector.Start(m_fromSensor);
    }
}
