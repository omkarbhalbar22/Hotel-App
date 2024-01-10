package com.example.hotelapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.hotelapp.R;
import com.example.hotelapp.model.Product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDetailActivity extends AppCompatActivity {

     ImageView productImage;
     TextView productDescription;

     Button addToCartBtn;


     Product currentProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);



        productImage=findViewById(R.id.productImage);
        productDescription=findViewById(R.id.productDescription);
        addToCartBtn=findViewById(R.id.addToCartBtn);



        String name = getIntent().getStringExtra("name");
        String status = getIntent().getStringExtra("status");
        int image = getIntent().getIntExtra("image",0);
        int id = getIntent().getIntExtra("id",0);
        int stock = getIntent().getIntExtra("stock",0);
        double price = getIntent().getDoubleExtra("price",0);
        double discount = getIntent().getDoubleExtra("discount",0);
        String desc=getIntent().getStringExtra("desc");
        Log.e("stock",String.valueOf(stock));

        Glide.with(this)
                .load(image)
                .into(productImage);

        getSupportActionBar().setTitle(name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        currentProduct=new Product(name,status,desc,price,discount,id,image,stock);

        productDescription.setText(desc);

        Cart cart = TinyCartHelper.getCart();


        addToCartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.addItem(currentProduct,1);
                addToCartBtn.setEnabled(false);
                addToCartBtn.setText("Added in cart");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cart, menu);
        return super.onCreateOptionsMenu(menu);
    }
//
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.cart1) {
            startActivity(new Intent(this, CartActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}