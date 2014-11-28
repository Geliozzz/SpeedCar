package ru.oxbao.sensor_test;


import android.widget.Toast;

public class TestExecutor {
    private boolean m_saveData = true;
    private Collector m_collector;
    private WorkMath m_workMath;
    private Saver m_saver;
    public TestData g_testData;
    private int m_numberOfMeasurements = 1000;
    private TestExecutorActivity m_ownerActivity;
    private String m_prefix = "UnKnown";
    public enum ToastMessage {
        failSaveData, failStartSensor
    }
    public enum TestEnum {
        test1, test2
    }

    public TestExecutor(TestExecutorActivity testExecutorActivity) {
        m_collector = new Collector(testExecutorActivity, this ,m_numberOfMeasurements);
        g_testData = new TestData(m_numberOfMeasurements);
        m_saver = new Saver(m_prefix, this);
        m_workMath = new WorkMath();
        m_ownerActivity = testExecutorActivity;
        m_ownerActivity.SetMaxProgressBar(m_numberOfMeasurements);

    }

    public void Start(TestEnum testEnum){
        m_collector.Start(InputInterface.InputTypeEnum.sensors);
        m_saver.SetPrefix(testEnum.toString());

    }

    public void Stop(){
        m_collector.Stop();
    }

    public void OnDataCollected(){
        m_collector.Stop();
        m_workMath.Analyze(g_testData);
        m_ownerActivity.OnTestFinished(Solutions.ResultTestEnum.GOOD);
        if (m_saveData){
            m_saver.SaveInFile(g_testData);
        }
    }

    public void ShowToast(ToastMessage toastMessage){
        if (toastMessage.equals(ToastMessage.failSaveData)){
            Toast toast = Toast.makeText(m_ownerActivity.getApplicationContext(), m_ownerActivity.getApplicationContext().getResources().getString(R.string.failSaveFile), Toast.LENGTH_SHORT);
            toast.show();
        }
        if (toastMessage.equals(ToastMessage.failStartSensor)){
            Toast toast = Toast.makeText(m_ownerActivity.getApplicationContext(), m_ownerActivity.getApplicationContext().getResources().getString(R.string.failAccelerometer), Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
