package ru.oxbao.sensor_test;


public class TestExecutor {
    private boolean m_saveData = true;
    private Collector m_collector;
    private Saver m_saver;
    public TestData g_testData;
    private int m_numberOfMeasurements = 1000;
    private TestExecutorActivity m_ownerActivity;

    public TestExecutor(TestExecutorActivity testExecutorActivity) {
        m_collector = new Collector(testExecutorActivity, this ,m_numberOfMeasurements);
        m_saver = new Saver();
        g_testData = new TestData(m_numberOfMeasurements);
        m_ownerActivity = testExecutorActivity;
    }

    public void Start(){
        m_collector.Start(InputInterface.InputTypeEnum.sensors); // TestExecutor запускает Collector
        //Получать данные с датчика или считывать их из файла(-ов) определяется параметром
        // метода Collector’a, который вызывает TestExecutor.
        // В тестЭкзекуторе пока этот параметр задать хардкодом.
    }

    private Solutions.ResultTestEnum Analyze(TestData testData){


        m_ownerActivity.OnTestFinished(Solutions.ResultTestEnum.GOOD);
        return Solutions.ResultTestEnum.GOOD;
    }

    public void OnDataCollected(){
        m_collector.Stop();
        Analyze(g_testData);
        if (m_saveData){
            m_saver.SaveInFile(g_testData);
        }
    }
}
