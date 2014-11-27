package ru.oxbao.sensor_test;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

public class TestExecutorActivity extends ActionBarActivity {
    private RadioGroup m_radioGroupTests;
    private Button m_btnStartTest;
    private ProgressBar m_progressBarTest;
    private Button m_btnViewResult;
    private TextView m_textViewX;
    private TextView m_textViewY;
    private TextView m_textViewZ;
    private LinearLayout m_linearLayoutTestExecutor;
    private TestExecutor m_testExecutor;
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

        m_testExecutor = new TestExecutor(this);

        /***********Запрет на зысыпание*****************/
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        m_btnViewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                startActivity(intent);
            }
        });

        m_btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_testExecutor.Start();
            }
        });

    }

    public void SetProgressBar(int progress){
        m_progressBarTest.setProgress(progress);
    }

    public void OnTestFinished(Solutions.ResultTestEnum resultTestEnum){

    }
}
