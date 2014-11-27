package ru.oxbao.sensor_test;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PopupActivity extends ActionBarActivity {
    private TextView m_textViewResult;
    private Button m_btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        m_btnBack = (Button)findViewById(R.id.btnBack);

        Intent intent = getIntent();
        String tmp = intent.getStringExtra("str");
        m_textViewResult = (TextView)findViewById(R.id.tvResult);
        m_textViewResult.setText(tmp);

        m_btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
