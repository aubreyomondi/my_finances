package com.ajomondi.myfinances;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SmsDetailActivity extends AppCompatActivity {
    TextView smsBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_detail);

        smsBody = (TextView) findViewById(R.id.smsBody);
        Intent intent = getIntent();
        Sms sms = (Sms) intent.getSerializableExtra("Sms");
        if (sms == null) {
            Log.d("Sms status", "sms is null");
        } else {
            sms = sms;
            smsBody.setText(sms.getBody());
        }
    }
}
