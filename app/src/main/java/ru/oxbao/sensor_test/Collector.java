package ru.oxbao.sensor_test;


import android.util.Log;

public class Collector {
    private InputInterface m_inputInterface;
    private int m_numberOfMeasurements;
    private int m_count;
    private TestExecutor m_ownerExecutor;
    private TestExecutorActivity m_ownerActivity;
    private final String COLLECTOR_TAG = "Collector";

    public Collector(TestExecutorActivity testExecutorActivity, TestExecutor testExecutor, int number) {
        m_inputInterface = new InputInterface(testExecutorActivity, testExecutor, this);
        m_numberOfMeasurements = number;
        m_ownerExecutor = testExecutor;
        m_ownerActivity = testExecutorActivity;
        m_count = 0;
    }

    public void Start(InputInterface.InputTypeEnum inputTypeEnum) {
        m_inputInterface.Start(inputTypeEnum);
        m_count = 0;
    }

    public void Stop(){
        m_inputInterface.Stop();
    }

    public void Amass(double XAxis, double YAxis, double ZAxis, long time) {
        try {
            m_ownerExecutor.g_testData.XAxis[m_count] = XAxis;
            m_ownerExecutor.g_testData.YAxis[m_count] = YAxis;
            m_ownerExecutor.g_testData.ZAxis[m_count] = ZAxis;
            m_ownerExecutor.g_testData.TimeInMilliseconds[m_count] = time;
        } catch (IndexOutOfBoundsException e){
            Log.d(COLLECTOR_TAG, "index testData is out of range");
        } catch (Exception e){
            e.printStackTrace();
            Log.d(COLLECTOR_TAG, "Anything exception ");
        }
        m_ownerActivity.SetTextViews(XAxis, YAxis, ZAxis);
        m_count++;
        m_ownerActivity.SetProgressBar(m_count);
        if (m_count > m_numberOfMeasurements - 1){
            m_ownerExecutor.OnDataCollected();     // Окончание накопления
        }



    }
}
