package com.example.aidllib;

parcelable PhotoBean;
interface IPhotoAidlInterface {
    List<PhotoBean> getPhotos();
    void addPhoto(inout PhotoBean photoBean);
    void clearPhotos();
}
