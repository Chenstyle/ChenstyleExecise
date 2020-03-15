package com.example.okhttplearn.serial;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @auther to Chenstyle
 * @time 2020-3-15 15:48
 */
class Book implements Parcelable {

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    protected Book(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }
}
