package ru.oxbao.sensor_test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputDataActivity extends Activity {
    private EditText m_editTableNameCar;
    private Button m_btnTstActivity;
    private Button m_btnPetrol;
    private Button m_btnDiesel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        m_editTableNameCar = (EditText)findViewById(R.id.edtInputName);
        m_btnTstActivity = (Button)findViewById(R.id.btnTstAct);
        m_btnDiesel = (Button)findViewById(R.id.btnDiesel);
        m_btnPetrol = (Button) findViewById(R.id.btnPetrol);

        m_btnPetrol.setBackgroundColor(getResources().getColor(R.color.InDataBtnActiveColor));
        m_btnDiesel.setBackgroundColor(getResources().getColor(R.color.InDataBtnNoActiveColor));

        m_btnTstActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TestExecutorActivity.class);
                startActivity(intent);
            }
        });

        m_btnPetrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_btnPetrol.setBackgroundColor(getResources().getColor(R.color.InDataBtnActiveColor));
                m_btnDiesel.setBackgroundColor(getResources().getColor(R.color.InDataBtnNoActiveColor));
            }
        });

        m_btnDiesel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_btnPetrol.setBackgroundColor(getResources().getColor(R.color.InDataBtnNoActiveColor));
                m_btnDiesel.setBackgroundColor(getResources().getColor(R.color.InDataBtnActiveColor));
            }
        });
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
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
