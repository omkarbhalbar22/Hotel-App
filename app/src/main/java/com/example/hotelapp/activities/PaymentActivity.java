package com.example.hotelapp.activities;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.hotelapp.R;
import com.example.hotelapp.generator.PdfInvoiceGenerator;
import com.example.hotelapp.model.Product;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;


public class PaymentActivity extends AppCompatActivity implements PaymentResultListener, Serializable {

  //  ActivityPaymentBinding binding;
  int price,totalamount;
   Button button;
   String emailaddress;
   EditText email;

   ArrayList products;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        button=findViewById(R.id.paymentBtn);
        email=findViewById(R.id.emailBox1);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        emailaddress=getIntent().getStringExtra("emailaddress");
        email.setText(emailaddress);
        products=new ArrayList<>();

        Checkout.preload(this);
         price=getIntent().getIntExtra("price12",0);
         totalamount=getIntent().getIntExtra("totalamount",0);

         products=(ArrayList<Product>)getIntent().getSerializableExtra("products");
         Log.e("amount",String.valueOf(price));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makepayment(price);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public void makepayment(int price){
      Checkout checkout =new Checkout();
      checkout.setKeyID("rzp_test_3wom4V2z1ZGMYC");

        JSONObject object=new JSONObject();
        try {
            object.put("name","developer");
            object.put("currency","INR");
            object.put("amount",price*100);
            checkout.open(PaymentActivity.this,object);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
    public void sendEmail(String to,String subject,String body,String attachpath){
        Intent emailintent=new Intent(Intent.ACTION_SEND);
        emailintent.setType("application/pdf");

        emailintent.putExtra(Intent.EXTRA_EMAIL,new String[]{to});
        emailintent.putExtra(Intent.EXTRA_SUBJECT,subject);
        emailintent.putExtra(Intent.EXTRA_TEXT,body);
        Uri atturi= Uri.fromFile(new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()/*Environment.getExternalStorageDirectory()*/,attachpath));
        getContentResolver().takePersistableUriPermission(atturi,Intent.FLAG_GRANT_READ_URI_PERMISSION);
        emailintent.putExtra(Intent.EXTRA_STREAM,atturi);
        emailintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        emailintent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            if (emailintent.resolveActivity(getPackageManager())!=null){
                startActivity(Intent.createChooser(emailintent,"Sending Email......."));
                   //startActivity(emailintent);
            }
            else {
                Toast.makeText(this, "mail not send", Toast.LENGTH_SHORT).show();
            }
            startActivity(Intent.createChooser(emailintent,"Sending Email......."));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.e("payment",s);
        PdfInvoiceGenerator.generateInvoice(products,"invoice.pdf",totalamount,price);
        sendEmail(emailaddress,"Food Order Invoice","Hotel App","invoice.pdf");

        //Toast.makeText(this, "paymentId "+s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Error :- "+s, Toast.LENGTH_LONG).show();
        Log.e("payment",s);
    }
}