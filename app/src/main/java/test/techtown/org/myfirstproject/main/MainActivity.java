package test.techtown.org.myfirstproject.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;

import test.techtown.org.myfirstproject.R;
import test.techtown.org.myfirstproject.auth.ChooseAuthActivity;
import test.techtown.org.myfirstproject.manager.PopupManager;

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
                AccessToken accessToken = AccountKit.getCurrentAccessToken();
                Log.d(TAG,"accessToken : " + accessToken);

                if (accessToken != null) {
                    //Handle Returning User
                    Log.d(TAG, "test1");

                } else {
                    Log.d(TAG, "test2");
                    PopupManager.getInstance(mContext).showToast("test2");

                    Intent intent = new Intent(mContext, ChooseAuthActivity.class);
                    startActivity(intent);
                }
            break;
        }
    }
}
