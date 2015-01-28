package ru.oxbao.speed_car;


import android.content.Context;
import android.hardware.SensorManager;

public class InputAdapter
{
    private SensorInput m_sensorInput;
    private StorageInput m_storageInput;
    private TestExecutor m_ownerTestExecutor;
    private InputOwnerInterface m_inputOwnerInterface;
 //   private ActivityTestExecutor m_ownerActivity;
    private SensorManager m_sensorManager;



    public enum InputTypeEnum
    {
        sensors,
        storage
    }

    public InputAdapter(ActivityTestExecutor activityTestExecutor,
                        TestExecutor testExecutor, InputOwnerInterface inputOwnerInterface)
    {
        m_sensorManager = (SensorManager) activityTestExecutor.getSystemService(Context.SENSOR_SERVICE);
        m_sensorInput = new SensorInput(inputOwnerInterface, m_sensorManager);
        m_storageInput = new StorageInput(activityTestExecutor, testExecutor, inputOwnerInterface);
        m_ownerTestExecutor = testExecutor;
        m_inputOwnerInterface = inputOwnerInterface;
    }


    public void Start(InputTypeEnum inputTypeEnum)
    {
        if (inputTypeEnum.equals(InputTypeEnum.sensors))
        {
            // Переопределить Тест дата для фиксированного количества измерений если до этого была работа из файла
            m_ownerTestExecutor.SetFixedTestData();
            m_inputOwnerInterface.SetNumberOfMeasurements(m_ownerTestExecutor.Getm_numberOfMeasurements()); // Возврат к фиксированному значению
            m_sensorInput.Start();
        } else if (inputTypeEnum.equals(InputTypeEnum.storage))
        {
            //Так как неизвестно количество данных в файлах
            // необходимо объявить заново ТестДата. Иначе возможна потеря информации
            int count = m_storageInput.GetNumberLines(); //!!!!!!!!!!!!!!!!!!!!!!!!! проверка на ноль
            m_ownerTestExecutor.g_testData = new TestData(count);
            // Кол-во измерений в коллектрое тоже должно измениться
            m_inputOwnerInterface.SetNumberOfMeasurements(count);
            m_storageInput.Start();
        }
    }

    public void Stop()
    {
        m_sensorInput.Stop();
    }

    public String[] GetFilesNames()
    {
        return m_storageInput.GetFilesFromFolder();
    }



}
