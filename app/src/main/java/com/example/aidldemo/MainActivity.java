package com.example.aidldemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.aidllib.IPhotoAidlInterface;
import com.example.aidllib.PhotoBean;
import com.example.photoservice.PhotoService;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "AIDL--TEST";

    private IPhotoAidlInterface iPhotoAidl;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "MainActivity-->onServiceConnected");
            iPhotoAidl = IPhotoAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e(TAG, "MainActivity-->onCreate");

        findViewById(R.id.btn_start_photo_service).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "MainActivity-->btn_start_photo_service");
                // 显示调用
                // note ①需要调用的module依赖服务所在的module  ②如果进程是夸应用的，则显示调用无效的。
                //Intent intent = new Intent(MainActivity.this, PhotoService.class);
                //bindService(intent, serviceConnection, BIND_AUTO_CREATE);

                // 隐式调用
                // note ①不需要调用的module不依赖服务所在的module  ②跨应用，隐式调用也可以用。
                Intent intent1 = new Intent();
                // service action name
                intent1.setAction("com.example.photoservice.PhotoService");
                // app的包名。虽然在子线程中，也是需要填写主APP的包名。
                intent1.setPackage("com.example.aidldemo");
                bindService(intent1, serviceConnection, BIND_AUTO_CREATE);
            }
        });

        findViewById(R.id.btn_get_photos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (iPhotoAidl != null) {
                        List<PhotoBean> photos = iPhotoAidl.getPhotos();
                        for (int i = 0; i < photos.size(); i++) {
                            Log.e(TAG, photos.get(i).getPath());
                        }
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_add_photos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (iPhotoAidl != null) {
                        int randomNum = new Random().nextInt(500);
                        Log.e(TAG, "MainActivity-->randomNum " + randomNum);
                        iPhotoAidl.addPhoto(new PhotoBean("add" + randomNum));
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_clear_photos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (iPhotoAidl != null) {
                        iPhotoAidl.clearPhotos();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_photo_aidl_flag).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "PhotoService-->AIDL_FLAG " + PhotoService.AIDL_FLAG);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceConnection != null) {
            unbindService(serviceConnection);
            serviceConnection = null;
        }
    }
}
