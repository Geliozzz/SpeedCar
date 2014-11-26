package ru.oxbao.sensor_test;


public class Collector {
    private InputInterface m_inputInterface;
    private int count;
    private TestExecutor m_testExecutor;
    private TestData m_testData;
    public void Start(boolean input, TestExecutor testExecutor){
        m_inputInterface = new InputInterface();
        m_testExecutor = testExecutor;
        if (input){
            m_inputInterface.Start(InputInterface.InputTypeEnum.sensors , this);
        } else{

        }
    }

    public void Collect(){
        //Заполняем тестДата
        count++;
        //после заполнения стоп
        m_inputInterface.Stop();
        m_testExecutor.Analyze(m_testData);
    }
}
