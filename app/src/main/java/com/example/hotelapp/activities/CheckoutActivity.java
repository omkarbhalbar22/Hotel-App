package com.example.hotelapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.ProgressDialog;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.hotelapp.R;
import com.example.hotelapp.adapter.CartAdapter;
import com.example.hotelapp.model.Product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.model.Item;
import com.hishd.tinycart.util.TinyCartHelper;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;

import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {


    CartAdapter adapter;

    ArrayList<Product> products;
    double totalPrice = 0;
    final int tax = 5;
    ProgressDialog progressDialog;
    Cart cart;
    int totalamount;
    EditText name,email,phone,date;
    RecyclerView cartList;
    TextView subtotal,taxview,total;
    Button checkoutBtn;
    String emailaddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        name=findViewById(R.id.nameBox);
        email=findViewById(R.id.emailBox);
        phone=findViewById(R.id.phoneBox);
        subtotal=findViewById(R.id.subtotal);
        total=findViewById(R.id.total);
        cartList=findViewById(R.id.cartList);
        checkoutBtn=findViewById(R.id.checkoutBtn);
        taxview=findViewById(R.id.tax);
        date=findViewById(R.id.dateBox);


        Date date1 =new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd/MM/yy");
        date.setText(simpleDateFormat.format(date1));

        taxview.setText(tax+"%");
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing...");

        products = new ArrayList<>();

        cart = TinyCartHelper.getCart();

        emailaddress=email.getText().toString();
        for(Map.Entry<Item, Integer> item : cart.getAllItemsWithQty().entrySet()) {
            Product product = (Product) item.getKey();
            int quantity = item.getValue();
            product.setQuantity(quantity);
            Log.i("product",product.getName());
            products.add(product);
            Log.i("product",products+"");
        }
       // Log.i("product",Arrays.toString(products.toArray()));
        adapter = new CartAdapter(this, products, new CartAdapter.CartListener() {
            @Override
            public void onQuantityChanged() {
                subtotal.setText(String.format("INR %.2f",cart.getTotalPrice()));
                totalamount=cart.getTotalPrice().intValue();
                totalPrice = (cart.getTotalPrice().doubleValue() * tax / 100) + cart.getTotalPrice().doubleValue();
                total.setText("INR " + totalPrice);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, layoutManager.getOrientation());
        cartList.setLayoutManager(layoutManager);
        cartList.addItemDecoration(itemDecoration);
        cartList.setAdapter(adapter);

        subtotal.setText(String.format("INR %.2f",cart.getTotalPrice()));
        totalamount=cart.getTotalPrice().intValue();
        totalPrice = (cart.getTotalPrice().doubleValue() * tax / 100) + cart.getTotalPrice().doubleValue();
        total.setText("INR " + totalPrice);

        checkoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckoutActivity.this, PaymentActivity.class);
                intent.putExtra("price12",(int)totalPrice);
                intent.putExtra("totalamount",totalamount);
                intent.putExtra("products",products);
                intent.putExtra("emailaddress",emailaddress);
                Log.e("totalprice",String.valueOf(totalPrice));
                startActivity(intent);

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}