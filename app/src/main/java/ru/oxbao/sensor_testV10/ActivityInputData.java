package ru.oxbao.sensor_testV10;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class ActivityInputData extends ActionBarActivity
{
    public enum EngineTypeEnum
    {
        Diesel,
        Petrol
    }

    public static EngineTypeEnum ToEngineTypeEnum(int i)
    {
        return EngineTypeEnum.values()[i];
    }

    // GUI
    private Button m_buttonOpenTestActivity;
    private boolean m_isHomeButton = true;
    // Service
    public static boolean g_flagEraseData = true;
    static final String LOG_TAG = "InDataActivity";

    // Variables
    private EngineTypeEnum m_engineType = EngineTypeEnum.Petrol;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        Log.d(LOG_TAG, "Create");
        DeviceHelper.GetOsVersion();


        m_buttonOpenTestActivity = (Button) findViewById(R.id.btnTstAct);

        m_buttonOpenTestActivity.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ActivityTestExecutor.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                m_isHomeButton = false;
                startActivity(intent);
            }
        });

        getSupportActionBar().hide();
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

        super.onPause();
    }

}
