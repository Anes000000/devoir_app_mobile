package com.example.tp5;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Category_Products_Activity extends AppCompatActivity {
    private ArrayList<Product> products=new ArrayList<>();
    private Category category;
    private  MyGridAdapter adapter;
    private GridView gridView;
    private Button go_back;
    private int category_position;
    private static final int REQUEST_CODE = 0;
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Product form_product = data.getParcelableExtra("product");
            products.add(form_product);
            adapter.notifyDataSetChanged();
        }
        if (requestCode == 234) {
            Product form_product = data.getParcelableExtra("edited_product");
            int position=data.getIntExtra("position",0);
            products.set(position, form_product);
            adapter.notifyDataSetChanged();
        }
    }
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return(true);
    }
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.add_category){
            Intent intent = new Intent(this, Add_Product_Form.class);
            startActivityForResult(intent,REQUEST_CODE);
        }
        return (true);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_products);
        //get the gridview
        gridView=findViewById(R.id.products_grid);

       category= getIntent().getParcelableExtra("category_clicked");
       category_position= getIntent().getIntExtra("category_position",0);

        //get products of the category
        products=category.getProducts_list();


        //associate the data to the grid view
         adapter=new MyGridAdapter(this,products);

        //associate the adapter to the grid view
        gridView.setAdapter(adapter);

        //go back button handling
        go_back=findViewById(R.id.go_back_to_categories);
        go_back.setOnClickListener(event->{
            //return the category value back
            Intent resultIntent = new Intent();
            category.setProducts_list(products);
            resultIntent.putExtra("category_back", category);
            resultIntent.putExtra("position_category_back", category_position);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
    }
}
