package test.techtown.org.myfirstproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext;

    private Button btSplashContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        initComponent();
    }


    private void initComponent() {
        Log.d(TAG, "initComponent()");
        btSplashContinue = (Button) findViewById(R.id.bt_splash_continue);
        btSplashContinue.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_splash_continue :
            Intent intent = new Intent(mContext, authSmsActivity.class);
            startActivity(intent);
            break;
        }
    }
}
