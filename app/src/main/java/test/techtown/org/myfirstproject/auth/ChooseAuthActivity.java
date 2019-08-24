package test.techtown.org.myfirstproject.auth;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import test.techtown.org.myfirstproject.R;
import test.techtown.org.myfirstproject.main.MainHomeActivity;
import test.techtown.org.myfirstproject.manager.PopupManager;

import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.accountkit.AccountKitLoginResult;

public class ChooseAuthActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = ChooseAuthActivity.class.getSimpleName();

    public static int APP_REQUEST_CODE_SMS = 1000;
    public static int APP_REQUEST_CODE_EMAIL = 1001;


    private Context mContext = null;

    private Button btAuthSms;
    private Button btAuthEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_auth);

        mContext = this;

        initComponent();
    }

    private void initComponent() {
        Log.d(TAG, "initComponent()");
        btAuthSms = (Button) findViewById(R.id.bt_auth_sms);
        btAuthEmail = (Button) findViewById(R.id.bt_auth_email);

        btAuthSms.setOnClickListener(this);
        btAuthEmail.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_auth_sms:
                Log.d(TAG, "onClick() - bt_auth_sms");

                Intent intentSms = new Intent(mContext, AccountKitActivity.class);
                AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilderSms =
                        new AccountKitConfiguration.AccountKitConfigurationBuilder(
                                LoginType.PHONE,
                                AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
                // ... perform additional configuration ...
                intentSms.putExtra(
                        AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                        configurationBuilderSms.build());
                startActivityForResult(intentSms, APP_REQUEST_CODE_SMS);

                break;
            case R.id.bt_auth_email:
                Log.d(TAG, "onClick() - bt_auth_email");

                Intent intentEmail = new Intent(mContext, AccountKitActivity.class);
                AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilderEmail =
                        new AccountKitConfiguration.AccountKitConfigurationBuilder(
                                LoginType.EMAIL,
                                AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
                // ... perform additional configuration ...
                intentEmail.putExtra(
                        AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                        configurationBuilderEmail.build());
                startActivityForResult(intentEmail, APP_REQUEST_CODE_EMAIL);

                break;
        }
    }


    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == APP_REQUEST_CODE_SMS) {
            // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;

            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
//                showErrorActivity(loginResult.getError());
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0,10));
                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
                goToMainHomeActivity();
            }

            // Surface the result to your user in an appropriate way.
            PopupManager.getInstance(mContext).showToast(toastMessage);
        }
    }

    private void goToMainHomeActivity() {
        Intent intent = new Intent(mContext, MainHomeActivity.class);
        startActivity(intent);
    }
}
