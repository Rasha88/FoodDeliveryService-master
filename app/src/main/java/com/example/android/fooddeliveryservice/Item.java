package com.example.android.fooddeliveryservice;

import java.io.Serializable;

/**
 * Created by rashali on 14/03/2018.
 */

public class Item implements Serializable{
    private String mName;
    private int mImageId;
    private double mPrice;
    private int mQuantity ;
    private boolean isChecked ;

    public Item(String name,double price,int imageId){
        mName = name;
        mPrice = price;
        mImageId = imageId;
        mQuantity = 0;
        isChecked = false;
    }

    public String getName() {
        return mName;
    }

    public double getPrice() {
        return mPrice;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int op_flag) {
        // op_flag = 1 => add operating , op_flag = 0 => subtraction
        if (op_flag == 1){
            this.mQuantity = getQuantity() + 1;
        }else {
            this.mQuantity = getQuantity() - 1;
        }

    }

    public int getImageId() {
        return mImageId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
