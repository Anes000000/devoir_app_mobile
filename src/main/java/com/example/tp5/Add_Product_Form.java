package com.example.tp5;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import android.Manifest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Add_Product_Form extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE=1888;
    private static final int CAMERA_PERMISSION_REQUEST_CODE=4000;
    private ImageView image;
    private Button add;
    private Button cancel;
    private EditText product_name;
    private EditText product_description;
    private String image_url=null;
    private void requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    CAMERA_PERMISSION_REQUEST_CODE);
        }else{
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 1888);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1888);
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
            image=findViewById(R.id.taken_photo);
            image.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        TextView take_photo_of_your_product=findViewById(R.id.take_photo_textview);
        take_photo_of_your_product.setOnClickListener(
                event->{
                    requestCameraPermission();
                }
        );
        product_name=findViewById(R.id.product_name);
        product_description=findViewById(R.id.product_description);
add=findViewById(R.id.edit_product);
add.setOnClickListener(event->{
    //get the bitmap
    Drawable drawable = image.getDrawable();
    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
    //save the image and get the url
    image_url=saveImageToExternalStorage(bitmap,product_name.getText().toString());
    //add a product
    Intent resultIntent = new Intent();
    Product form_product=new Product( product_name.getText().toString(), product_description.getText().toString(),image_url);
    resultIntent.putExtra("product", form_product);
    setResult(Activity.RESULT_OK, resultIntent);
    finish();
});
        cancel=findViewById(R.id.cancel_product_form_button);
        cancel.setOnClickListener(event->{
            finish();
        });
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
