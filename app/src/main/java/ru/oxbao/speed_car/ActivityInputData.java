package ru.oxbao.speed_car;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.*;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class ActivityInputData extends ActionBarActivity
{
    // GUI
    private Button m_buttonOpenTestActivity;
    private boolean m_isHomeButton = true;
    private Button m_btnSpeedUp;
    private Button m_btnSpeedDown;
    private EditText m_edtNeedSpeed;
    // Service
    public static boolean g_flagEraseData = true;
    static final String LOG_TAG = "InDataActivity";
    //Objects
    private Handler m_handler;
    private SpeedHelper m_speedHelper;

    // Variables
    public static int g_needSpeed = 100;
    private DirectionSpeed directionSpeed = DirectionSpeed.Stop;

    private enum DirectionSpeed
    {
        Up,
        Down,
        Stop
    }


    private class SpeedHelper extends Thread
    {
        @Override
        public void run()
        {
            while (!this.isInterrupted())
            {
                switch (directionSpeed)
                {
                    case Up:
                        g_needSpeed++;
                        break;
                    case Down:
                        g_needSpeed--;
                        break;
                    default:
                        break;
                }
                try
                {
                    sleep(250);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                m_handler.sendEmptyMessage(1);
            }
        }
    }

    //
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        Log.d(LOG_TAG, "Create");
        DeviceHelper.GetOsVersion();


        m_buttonOpenTestActivity = (Button) findViewById(R.id.btnTstAct);
        m_btnSpeedUp = (Button) findViewById(R.id.btnNeedSpeedUp);
        m_btnSpeedDown = (Button) findViewById(R.id.btnNeedSpeedDown);
        m_edtNeedSpeed = (EditText) findViewById(R.id.edtNeedSpeedEnter);
        m_edtNeedSpeed.setText(String.valueOf(g_needSpeed));

        m_buttonOpenTestActivity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ActivityTestExecutor.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                intent.putExtra("needSpeed", g_needSpeed);
                m_isHomeButton = false;
                m_speedHelper.interrupt();
                startActivity(intent);
            }
        });


        m_btnSpeedUp.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    directionSpeed = DirectionSpeed.Up;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    directionSpeed = DirectionSpeed.Stop;
                }
                return true;
            }
        });

        m_btnSpeedDown.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent)
            {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
                {
                    directionSpeed = DirectionSpeed.Down;
                }
                if (motionEvent.getAction() == MotionEvent.ACTION_UP)
                {
                    directionSpeed = DirectionSpeed.Stop;
                }
                return true;
            }
        });



        getSupportActionBar().hide();

        m_handler = new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                m_edtNeedSpeed.setText(String.valueOf(g_needSpeed));
            }

        };
        m_speedHelper = new SpeedHelper();

        m_speedHelper.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.in_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
   /*     int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume()
    {
        Log.d(LOG_TAG, "RESUME");
        m_isHomeButton = true;
        super.onResume();
    }

    @Override
    protected void onDestroy()
    {
        Log.d(LOG_TAG, "Destroy");
        super.onDestroy();
    }

    @Override
    protected void onPause()
    {
        g_flagEraseData = true;
        if (m_isHomeButton)
        {
            Log.d(LOG_TAG, "killProcess");
            android.os.Process.killProcess(Process.myPid());
        }
        m_speedHelper.interrupt();
        super.onPause();
    }

}
