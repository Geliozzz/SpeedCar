package ru.oxbao.sensor_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultActivity extends ActionBarActivity {
    private TextView tvResult;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        btnBack = (Button)findViewById(R.id.btnBack);

        Intent intent = getIntent();
        String tmp = intent.getStringExtra("str");
        tvResult = (TextView)findViewById(R.id.tvResult);
        tvResult.setText(tmp);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
