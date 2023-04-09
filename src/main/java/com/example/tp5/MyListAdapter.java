package com.example.tp5;

import static com.example.tp5.MainActivity.REQUEST_CATEGORY;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MyListAdapter extends ArrayAdapter<Category> {

    private Activity context;
    private List<Category> listCategory;

    public MyListAdapter(Activity context, List<Category> listCategory) {
        super(context, R.layout.my_list_item, listCategory);
        this.context = context;
        this.listCategory = listCategory;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom layout for the list item
        if (convertView == null) {
            LayoutInflater inflater= context.getLayoutInflater();
            convertView=inflater.inflate(R.layout.my_list_item,null);
        }

        // Get references to the views within the list item layout
        TextView itemName =convertView.findViewById(R.id.product_name);
        Button delete_button = convertView.findViewById(R.id.delete_category_button);

        itemName.setText(listCategory.get(position).getName());

        // Bind data to the views
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle delete_button click event
                listCategory.remove(position);
                notifyDataSetChanged();
            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent(getContext(),Category_Products_Activity.class);
                resultIntent.putExtra("category_clicked", listCategory.get(position));
                resultIntent.putExtra("category_position", position);
                ((Activity)getContext()).startActivityForResult(resultIntent,REQUEST_CATEGORY);
            }
        });



        // Return the completed view for the list item
        return convertView;
    }
}