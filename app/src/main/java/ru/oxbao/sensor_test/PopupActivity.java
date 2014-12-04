package ru.oxbao.sensor_test;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PopupActivity extends Activity {
    private TextView m_textViewResult;
    private Button m_btnBack;
    final String LOG_TAG = "PopupActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        m_btnBack = (Button)findViewById(R.id.btnBack);

        Intent intent = getIntent();
        String tmp = intent.getStringExtra("TestDataValue");
        m_textViewResult = (TextView)findViewById(R.id.tvResult);
        m_textViewResult.setText(tmp);

        m_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestExecutorActivity.g_startTestFlag = true;
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG, "Destroy");
        super.onDestroy();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_HOME){
            Log.d(LOG_TAG, "EXIT");
            moveTaskToBack(true);
            System.exit(0);
            System.gc();
        }

        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD);
    }
}
