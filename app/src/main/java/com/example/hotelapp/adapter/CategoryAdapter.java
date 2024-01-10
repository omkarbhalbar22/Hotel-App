package com.example.hotelapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.bumptech.glide.Glide;
import com.example.hotelapp.R;
import com.example.hotelapp.activities.CategoryActivity;
import com.example.hotelapp.model.Category;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    Context context;
    List<Category> categories;
//
    public CategoryAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }
//
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_categories, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
//

       holder.textView.setText(category.getName());
        Glide.with(context)
                .load(category.getDrawable())
                .into(holder.roundedImageView);

       holder.roundedImageView.setBackgroundColor(Color.parseColor(category.getColor()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CategoryActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("catId", category.getId());
                intent.putExtra("categoryName", category.getName());
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return categories.size();
    }
//
    public class CategoryViewHolder extends RecyclerView.ViewHolder {
//        ItemCategoriesBinding binding;
    RoundedImageView roundedImageView;
    TextView textView;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            roundedImageView=itemView.findViewById(R.id.image);
            textView=itemView.findViewById(R.id.label);
        }

    }
}
