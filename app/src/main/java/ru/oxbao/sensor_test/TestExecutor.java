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
<<<<<<< HEAD
        m_collector.Start(InputInterface.InputTypeEnum.sensors); // TestExecutor запускает Collector
        //Получать данные с датчика или считывать их из файла(-ов) определяется параметром
        // метода Collector’a, который вызывает TestExecutor.
        // В тестЭкзекуторе пока этот параметр задать хардкодом.
    }

    public Solutions.ResultTestEnum Analyze(TestData testData){

      //  TestExecutor через вызов метода Saver
      // сохраняет TestData в файл(-ы) (если установлен соответствующий флаг
        m_ownerActivity.SetResultAnalyze(Solutions.ResultTestEnum.GOOD);
        if (m_saveData){
            m_saver.SaveInFile(testData);
        }
        return Solutions.ResultTestEnum.GOOD;
    }
=======
        Collector collector = new Collector();
        collector.Start(m_fromSensor, this);
    }
    public InputInterface.ResultTestEnum Analyze(TestData testData){
        return InputInterface.ResultTestEnum.GOOD;
    }

>>>>>>> 66531d8cde43049e596bd73b4650f29978cb631b


}
