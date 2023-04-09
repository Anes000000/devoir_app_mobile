package com.example.tp5;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MyGridAdapter extends BaseAdapter {

    private Context context;
    private List<Product> itemList;
    private Intent intent;

    public MyGridAdapter(Context context, List<Product> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.grid_item, parent, false);
        }
        // Set data to views
        ImageView product_image=view.findViewById(R.id.product_imageview);
        Button delete = view.findViewById(R.id.delete_product_button);
        Button edit = view.findViewById(R.id.edit_product_button);
        Product item = itemList.get(position);

        if(item.getUrl()==null){
        product_image.setImageResource(R.drawable.product_image);
        }else {
        System.out.println(item.getName()+"::::::::::::::::::::::::::::"+item.getUrl());
            File imgFile = new File(item.getUrl());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                ImageView myImage = (ImageView) view.findViewById(R.id.product_imageview);
                myImage.setImageBitmap(myBitmap);
            } else {
                System.out.println("image not uplaoded yet");
            }
      }

        delete.setOnClickListener(event->{
            itemList.remove(position);
            this.notifyDataSetChanged();
        });
edit.setOnClickListener(event->{
    Intent intent = new Intent(context, Edit_product_form.class);
    intent.putExtra("product_to_edit", itemList.get(position));
    intent.putExtra("position", position);
    ((Activity) context).startActivityForResult(intent, 234);
});
        return view;
    }
}
