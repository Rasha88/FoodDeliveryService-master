package com.example.android.fooddeliveryservice;

import android.widget.TextView;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by rashali on 15/03/2018.
 */

public class OrderData implements Serializable {
    public ArrayList<Item> selectedItems = new ArrayList<Item>();
    public double totalPrice ;
    public TextView totalPriceTextView ;
    public TextView totalItemsTextView ;


    //calculate total price
    public double calculateTotalPrice(ArrayList<Item> selectedItems){
        double totalPrice = 0.0;
        int i =0;
        while (i<selectedItems.size()){
            totalPrice += (selectedItems.get(i).getPrice() * selectedItems.get(i).getQuantity());
            i++;
        }
        return totalPrice;
    }

    public void setOrderDetails(ArrayList<Item> selectedItems){

        // set this text on the total_price TextView
        totalPriceTextView.setText("Total: "+ NumberFormat.getCurrencyInstance().format(calculateTotalPrice(selectedItems)));

        // set this text on the total_items TextView
        totalItemsTextView.setText("Items: "+ String.valueOf(selectedItems.size()));

    }
}
