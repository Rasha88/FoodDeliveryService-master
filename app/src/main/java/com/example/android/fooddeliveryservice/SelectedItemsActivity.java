package com.example.android.fooddeliveryservice;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.id.list;

public class SelectedItemsActivity extends AppCompatActivity {
    private ItemAdapter adapter;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_items);

        Intent intent = getIntent();
        OrderData obj = (OrderData) intent.getSerializableExtra("orderDataObj");
        ArrayList<Item> selectedItems = new ArrayList<Item>();
        selectedItems = obj.selectedItems;

        String msg = "";
        for (int i= 0;i<selectedItems.size();i++){
            msg+= (selectedItems.get(i)).getName() +" \n ";
        }

        // Get the total price from the orderDataObj object
        TextView totalPriceTextView = (TextView) findViewById(R.id.total_price);
        obj.totalPriceTextView = totalPriceTextView;

        // Get the total items from the size of mListItems array and
        TextView totalItemsTextView = (TextView) findViewById(R.id.total_items);
        obj.totalItemsTextView = totalItemsTextView;
        //set total price
        obj.setOrderDetails(selectedItems);


        adapter = new ItemAdapter(this,obj,R.layout.simple_selected_item);
        list = (ListView) findViewById(R.id.list_selected_items);
        list.setAdapter(adapter);


    }


}
