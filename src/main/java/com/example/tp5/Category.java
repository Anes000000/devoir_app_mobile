package com.example.tp5;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Category implements Parcelable {
    private String name;
    private String description;
    private ArrayList<Product> products_list;
    public Category(String name,String description,ArrayList<Product> products_list){
        this.name=name;
        this.description=description;
        this.products_list=products_list;
    }

    public ArrayList<Product> getProducts_list() {
        return products_list;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setProducts_list(ArrayList<Product> products_list) {
        this.products_list = products_list;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    protected Category(Parcel in) {
        name = in.readString();
        description = in.readString();
        products_list = in.createTypedArrayList(Product.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeTypedList(products_list);
    }
    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}
