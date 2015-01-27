package ru.oxbao.speed_car;


import android.content.Intent;
import android.os.*;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class ActivityPopup extends ActionBarActivity
{
    //GUI
    private TextView m_tvAccelerationTimeMessage;
    private TextView m_tvaccelerationTime;
    private Button m_repeatTest;
    //Variable
    final String LOG_TAG = "PopupActivity";
    private boolean m_isHomeButton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ActivityInputData.g_flagEraseData = true; // Стереть введенную информацию. Снимет этот флаг кнопка назад или системнаяя назад
        getSupportActionBar().hide();
        /***********Запрет на зысыпание*****************/
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        InitGUI();
    }

    private void InitGUI()
    {
        m_repeatTest = (Button) findViewById(R.id.btnRepeatTest);
        Intent intent = getIntent();
        String resultValue = intent.getStringExtra("TimeAcceleration");
        m_tvaccelerationTime = (TextView) findViewById(R.id.tvaccelerationTime);
        m_tvAccelerationTimeMessage = (TextView) findViewById(R.id.tvAccelerationTimeMessage);
        String resultMessage = getResources().getString(R.string.accelerationTimeTo) + String.valueOf(ActivityInputData.g_needSpeed) + " " + getResources().getString(R.string.km_h);
        m_tvAccelerationTimeMessage.setText(resultMessage);
        m_tvaccelerationTime.setText(resultValue);


        m_repeatTest.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                ActivityTestExecutor.g_startTestFlag = true;
                ActivityInputData.g_flagEraseData = false;
                m_isHomeButton = false;
                finish();
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        Log.d(LOG_TAG, "Destroy");
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
        {
            Log.d(LOG_TAG, "Back");
            ActivityInputData.g_flagEraseData = false;
            m_isHomeButton = false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause()
    {
        if (m_isHomeButton)
        {
            ActivityInputData.g_flagEraseData = true;
            android.os.Process.killProcess(android.os.Process.myPid());
        }
        super.onPause();
    }

    @Override
    protected void onResume()
    {
        m_isHomeButton = true;
        super.onResume();
    }
}
