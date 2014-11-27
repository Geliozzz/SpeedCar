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
<<<<<<< HEAD

    public void Start(InputTypeEnum inputTypeEnum){
        m_sensorInput.Start();
=======
    public void Start(InputTypeEnum inputTypeEnum, Collector collector){
        if (inputTypeEnum.equals(InputTypeEnum.sensors)){
            m_sensorInput = new SensorInput();
            m_sensorInput.Start(collector);
        }
>>>>>>> 66531d8cde43049e596bd73b4650f29978cb631b
    }
    public void Stop(){
        m_sensorInput.Stop();
    }



}
