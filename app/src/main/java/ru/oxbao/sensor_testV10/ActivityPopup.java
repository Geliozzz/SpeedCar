package ru.oxbao.sensor_testV10;


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
    private TextView m_textViewResult;
    private Button m_btnBack;
    final String LOG_TAG = "PopupActivity";
    private boolean m_isHomeButton = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ActivityInputData.g_flagEraseData = true; // Стереть введенную информацию. Снимет этот флаг кнопка назад или системнаяя назад
        getSupportActionBar().hide();
        m_btnBack = (Button) findViewById(R.id.btnBack);

        /***********Запрет на зысыпание*****************/
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Intent intent = getIntent();
        String tmp = intent.getStringExtra("TestDataValue");
        m_textViewResult = (TextView) findViewById(R.id.tvResult);
        m_textViewResult.setText(tmp);

        m_btnBack.setOnClickListener(new View.OnClickListener()
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
