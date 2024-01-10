package com.example.hotelapp.adapter;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelapp.R;



import com.example.hotelapp.model.Product;
import com.hishd.tinycart.model.Cart;
import com.hishd.tinycart.util.TinyCartHelper;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    Context context;
    ArrayList<Product> products;
    CartListener cartListener;
    Cart cart;
    String name1;
    int stock1,qu;
    TextView  productName,productStock, quantity;
    Button  plusBtn,minusBtn,saveBtn;
    View customelayout;
   Product product;
    public interface CartListener {
        public void onQuantityChanged();
    }


    public CartAdapter(Context context, ArrayList<Product> products, CartListener cartListener) {
        this.context = context;
        this.products = products;
        this.cartListener = cartListener;
        cart = TinyCartHelper.getCart();
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cart, parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        product = products.get(position);
        Glide.with(context)
                .load(product.getImage())
                .into(holder.image);

        holder.name.setText(product.getName());
        holder.price.setText("INR " + product.getPrice());
        holder.quantity.setText(product.getQuantity() + " item(s)");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                LayoutInflater inflater=LayoutInflater.from(context);
                View dialogview=inflater.inflate(R.layout.quantity_dialog,null);
                builder.setView(dialogview);
                productName=(TextView)dialogview.findViewById(R.id.productName);
                productStock=(TextView)dialogview.findViewById(R.id.productStock);
                quantity=(TextView)dialogview.findViewById(R.id. quantity);
                plusBtn=(Button)dialogview.findViewById(R.id.plusBtn);
                minusBtn=(Button)dialogview.findViewById(R.id.minusBtn);
                saveBtn=(Button)dialogview.findViewById(R.id.saveBtn);


                AlertDialog dialog=builder.create();

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

                productName.setText(product.getName());
                productStock.setText(String.format("Stock: %d",product.getStock()));
                quantity.setText(String.valueOf(product.getQuantity()));
                int stock = product.getStock();

                plusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int quantity1 = product.getQuantity();
                        quantity1++;

                        if(quantity1>product.getStock()) {
                            Toast.makeText(context, "Max stock available: "+ product.getStock(), Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            product.setQuantity(quantity1);
                            quantity.setText(String.valueOf(quantity1));
                        }

                        notifyDataSetChanged();
                        cart.updateItem(product, product.getQuantity());
                        cartListener.onQuantityChanged();
                    }
                });

                minusBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int quantity1 = product.getQuantity();
                        if(quantity1 > 1)
                            quantity1--;
                        product.setQuantity(quantity1);
                        quantity.setText(String.valueOf(quantity1));

                        notifyDataSetChanged();
                        cart.updateItem(product, product.getQuantity());
                        cartListener.onQuantityChanged();
                    }
                });
                saveBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        notifyDataSetChanged();
                        cart.updateItem(product, product.getQuantity());
                        cartListener.onQuantityChanged();
                    }
                });

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name,quantity,price;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
           image=itemView.findViewById(R.id.image);
           name=itemView.findViewById(R.id.name);
           quantity=itemView.findViewById(R.id.quantity);
           price=itemView.findViewById(R.id.price);
        }
    }

}
