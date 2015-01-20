package ru.oxbao.sensor_test;

import android.app.Activity;
import android.content.Intent;
import android.os.*;
import android.os.Process;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class InputDataActivity extends ActionBarActivity
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
    private Button m_buttonDiesel, m_buttonPetrol;
    private EditText m_editCarName;
    private boolean m_isHomeButton = true;
    // Service
    public static boolean g_flagEraseData = true;
    static final String LOG_TAG = "InDataActivity";

    // Variables
    private EngineTypeEnum m_enginveType = EngineTypeEnum.Petrol;

    //
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);

        Log.d(LOG_TAG, "Create");
        DeviceHelper.GetOsVersion();

        m_editCarName = (EditText) findViewById(R.id.edtInputName);
        m_buttonOpenTestActivity = (Button) findViewById(R.id.btnTstAct);
        m_buttonDiesel = (Button) findViewById(R.id.btnDiesel);
        m_buttonPetrol = (Button) findViewById(R.id.btnPetrol);

        m_editCarName.setText("");
        m_editCarName.setHint(getResources().getString(R.string.carHint));
        m_editCarName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus) {
                    HideKeyboard(view);
                } else m_editCarName.selectAll();
            }
        });

        m_buttonOpenTestActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TestExecutorActivity.class);
                intent.putExtra("CarName", m_editCarName.getText().toString());
                intent.putExtra("CarEngineType", m_enginveType.ordinal());
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                m_isHomeButton = false;
                startActivity(intent);
            }
        });

        m_buttonDiesel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_enginveType = EngineTypeEnum.Diesel;
                SetButtons(false);
                HideKeyboard(view);
            }
        });

        m_buttonPetrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_enginveType = EngineTypeEnum.Petrol;
                SetButtons(true);
                HideKeyboard(view);
            }
        });

        SetButtons(true);
        getSupportActionBar().hide();
    }

    private void SetButtons(boolean flag)
    {
        if (DeviceHelper.g_moreGingerBread)
        {
            if (flag) {
                m_buttonPetrol.setBackgroundResource(R.drawable.draw_fuel_icecream_left_active);
                m_buttonPetrol.setTextColor(getResources().getColor(R.color.black));
                m_buttonDiesel.setBackgroundResource(R.drawable.draw_fuel_icecream_right_noactive);
                m_buttonDiesel.setTextColor(getResources().getColor(R.color.white));
            } else {
                m_buttonPetrol.setBackgroundResource(R.drawable.draw_fuel_icecream_left_noactive);
                m_buttonPetrol.setTextColor(getResources().getColor(R.color.white));
                m_buttonDiesel.setBackgroundResource(R.drawable.draw_fuel_icecream_right_active);
                m_buttonDiesel.setTextColor(getResources().getColor(R.color.black));
            }
        } else {
            if (flag) {
                m_buttonPetrol.setBackgroundResource(R.drawable.draw_left_btn_fuel_active);
                m_buttonPetrol.setTextColor(getResources().getColor(R.color.black));
                m_buttonDiesel.setBackgroundResource(R.drawable.draw_right_btn_fuel_noactive);
                m_buttonDiesel.setTextColor(getResources().getColor(R.color.white));
            } else {
                m_buttonPetrol.setBackgroundResource(R.drawable.draw_left_btn_fuel_noactive);
                m_buttonPetrol.setTextColor(getResources().getColor(R.color.white));
                m_buttonDiesel.setBackgroundResource(R.drawable.draw_right_btn_fuel_active);
                m_buttonDiesel.setTextColor(getResources().getColor(R.color.black));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.in_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
   /*     int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    public void HideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    protected void onResume() {
        Log.d(LOG_TAG, "RESUME");
        if (g_flagEraseData) {
            m_editCarName.setText("");
            SetButtons(true);
            g_flagEraseData = false;
        }
        m_isHomeButton = true;
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "Destroy");
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        g_flagEraseData = true;
        if (m_isHomeButton){
            Log.d(LOG_TAG, "killProcess");
            android.os.Process.killProcess(Process.myPid());
        }

        super.onPause();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK){
            Log.d(LOG_TAG, "Back");
            m_isHomeButton = false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
