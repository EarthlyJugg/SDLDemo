package com.example.sdldemo.util;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Sammie on 2017/2/28.
 */

public class ToastUtil {
    public static Toast mToast = null;

    public static void show(Context activity, String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(activity, msg, Toast.LENGTH_SHORT);

        }
        mToast.setText(msg);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }

    public static void showCenterToast(Context context, String msg) {
        if (null == context) {
            return;
        }
        try {
            if (null != msg && !msg.equals("")) {
                if (mToast == null)
                    mToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
                else {
                    mToast.setText(msg);
                    mToast.setDuration(Toast.LENGTH_SHORT);
                }
                mToast.setGravity(Gravity.CENTER, 0, 0);
                if (context instanceof Activity && !((Activity) context).isFinishing())
                    mToast.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancelToast() {
        if (mToast != null)
            mToast.cancel();
        mToast = null;
    }
}
