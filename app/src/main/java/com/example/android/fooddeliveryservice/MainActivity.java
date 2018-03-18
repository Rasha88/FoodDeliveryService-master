package com.example.android.fooddeliveryservice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.value;

public class MainActivity extends AppCompatActivity  {

    GridView grid;
    ArrayList<Item> food;
    public ArrayList<Item> selectedItems = new ArrayList<Item>();
    Context context;
    //String msg ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_grid);

        context = this;
        food = new ArrayList<Item>();

        food.add(new Item("Pizza",0.7,R.drawable.food_pizza ));
        food.add(new Item("Burger",0.4,R.drawable.food_burger  ));
        food.add(new Item("Meat",0.6,R.drawable.food_2));
        food.add(new Item("Shish kebab",0.5,R.drawable.food_shish_kebab ));
        food.add(new Item("Pepsi",0.3,R.drawable.drink_pepsi));
        food.add(new Item("Coca Cola",0.3,R.drawable.drink_cola ));
        food.add(new Item("Strawberry" ,0.2,R.drawable.drink_3));
        food.add(new Item("Cup cake" ,0.2,R.drawable.cup_cake));
        food.add(new Item("Cherry swiss roll" ,0.2,R.drawable.cherry_swiss_roll));
        food.add(new Item("Donat" ,0.2,R.drawable.sweet_1));

        Intent intent = getIntent();
        OrderData extra_orderDataObj = (OrderData) intent.getSerializableExtra("orderDataObj");
        if(extra_orderDataObj !=null) {
            for (int i = 0; i< extra_orderDataObj.selectedItems.size();i++){
                food.add(extra_orderDataObj.selectedItems.get(i));
            }
        }

        ItemAdapter adapter = new ItemAdapter(this,food,R.layout.simple_item);
        grid = (GridView) findViewById(R.id.gridview);
        grid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE);
        grid.setAdapter(adapter);


        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                //Item selectedItem = food.get(position);
                Item selectedItem = (Item) parent.getItemAtPosition(position);

                //set the value of checkbox
                CheckedTextView check_text_view = (CheckedTextView)(v.findViewById(R.id.is_chk));
                if (!check_text_view.isChecked()){
                    //add the selected item to selectedItems array
                    selectedItems.add(selectedItem);

                    //set the value of checkbox
                    check_text_view.setChecked(true);
                    selectedItem.setChecked(true);

                    v.setBackgroundResource(R.drawable.selected_item);

                    //update the quantity for selected item to be 1
                    selectedItem.setQuantity(1);
                }else {
                    //remove the selected item to selectedItems array
                    selectedItems.remove(selectedItem);

                    check_text_view.setChecked(false);
                    selectedItem.setChecked(false);

                    v.setBackgroundResource(R.drawable.item_grid);

                    //update the quantity for selected item to be 0
                    selectedItem.setQuantity(0);
                }

                /*String msg ="";
                for (int i= 0;i<selectedItems.size();i++){
                    msg+= (selectedItems.get(i)).getName() +" \n ";
                }
                ((TextView)findViewById(R.id.res)).setText(msg);
               // Toast.makeText(MainActivity.this,msg, Toast.LENGTH_LONG).show();*/

            }
        });

    }

    public void showSelectedItems(View v){
        OrderData orderDataObj = new OrderData();
        orderDataObj.selectedItems =  selectedItems ;

        Intent i = new Intent(this,SelectedItemsActivity.class).putExtra("orderDataObj",orderDataObj);
        startActivity(i);

    }
}
