package ru.oxbao.speed_car;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityTestExecutor extends ActionBarActivity
{
    // GUI
    private RadioGroup m_radioGroupTests;
    private Button m_buttonStartTest;
    private Button m_navigateButton;
    private Spinner m_spinnerFiles;
    private ProgressWheel m_progressBarWheel;
    private ImageView m_imageViewStep2;
    private ImageView m_imageViewStep3;
    private TextView m_tvStep;
    // Service
    private boolean m_popupIsStarted = false;
    public static boolean g_startTestFlag = false;
    private boolean m_isHomeButton = true;
    final String LOG_TAG = "TestExecutorActivity";
    // Objects
    private Handler m_handler;
    private TestExecutor m_testExecutor;
    // Variables
    private TestResult m_testResult;
    private int m_need_speed;

    // Temporary
    private RadioButton m_radioTest1;

    private enum Steps
    {
        Step2,
        Step3
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_executor_test);
        Log.d(LOG_TAG, "CREATE");
        /***********Запрет на зысыпание*****************/
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getSupportActionBar().hide();
        ActivityInputData.g_flagEraseData = true; // Стереть введенную информацию. Снимет этот флаг кнопка назад или системнаяя назад
        InitGUI();
        m_testResult = new TestResult();
        m_testExecutor = new TestExecutor(this, m_testResult, ActivityInputData.g_needSpeed);

    }

    private void InitGUI()
    {
        m_buttonStartTest = (Button) findViewById(R.id.btnStartTest);
        m_navigateButton = (Button) findViewById(R.id.btnRepeatTest);
        m_progressBarWheel = (ProgressWheel) findViewById(R.id.progressBarWheel);

        m_radioGroupTests = (RadioGroup) findViewById(R.id.radGrTests);

        m_spinnerFiles = (Spinner) findViewById(R.id.spinnerFiles);
        m_radioTest1 = (RadioButton) findViewById(R.id.test1);

        m_imageViewStep2 = (ImageView) findViewById(R.id.imageViewStep2);
        m_imageViewStep3 = (ImageView) findViewById(R.id.imageViewStep3);
        m_tvStep = (TextView) findViewById(R.id.tvStepText);


        m_handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                m_progressBarWheel.setProgress(msg.getData().getInt("count"));
            }
        };

        m_buttonStartTest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                StartTest();
            }
        });

        m_navigateButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ActivityInputData.g_flagEraseData = false;
                m_isHomeButton = false;
                m_testExecutor.Stop();
                finish();
            }
        });

        m_radioGroupTests.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i)
            {
                switch (i)
                {
                    case R.id.test1:
                        m_spinnerFiles.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.test2:
                        if (m_testExecutor.GetFilesNames() != null)
                        {
                            m_spinnerFiles.setVisibility(View.VISIBLE);
                            m_spinnerFiles.setAdapter(m_testExecutor.GetFilesNames());
                        } else
                        {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.filesNotFound), Toast.LENGTH_SHORT).show();
                            m_radioTest1.setChecked(true);
                        }
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume()
    {
        Log.d(LOG_TAG, "Resume");
        SetProgressBar(0);
        SetStep(Steps.Step2);
        NavigationButtonSetEnabled(true);
        /***Ready for test*/
        if (m_popupIsStarted)
        {
            TestButtonSetEnabled(true);
            m_popupIsStarted = false;
        }
        if (g_startTestFlag)
        {
            StartTest();
            g_startTestFlag = false;
        }
        m_isHomeButton = true;
        super.onResume();
    }


    public void SetProgressBar(int progress)
    {
        m_progressBarWheel.setProgress(progress);
    }

    public void SetMaxProgressBar(int max)
    {

        m_progressBarWheel.setBarLength(max);
    }

    public void OnTestFinished(float seconds)
    {
        String stringToShow = String.valueOf(seconds);
        ShowResult(stringToShow);
    }

    public void OnCalibrateFinished()
    {
        // set progressbarmax
        SetProgressBar(0);
        SetMaxProgressBar(ActivityInputData.g_needSpeed);
        m_buttonStartTest.setText(getResources().getString(R.string.forward));
        SetStep(Steps.Step3);
    }


    public String GetCheckedSpinner()
    {
        String result = "";
        try
        {
            result = m_spinnerFiles.getSelectedItem().toString();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public void SendMessage(int count)
    {
        Bundle bundle = new Bundle();
        bundle.putInt("count", count);
        Message message = new Message();
        message.setData(bundle);
        m_handler.sendMessage(message);
    }

    // При чтении с датчика возникают события новых значений после остановки.
    // Иногда открывается несколько результатов
    //Что б не открвывалось больше одного результата введен флаг.
    private void ShowResult(String resultString)
    {
        if (!m_popupIsStarted)
        {
            Intent intent = new Intent(getApplicationContext(), ActivityPopup.class);
            intent.putExtra("TimeAcceleration", resultString);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            m_isHomeButton = false;
            m_popupIsStarted = true;
            startActivity(intent);
        }
    }

    private void TestButtonSetEnabled(boolean flag)
    {
        if (flag)
        {
            m_buttonStartTest.setBackgroundResource(R.drawable.oval_button_ready);
            m_buttonStartTest.setText(getResources().getString(R.string.startTest));
            m_buttonStartTest.setEnabled(true);
            m_buttonStartTest.setTextColor(getResources().getColor(R.color.btnExecuteTextColor));
        } else
        {
            m_buttonStartTest.setText(getResources().getString(R.string.calibrate));
            m_buttonStartTest.setBackgroundResource(R.drawable.oval_button_execute);
            m_buttonStartTest.setTextColor(getResources().getColor(R.color.btnExecuteTextColor));
            m_buttonStartTest.setEnabled(false);
        }
    }

    public void StartTest()
    {
        switch (m_radioGroupTests.getCheckedRadioButtonId())
        {
            case R.id.test1:
                m_testExecutor.Start(TestExecutor.TestEnum.test1);
                break;
            case R.id.test2:
                m_testExecutor.Start(TestExecutor.TestEnum.test2);
                break;
        }
        //  SetStep(Steps.Step2);
        NavigationButtonSetEnabled(false);
        SetMaxProgressBar(m_testExecutor.Get_m_numberOfMeasurements());
        TestButtonSetEnabled(false);
    }

    @Override
    protected void finalize() throws Throwable
    {
        Log.d(LOG_TAG, "FINALIZE");
        super.finalize();
    }

    @Override
    protected void onPause()
    {
        Log.d(LOG_TAG, "PAUSE");
        m_testExecutor.Stop();
        if (m_isHomeButton)
        {
            ActivityInputData.g_flagEraseData = true;
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        Log.d(LOG_TAG, "STOP");
        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        Log.d(LOG_TAG, "Destroy");
        super.onDestroy();
    }

    private void SetStep(Steps step)
    {
        if (step == Steps.Step2)
        {
            m_imageViewStep2.setBackgroundColor(getResources().getColor(R.color.StepActiveColor));
            m_imageViewStep3.setBackgroundColor(getResources().getColor(R.color.StepNoActiveColor));
            m_tvStep.setText(getResources().getText(R.string.step2Text));
        } else if (step == Steps.Step3)
        {
            m_imageViewStep2.setBackgroundColor(getResources().getColor(R.color.StepNoActiveColor));
            m_imageViewStep3.setBackgroundColor(getResources().getColor(R.color.StepActiveColor));
            m_tvStep.setText(getResources().getText(R.string.step3Text));
        }
    }

    private void NavigationButtonSetEnabled(boolean enable)
    {
        if (enable)
        {
            m_navigateButton.setEnabled(true);
            m_navigateButton.setBackgroundResource(R.drawable.navigate_buttons_enabled);
            m_navigateButton.setTextColor(getResources().getColor(R.color.NavigationButtonTextColor));
        } else
        {
            m_navigateButton.setEnabled(false);
            m_navigateButton.setBackgroundResource(R.drawable.navigate_buttons_disabled);
            m_navigateButton.setTextColor(getResources().getColor(R.color.NavigationButtonTextColorInactive));
        }



    }
}
