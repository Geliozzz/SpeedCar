package ru.oxbao.sensor_test;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TestExecutorActivity extends Activity {
    private RadioGroup m_radioGroupTests;
    private Button m_btnStartTest;
    private TextView m_textViewX;
    private TextView m_textViewY;
    private TextView m_textViewZ;
    private Spinner m_spinnerFiles;
    private LinearLayout m_linearLayoutTestExecutor;
    private TestExecutor m_testExecutor;
    private RadioButton m_radioTest1;
    private ProgressWheel m_progressBarWheel;
    final String LOG_TAG = "Logs";
    private Handler m_handler;
    private boolean m_popupIsStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executor_test);

        m_radioGroupTests = (RadioGroup) findViewById(R.id.radGrTests);
        m_btnStartTest = (Button) findViewById(R.id.btnStartTest);
        m_textViewX = (TextView) findViewById(R.id.tvX);
        m_textViewY = (TextView) findViewById(R.id.tvY);
        m_textViewZ = (TextView) findViewById(R.id.tvZ);
        m_linearLayoutTestExecutor = (LinearLayout) findViewById(R.id.testLay);
        m_spinnerFiles = (Spinner) findViewById(R.id.spinnerFiles);
        m_radioTest1 = (RadioButton) findViewById(R.id.test1);
        m_progressBarWheel = (ProgressWheel) findViewById(R.id.progressBarWheel);
        m_testExecutor = new TestExecutor(this);



        /***********Запрет на зысыпание*****************/
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        m_handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                Bundle bundle = new Bundle();

             //   m_progressBarTest.setProgress(msg.getData().getInt("count"));
                m_progressBarWheel.setProgress(msg.getData().getInt("count"));
            }
        };

  /*      m_btnViewResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
                intent.putExtra("TestDataValue", m_testExecutor.g_testData.toString());
                startActivity(intent);
            }
        });*/


        m_btnStartTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (m_radioGroupTests.getCheckedRadioButtonId()){
                    case R.id.test1:
                        m_testExecutor.Start(TestExecutor.TestEnum.test1);
                        break;
                    case R.id.test2:
                        m_testExecutor.Start(TestExecutor.TestEnum.test2);
                        break;
                }

                m_btnStartTest.setText(getResources().getString(R.string.wait));
                m_btnStartTest.setBackgroundResource(R.drawable.oval_button_execute);
                m_btnStartTest.setTextColor(getResources().getColor(R.color.btnExecuteTextColor));
                m_btnStartTest.setEnabled(false);

            }
        });

        m_radioGroupTests.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.test1:
                        m_spinnerFiles.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.test2:
                        if (m_testExecutor.GetFilesNames() != null){
                            m_spinnerFiles.setVisibility(View.VISIBLE);
                            m_spinnerFiles.setAdapter(m_testExecutor.GetFilesNames());
                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.filesNotFound), Toast.LENGTH_SHORT).show();
                            m_radioTest1.setChecked(true);
                        }
                        break;
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        /***Ready for test*/
        m_btnStartTest.setBackgroundResource(R.drawable.oval_button_ready);
        m_btnStartTest.setText(getResources().getString(R.string.startTest));
        m_btnStartTest.setEnabled(true);
        m_btnStartTest.setTextColor(getResources().getColor(R.color.btnStartReadyTextColor));
        SetProgressBar(0);
        m_popupIsStarted = false;
    }

    public void SetProgressBar(int progress) {
     //   m_progressBarTest.setProgress(progress);
        m_progressBarWheel.setProgress(progress);
    }

    public void SetMaxProgressBar(int max) {
     //   m_progressBarTest.setMax(max);
        m_progressBarWheel.setBarLength(max);
    }

    public void OnTestFinished(Solutions.ResultTestEnum resultTestEnum) {

    }

    public void SetTextViews(double x, double y, double z) {
        m_textViewX.setText(String.valueOf(x));
        m_textViewY.setText(String.valueOf(y));
        m_textViewZ.setText(String.valueOf(z));
    }

    public String GetCheckedSpinner(){
        String result = "";
        try {
           result = m_spinnerFiles.getSelectedItem().toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    public void SendMessage(int count){
        Bundle bundle = new Bundle();
        bundle.putInt("count", count);
        Message message = new Message();
        message.setData(bundle);
        m_handler.sendMessage(message);
    }
    // При чтении с датчика возникают события новых значений после остановки.
    // Иногда открывается несколько результатов
    //Что б не открвывалось больше одного результата введен флаг.
    public void ShowResult(){
        if (!m_popupIsStarted){
            Intent intent = new Intent(getApplicationContext(), PopupActivity.class);
            intent.putExtra("TestDataValue", m_testExecutor.g_testData.toString());
            startActivity(intent);
        }
    }
}
