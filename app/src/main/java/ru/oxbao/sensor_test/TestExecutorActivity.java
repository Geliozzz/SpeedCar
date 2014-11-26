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
                Collector(InputTypeEnum.sensors);

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

    public void Collector(InputTypeEnum inputTypeEnum) {
        if (inputTypeEnum.equals(InputTypeEnum.sensors)) {
            m_sensors.Start();
            m_counter = 0;
        } else if (inputTypeEnum.equals(InputTypeEnum.storage)) {

        }

    }

    public void Collector(float XAxis, float YAxis, float ZAxis, long TimeInMillisecond) {
        if (m_counter <= m_count_dem) {
            try {
                m_testData.XAxis[m_counter] = (double) XAxis;
                m_testData.YAxis[m_counter] = (double) YAxis;
                m_testData.ZAxis[m_counter] = (double) ZAxis;
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
            Analyze(m_testData);
            if (m_needSave) {
                Saver(m_testData);
            }

            if (Analyze(m_testData).equals(ResultTestEnum.GOOD)) {
                m_linearLayoutTestExecutor.setBackgroundColor(getResources().getColor(R.color.green));
                Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.engIsNorm), Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    private ResultTestEnum Analyze(TestData testData) {
        return ResultTestEnum.GOOD;
    }

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


    @Override
    protected void finalize() throws Throwable {
        m_sensors.Stop();
        super.finalize();
    }


}
