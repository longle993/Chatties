package com.example.chatties.Adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.chatties.databinding.ItemListImageBinding;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter {
    Activity activity;
    ArrayList<Uri> listImage;
    public ImageAdapter(Activity activity, ArrayList<Uri> listImage) {
        this.activity = activity;
        this.listImage = listImage;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemListImageBinding binding = ItemListImageBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ImageAdapter.Viewholder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Viewholder view = (Viewholder) holder;
        Uri imageURI = listImage.get(position);
        Glide.with(activity).load(imageURI).into(view.binding.imageView);
        view.binding.btnRemovePic.setOnClickListener(v -> {
            listImage.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return listImage.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        ItemListImageBinding binding;
        public Viewholder(@NonNull ItemListImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
