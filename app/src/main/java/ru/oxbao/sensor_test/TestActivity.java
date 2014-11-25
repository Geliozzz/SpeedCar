package ru.oxbao.sensor_test;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class TestActivity extends ActionBarActivity implements SensorEventListener {

    private RadioGroup rGTests;
    private Button btnStartTest;
    private ProgressBar prbTest;
    private Button btnResult;
    private TextView tvX;
    private TextView tvY;
    private TextView tvZ;
    private LinearLayout linearLayout;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private static final int count_dem = 2000; // Кол- во измерений
    private int counter;
    private boolean isStart = false;
    private static TestData testData = new TestData(count_dem);
    public static enum resultTest{ // Перечисление с результататом теста
        GOOD, BAD
    }

    final String LOG_TAG = "Logs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        rGTests = (RadioGroup)findViewById(R.id.radGrTests);
        btnStartTest = (Button)findViewById(R.id.btnStartTest);
        prbTest = (ProgressBar)findViewById(R.id.prBrTest);
        btnResult = (Button)findViewById(R.id.btnViewResult);
        tvX = (TextView)findViewById(R.id.tvX);
        tvY = (TextView)findViewById(R.id.tvY);
        tvZ = (TextView)findViewById(R.id.tvZ);
        linearLayout = (LinearLayout)findViewById(R.id.testLay);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        prbTest.setMax(count_dem);


        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        if (accelerometer != null){

        }
        else {
            Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.failAccelerometer), Toast.LENGTH_SHORT);
            toast.show();
        }

        btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isStart = true;

            }
        });

        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("str", testData.toString());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (isStart == true){
            tvX.setText(String.valueOf(sensorEvent.values[0]));
            tvY.setText(String.valueOf(sensorEvent.values[1]));
            tvZ.setText(String.valueOf(sensorEvent.values[2]));
            testData.setAxisX(sensorEvent.values[0], counter);
            testData.setAxisY(sensorEvent.values[1], counter);
            testData.setAxisZ(sensorEvent.values[2], counter);
            testData.setTime(System.currentTimeMillis(), counter);
            prbTest.setProgress(counter);
            counter++;
            if (counter >= count_dem){
                isStart = false;
                counter = 0;
                if (testData.analyse().equals(resultTest.GOOD)){
                    linearLayout.setBackgroundColor(getResources().getColor(R.color.green));
                    Toast toast = Toast.makeText(getApplicationContext(), getResources().getString(R.string.engIsNorm), Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
        setProgress(counter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter", counter);
        outState.putBoolean("isStart", isStart);

        Log.d(LOG_TAG, "Store counter, isStart");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        counter = savedInstanceState.getInt("counter");
        isStart = savedInstanceState.getBoolean("isStart");
        Log.d(LOG_TAG, "ReStore counter, isStart");
    }
}
