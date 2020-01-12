package com.example.photoservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.aidllib.IPhotoAidlInterface;
import com.example.aidllib.PhotoBean;

import java.util.ArrayList;
import java.util.List;

public class PhotoService extends Service {
    private static final String TAG = "AIDL--TEST";

    private List<PhotoBean> photoBeans;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "PhotoService-->onCreate");

        photoBeans = new ArrayList<>();
        photoBeans.add(new PhotoBean("a"));
        photoBeans.add(new PhotoBean("b"));
        photoBeans.add(new PhotoBean("c"));
        photoBeans.add(new PhotoBean("d"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IPhotoStub();
    }

    class IPhotoStub extends IPhotoAidlInterface.Stub {

        @Override
        public List<PhotoBean> getPhotos() throws RemoteException {
            return photoBeans;
        }

        @Override
        public void addPhoto(PhotoBean photoBean) throws RemoteException {
            photoBeans.add(photoBean);
        }

        @Override
        public void clearPhotos() throws RemoteException {
            photoBeans.clear();
        }
    }
}
