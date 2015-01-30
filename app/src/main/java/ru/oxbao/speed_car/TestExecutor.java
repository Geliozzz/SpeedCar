package ru.oxbao.speed_car;


import android.widget.ArrayAdapter;
import android.widget.Toast;

public class TestExecutor
{
    public enum ToastMessage
    {
        failSaveData,
        failStartSensor
    }

    public enum TestEnum
    {
        test1,
        test2
    }

    // Flags, parameters
    private boolean m_saveData = true;
    private int m_numberOfMeasurements;
    private String m_prefix = "UnKnown";
    private int m_speed;
    private int m_needSpeed;
    // Objects
    private Collector m_collector;
    private RealtimeInputInterface m_realtimeInputInterface;
    private Saver m_saver;
    public TestData g_testData;
    private WorkMath m_workMath;
    private ActivityTestExecutor m_ownerActivity;
    // Output
    public TestResult g_testResult;

    public TestExecutor(ActivityTestExecutor activityTestExecutor, TestResult testResult, int needSpeed)
    {
        m_workMath = new WorkMath();
        m_numberOfMeasurements = WorkMath.NumberOfMeasurements;
        m_ownerActivity = activityTestExecutor;
        m_ownerActivity.SetMaxProgressBar(m_numberOfMeasurements);
        g_testResult = testResult;

        g_testData = new TestData(m_numberOfMeasurements);
        m_collector = new Collector(activityTestExecutor, this, m_numberOfMeasurements);
        m_realtimeInputInterface = new RealtimeInputInterface(activityTestExecutor, this);
        m_saver = new Saver(this, m_prefix);
        m_needSpeed = needSpeed;

    }

    public void Start(TestEnum testEnum)
    {
        if (testEnum.equals(TestEnum.test1))
        {
            m_collector.Start(InputAdapter.InputTypeEnum.sensors);
            m_saveData = true;
        } else if (testEnum.equals(TestEnum.test2))
        {
            m_collector.Start(InputAdapter.InputTypeEnum.storage);
            m_saveData = false;
        }
        m_saver.SetPrefix(testEnum.toString());
        //**********Сделано для для отладник. В реальном приложеннии скорость будет измеряться!!!!!!!!!!!!!!!!!!!!!
        m_speed = 0;
        //**********************************************************************
    }

    /**
     * <p>Используется для остановки выполнения теста</p>
     */

    public void Stop()
    {
        m_collector.Stop();
        m_realtimeInputInterface.Stop();
    }


    public void OnDataCollected()
    {
        if (m_saveData)
        {
            m_saver.SaveData(g_testData, false);
        } else
        {
            m_saver.SaveData(g_testData, true); // for alternative
        }
        m_ownerActivity.OnCalibrateFinished();
        m_realtimeInputInterface.Start();
    }

    public void ShowToast(ToastMessage toastMessage)
    {
        if (toastMessage.equals(ToastMessage.failSaveData))
        {
            Toast toast = Toast.makeText(m_ownerActivity.getApplicationContext(), m_ownerActivity.getApplicationContext().getResources().getString(R.string.failSaveFile), Toast.LENGTH_SHORT);
            toast.show();
        }
        if (toastMessage.equals(ToastMessage.failStartSensor))
        {
            Toast toast = Toast.makeText(m_ownerActivity.getApplicationContext(), m_ownerActivity.getApplicationContext().getResources().getString(R.string.failAccelerometer), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public ArrayAdapter GetFilesNames()
    {
        try
        {
            String[] files = m_collector.GetFilesNames();
            if (files.length == 0)
            {
                return null;
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(m_ownerActivity, R.layout.support_simple_spinner_dropdown_item, files);
            return arrayAdapter;
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public String GetCheckedSpinner()
    {
        return m_ownerActivity.GetCheckedSpinner();
    }

    public void SetFixedTestData()
    {
        g_testData = new TestData(m_numberOfMeasurements);
    }

    public int Get_m_numberOfMeasurements()
    {
        return m_numberOfMeasurements;
    }

    /**
     * <p>Метод вызывается методом Amass при передаче значений без накопления</p>
     *
     * @param XAxis ось Х
     * @param YAxis ось Y
     * @param ZAxis ось Z
     * @param time метка временени
     */
    public void ProcessSample(double XAxis, double YAxis, double ZAxis, long time)
    {
        m_speed++;
        SetProgress(m_speed);
        if (m_speed >= m_needSpeed){
            OnTestFinished(9.3f);
        }
    }
    /**
     * <p>Устанавливает значение пргрессбара</p>
     * @param progress параметр устанавливает значние прогрессбара
     */
    public void SetProgress(int progress)
    {
        m_ownerActivity.SetProgressBar(progress);
    }
    /**
     * <p>Останавливает тестирование с выдачей результата.</p>
     *
     * @param seconds Время потраченное на разгон
     *
     */
    public void OnTestFinished(float seconds)
    {
        m_ownerActivity.OnTestFinished(seconds);
    }
}
