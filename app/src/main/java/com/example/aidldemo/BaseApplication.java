package com.example.aidldemo;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class BaseApplication extends Application {
    private static final String TAG = "AIDL--TEST";

    @Override
    public void onCreate() {
        super.onCreate();

        // 创建子进程时，避免 Application 重复创建
        String processName = getProcessName();
        Log.e(TAG, "BaseApplication-->getProcessName-->" + processName);
        if (!TextUtils.isEmpty(processName) && processName.equals(getPackageName())) {
            Log.e(TAG, "BaseApplication-->onCreate");
            Log.e(TAG, "BaseApplication-->myPid-->" + android.os.Process.myPid());
        }
    }

    public static String getProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
