package ru.oxbao.sensor_test;


import android.content.Intent;
import android.hardware.Sensor;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestExecutorActivity extends ActionBarActivity {
    private RadioGroup m_radioGroupTests;
    private Button m_btnStartTest;
    private ProgressBar m_progressBarTest;
    private Button m_btnViewResult;
    private TextView m_textViewX;
    private TextView m_textViewY;
    private TextView m_textViewZ;
    private LinearLayout m_linearLayoutTestExecutor;
    private Sensors m_sensors;
    private static final int m_count_dem = 500; // Кол- во измерений
    private int m_counter;
    private static TestData m_testData = new TestData(m_count_dem);
    private boolean m_needSave = true;

    final String LOG_TAG = "Logs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executor_test);

        m_radioGroupTests = (RadioGroup) findViewById(R.id.radGrTests);
        m_btnStartTest = (Button) findViewById(R.id.btnStartTest);
        m_progressBarTest = (ProgressBar) findViewById(R.id.prBrTest);
        m_btnViewResult = (Button) findViewById(R.id.btnViewResult);
        m_textViewX = (TextView) findViewById(R.id.tvX);
        m_textViewY = (TextView) findViewById(R.id.tvY);
        m_textViewZ = (TextView) findViewById(R.id.tvZ);
        m_linearLayoutTestExecutor = (LinearLayout) findViewById(R.id.testLay);
        m_progressBarTest.setMax(m_count_dem);

        /***********Запрет на зысыпание*****************/
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        List<Integer> requiredSensors = new ArrayList<Integer>();
        requiredSensors.add(Sensor.TYPE_ACCELEROMETER);
        m_sensors = new Sensors(this, this, requiredSensors);

        m_btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_needSave = true;
               // Collector(InputTypeEnum.storage);

            }
        });

        m_btnViewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("str", m_testData.toString());
                startActivity(intent);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

/*    public void Collector(InputTypeEnum inputTypeEnum) {
        if (inputTypeEnum.equals(InputTypeEnum.sensors)) {
            m_sensors.Start();
            m_counter = 0;
        } else if (inputTypeEnum.equals(InputTypeEnum.storage)) {
            Reader("acc_2014_11_26_14_1_43.txt");
            m_needSave = false;
        }

    }*/

    public void Collector(double XAxis, double YAxis, double ZAxis, long TimeInMillisecond) {
        if (m_counter <= m_count_dem) {
            try {
                m_testData.XAxis[m_counter] = XAxis;
                m_testData.YAxis[m_counter] = YAxis;
                m_testData.ZAxis[m_counter] = ZAxis;
                m_testData.TimeInMilliseconds[m_counter] = TimeInMillisecond;
            } catch (IndexOutOfBoundsException e) {
                Log.d(LOG_TAG, "Invalid index in TestDataClass");
            } catch (Exception e) {
                e.printStackTrace();
            }
            m_counter++;
            m_progressBarTest.setProgress(m_counter);
            m_textViewX.setText(String.valueOf(XAxis));
            m_textViewY.setText(String.valueOf(YAxis));
            m_textViewZ.setText(String.valueOf(ZAxis));
        } else {
            m_sensors.Stop();
            m_counter = 0;
           // Analyze(m_testData);
            if (m_needSave) {
                Saver(m_testData);
            }

   /*         if (Analyze(m_testData).equals(ResultTestEnum.GOOD)) {
                m_linearLayoutTestExecutor.setBackgroundColor(getResources().getColor(R.color.green));
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.engIsNorm), Toast.LENGTH_SHORT);
                toast.show();
            }*/
        }
    }

/*  //  private ResultTestEnum Analyze(TestData testData) {
        return ResultTestEnum.GOOD;
    }*/

    private void Saver(TestData testData) {
        String path = Environment.getExternalStorageDirectory().toString();
        if (StorageWriter.SaveToFileTestData(testData, path, "acc")) {
            Log.d(LOG_TAG, "Save Ok");
        } else {
            Log.d(LOG_TAG, "Can't save file");
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.canNotSave), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void Reader(String fileName){
        String path = Environment.getExternalStorageDirectory().toString();
        File file = new File(path, fileName);
        try {
            BufferedReader bufferedFileReader = new BufferedReader(new FileReader(file));
            while (bufferedFileReader.ready()){
                String[] line =  bufferedFileReader.readLine().split(" ");
                try {
                    double axisX = Double.parseDouble(line[0]);
                    double axisY = Double.parseDouble(line[1]);
                    double axisZ = Double.parseDouble(line[2]);
                    long time = Long.parseLong(line[3]);
                    Collector(axisX, axisY, axisZ, time);
                } catch (IndexOutOfBoundsException e){
                    Log.d(LOG_TAG, "Index is out of range");
                } catch (NumberFormatException e){

                } catch (Exception e){
                    e.printStackTrace();
                }

            }
        } catch (FileNotFoundException e){
            Log.d(LOG_TAG, "File not found");
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.fileNotFound), Toast.LENGTH_SHORT);
            toast.show();
        } catch (IOException e){
            Log.d(LOG_TAG, "Can't read line");
        } catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    protected void finalize() throws Throwable {
        m_sensors.Stop();
        super.finalize();
    }


}
