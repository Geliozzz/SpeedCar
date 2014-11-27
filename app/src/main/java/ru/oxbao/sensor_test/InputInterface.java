package ru.oxbao.sensor_test;


public class InputInterface {
    private SensorInput m_sensorInput;
    private StorageInput m_storageInput;

    public enum InputTypeEnum {
        sensors, storage
    }

    public InputInterface(TestExecutorActivity testExecutorActivity ,Collector collector) {
        m_sensorInput = new SensorInput(testExecutorActivity, collector);
        m_storageInput = new StorageInput();
    }


    public void Start(InputTypeEnum inputTypeEnum){
        m_sensorInput.Start();
    }
    public void Stop(){
        m_sensorInput.Stop();
    }



}
