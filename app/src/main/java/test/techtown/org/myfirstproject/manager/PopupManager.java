package test.techtown.org.myfirstproject.manager;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;
import android.os.Handler;

public class PopupManager {
    public static final String TAG = PopupManager.class.getSimpleName();

    private volatile static PopupManager sInstance;

    private Context mContext = null;
    private Handler mHandler = null;
    private Toast mToast = null;

    public static PopupManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (PopupManager.class) {
                if (sInstance == null) {
                    sInstance = new PopupManager(context);
                }
            }
        }
        return sInstance;
    }

    private PopupManager(Context context) {
        mContext = context;
        mHandler = new Handler(Looper.getMainLooper());
    }

    private void _showToast(String msg, int duration) {
        try {
            if (mToast != null) {
                mToast.cancel();
                mToast = null;
            }

            mToast = Toast.makeText(mContext.getApplicationContext(), msg, duration);
            mToast.show();

            Log.d(TAG, "showToast() - " + msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return;
    }

    public void showToast(final String msg, final int duration) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                _showToast(msg, duration);
            }
        });
    }

    public void showToast(final String msg) {
        showToast(msg, Toast.LENGTH_SHORT);
    }

}
