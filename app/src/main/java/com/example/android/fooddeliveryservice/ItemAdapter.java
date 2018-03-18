package com.example.android.fooddeliveryservice;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by rashali on 15/03/2018.
 */

public class ItemAdapter extends ArrayAdapter<Item> {
    private int mItemLayoutResourceId;
    private Activity mContxt;
    private ArrayList<Item> mListItems;
    ArrayAdapter<Item> adp = this;
    OrderData orderData ;

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context The current context. Used to inflate the layout file.
     * @param orderDataDetails   OrderData objects to access details of order
     */
    public ItemAdapter(Activity context, OrderData orderDataDetails, int itemLayoutResourceId) {
        super(context, 0, orderDataDetails.selectedItems);
        mItemLayoutResourceId = itemLayoutResourceId;
        mContxt = context;
        mListItems = orderDataDetails.selectedItems;
        orderData = orderDataDetails;
    }

    public ItemAdapter(Activity context, ArrayList<Item> items, int itemLayoutResourceId) {
        super(context, 0, items);
        mItemLayoutResourceId = itemLayoutResourceId;
        mContxt = context;
        mListItems = items;
    }


    public static class ViewHolder {
        public TextView item_name;
        public TextView item_price;
        public TextView quantityTextView;
        public CheckedTextView item_isCheck;
        public ImageView iconView;
        public Button decrementButton;
        public Button incrementButton;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view(single item in Grid / List)
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(getContext()).inflate(
                    mItemLayoutResourceId, parent, false);
        }

        // Get the {@link Item} object located at this position in the list
        final Item currentItem = getItem(position);

        final ViewHolder viewHolder = new ViewHolder();

        // Find the TextView in the simple_item.xml layout with the ID item_name_text_view
        viewHolder.item_name = (TextView) itemView.findViewById(R.id.item_name_text_view);
        // Get the Item name from the current Item object and
        // set this text on the item_name_text_view TextView
        viewHolder.item_name.setText(currentItem.getName());

        // Find the TextView in the simple_item.xml layout with the ID item_price_text_view
        viewHolder.item_price = (TextView) itemView.findViewById(R.id.item_price_text_view);
        // Get the item price  from the current Item object and
        // set this text on the item_price_text_view TextView
        viewHolder.item_price.setText(NumberFormat.getCurrencyInstance().format(currentItem.getPrice()));

        // Find the ImageView in the simple_item.xml layout with the ID item_image
        viewHolder.iconView = (ImageView) itemView.findViewById(R.id.item_image);
        // Get the image resource ID from the current Item object and
        // set the image to iconView
        viewHolder.iconView.setImageResource(currentItem.getImageId());

        if (mItemLayoutResourceId == R.layout.simple_item) {
            viewHolder.item_isCheck = (CheckedTextView) itemView.findViewById(R.id.is_chk);
            viewHolder.item_isCheck.setChecked(currentItem.isChecked());

            if (currentItem.isChecked()) {
                itemView.setBackgroundResource(R.drawable.selected_item);
            } else {
                itemView.setBackgroundResource(R.drawable.item_grid);
            }
        } else if (mItemLayoutResourceId == R.layout.simple_selected_item) {

            // Get the item quantity from the current Item object and
            // set this text on the quantity_text_view TextView
            viewHolder.quantityTextView = (TextView) itemView.findViewById(R.id.quantity_text_view);
            viewHolder.quantityTextView.setText(String.valueOf(currentItem.getQuantity()));


            viewHolder.decrementButton = (Button) itemView.findViewById(R.id.decrement_quantity_btn);
            //setup Decrement Quantity Button on click event
            viewHolder.decrementButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    if (currentItem.getQuantity() > 1) {
                        //update quantity TextView for the holder row
                        currentItem.setQuantity(0);
                        viewHolder.quantityTextView.setText(String.valueOf(currentItem.getQuantity()));

                    } else if (currentItem.getQuantity() == 1) {

                        //update quantity of object  to be 0
                        currentItem.setQuantity(0);
                        //remove item from the selected listview
                        mListItems.remove(position);
                        adp.notifyDataSetChanged();

                    }

                    //update total price TextView & total items
                    orderData.setOrderDetails(mListItems);

                }
            });

            viewHolder.incrementButton = (Button) itemView.findViewById(R.id.increment_quantity_btn);
            //setup Decrement Quantity Button on click event

            viewHolder.incrementButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (currentItem.getQuantity() < 10) {

                        currentItem.setQuantity(1);
                        viewHolder.quantityTextView.setText(String.valueOf(currentItem.getQuantity()));

                        //update total price TextView & total items
                        orderData.setOrderDetails(mListItems);

                    } else {
                        Toast.makeText(mContxt, "You can't order more than 10 time for the item", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            Button removeButton = (Button) itemView.findViewById(R.id.remove_item_btn);
            //setup increment Quantity Button on click event
            removeButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    //remove the item from the listview
                    mListItems.remove(position);
                    //refresh listView
                    adp.notifyDataSetChanged();

                    //update total price TextView & total items
                    orderData.setOrderDetails(mListItems);

                    //if no items were selected (or all selected items have removed )
                    //then negative to main activity
                    if (mListItems.size() == 0){
                       Intent i = new Intent(mContxt,MainActivity.class);
                       mContxt.startActivity(i);
                    }
                }
            });
        }

        // Return the whole list item layout
        // so that it can be shown in the ListView
        return itemView;
    }


}

