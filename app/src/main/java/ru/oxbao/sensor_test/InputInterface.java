package ru.oxbao.sensor_test;


public class InputInterface {
    private SensorInput m_sensorInput;

    public enum InputTypeEnum {
        sensors, storage
    }
    public enum ResultTestEnum {
        GOOD, BAD
    }
    public void Start(InputTypeEnum inputTypeEnum){
        if (inputTypeEnum.equals(InputTypeEnum.sensors)){
            m_sensorInput = new SensorInput();
            m_sensorInput.Start();
        }
    }
    public void Stop(){
        m_sensorInput.Stop();
    }



}
