package com.example.tp5;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Edit_product_form extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private ImageView image;
    private Button edit;
    private Button cancel;
    private Product product_to_edit;
    private int position;
    private static final int CAMERA_PERMISSION_REQUEST_CODE=489;
    private static final int REQUEST_IMAGE_CAPTURE=949;
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
            } else {
                // Permission denied, you cannot access the camera
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_product_form);
        fill_the_content();
        handle_events();
    }
    public void fill_the_content(){
        product_to_edit = getIntent().getParcelableExtra("product_to_edit");
        position= getIntent().getIntExtra("position",0);
        edit=findViewById(R.id.edit_product);
        cancel=findViewById(R.id.cancel_product_form_button);
        name=findViewById(R.id.product_name);
        description=findViewById(R.id.product_description);
        image=findViewById(R.id.taken_photo);
        name.setText(product_to_edit.getName());
        description.setText(product_to_edit.getDescription());
        File imgFile = new File(product_to_edit.getUrl());
        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            image.setImageBitmap(myBitmap);
        } else {
            System.out.println("image not uplaoded yet");
        }
    }
    public void handle_events(){
        edit.setOnClickListener(event->{
            //get the bitmap
            Drawable drawable = image.getDrawable();
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            //save the image and get the url
            String new_image_url=saveImageToExternalStorage(bitmap,name.getText().toString());
            Product edited_product=new Product(name.getText().toString(),description.getText().toString(),new_image_url);
            Intent intent=new Intent();
            intent.putExtra("edited_product", edited_product);
            intent.putExtra("position",position );
            setResult(Activity.RESULT_OK, intent);
            finish();
        });
        cancel.setOnClickListener(event->{
finish();
        });
        TextView take_photo_of_your_product=findViewById(R.id.take_photo_textview);
        take_photo_of_your_product.setOnClickListener(
                event->{
                    requestCameraPermission();
                }
        );
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
