package com.example.tp5;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.Manifest;

public class MainActivity extends AppCompatActivity {
    public static ListView category_list;
    public List<Category> category_items;
    private MyListAdapter adapter;
    private static final int REQUEST_CODE=1;
    public static final int REQUEST_CATEGORY=9898;
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 5;


    public boolean onOptionsItemSelected(MenuItem item){
if(item.getItemId()==R.id.add_category){
    Intent intent = new Intent(this, Add_category_Form_Activity.class);
    startActivityForResult(intent,REQUEST_CODE);
}
        return (true);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            Category form_data = data.getParcelableExtra("category");
            category_items.add(form_data);
            adapter.notifyDataSetChanged();
            }
        if(requestCode==REQUEST_CATEGORY && resultCode==RESULT_OK){
        Category category_back=data.getParcelableExtra("category_back");
        int position=data.getIntExtra("position_category_back",0);
        category_items.set(position,category_back);
        adapter.notifyDataSetChanged();
        }
        }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize_lists();
    }
    public void initialize_lists(){
    category_list=findViewById(R.id.category_list);
        ArrayList<Product> product_list=new ArrayList<Product>();
        category_items=new ArrayList<Category>();

        ImageView sim=new ImageView(this);
        ImageView sosemie=new ImageView(this);
        sosemie.setImageResource(R.drawable.sosemie);

        //the urls:
        String sosemie_url=null;
        String sim_url=null;

        //sim:
        sim.setImageResource(R.drawable.sim);
        Bitmap sim_bitmap = ((BitmapDrawable) sim.getDrawable()).getBitmap();
        //take storage permission , save the IMG_file and get the url of the saved image :
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            sim_url=saveImageToExternalStorage(sim_bitmap,"sim");
        }

        //sosemie:
        sosemie.setImageResource(R.drawable.sosemie);
        Bitmap sosemie_bitmap = ((BitmapDrawable) sosemie.getDrawable()).getBitmap();
        //take storage permission , save the IMG_file and get the url of the saved image :
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE }, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        } else {
            sosemie_url=saveImageToExternalStorage(sosemie_bitmap,"sosemie");
        }

        product_list.add(new Product("5kg sim","",sim_url));
        product_list.add(new Product("5kd sosemie","",sosemie_url));


        category_items.add(new Category("farine","",product_list));


        adapter=new MyListAdapter(this,category_items);
        category_list.setAdapter(adapter);
    }
    private String saveImageToExternalStorage(Bitmap Product_bitmap,String ProductName) {

        // Get the directory for the user's public pictures directory.
        File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        // Create a directory for your app if it doesn't exist
        File directory = new File(root.getAbsolutePath() + "/MyAppImages");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Create a file name
        String fileName = "Product_IMG_" + ProductName + ".jpeg";

        // Create the file
        File file = new File(directory, fileName);

        try {

            // Save the bitmap to the file
            FileOutputStream fos = new FileOutputStream(file);
            Product_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();

            // Return the file path
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            // If an exception occurs, return null
            return null;
        }
    }
}
