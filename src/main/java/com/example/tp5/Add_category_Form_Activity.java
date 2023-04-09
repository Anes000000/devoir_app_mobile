package com.example.tp5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Add_category_Form_Activity extends AppCompatActivity {
    private EditText name;
    private EditText description;
    private Button add_category;
    private Button cancel_form;
    private Category form_category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        name=findViewById(R.id.product_name);
        description=findViewById(R.id.product_description);
        add_category=findViewById(R.id.edit_product);
        cancel_form=findViewById(R.id.cancel_product_form_button);
       handle_buttons();
    }
    public void handle_buttons(){
        add_category.setOnClickListener(event->{
            Intent resultIntent = new Intent();
            form_category=new Category( name.getText().toString(), description.getText().toString(),new ArrayList<Product>());
            resultIntent.putExtra("category", form_category);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });
        cancel_form.setOnClickListener(event->{
            finish();
        });
    }
}
