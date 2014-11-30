package ru.oxbao.sensor_test;


import android.os.Environment;

public class Saver {

    private TestExecutor m_ownerTestExecutor;
    private StorageOutput m_storageOutput;
    private final String SAVER_TAG = "Saver";
   // private

    public Saver(TestExecutor testExecutor, String prefix) {
        m_storageOutput = new StorageOutput(testExecutor, prefix);
        m_ownerTestExecutor = testExecutor;
    }

    public void SaveData(TestData testData){
        m_storageOutput.SaveInFile(testData);
    }

    public void SetPrefix(String prefix){
        m_storageOutput.SetPrefix(prefix);
    }

}
