package com.example.gzhang.shopifyplease;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by GZhang on 2018-01-04.
 */

public class Product implements Parcelable {

    Bitmap image;
    String title;
    String description;
    String vendor;
    String productType;

    public Product(Bitmap image, String title, String description, String vendor, String productType){
        this.image = image;
        this.title = title;
        this.description = description;
        this.vendor = vendor;
        this.productType = productType;
    }

    protected Product(Parcel in) {
        image = in.readParcelable(Bitmap.class.getClassLoader());
        title = in.readString();
        description = in.readString();
        vendor = in.readString();
        productType = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        //write image
/*
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();

        parcel.writeByteArray(byteArray);*/


        parcel.writeParcelable(image, i);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(vendor);
        parcel.writeString(productType);

    }
}
