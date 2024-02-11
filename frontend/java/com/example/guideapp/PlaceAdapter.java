package com.example.guideapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>{

    Context ctx;
    List<Place> data;

    public PlaceAdapter(Context ctx, List<Place> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public PlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(ctx).inflate(R.layout.places_row_layout,parent,false);
        PlaceViewHolder holder = new PlaceViewHolder(root);

        holder.setIsRecyclable(false);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceViewHolder holder, int position) {

        holder.txtName.setText(data.get(position).getName());
        holder.txtDesc.setText(data.get(position).getDescription());
        ExecutorService srv = ((GuideApp)((Activity)ctx).getApplication()).srv;

        holder.downloadImage(srv,data.get(position).getImages());

        holder.row.setOnClickListener(v->{

            NavController navController =
                    Navigation.findNavController((Activity) ctx,R.id.fragmentContainerView);

            Bundle dataBundle = new Bundle();
            dataBundle.putString("placeId",data.get(holder.getAdapterPosition()).getPlaceId());

            navController.navigate(R.id.action_fragmentListOpSys_to_fragmentDetails,dataBundle);


        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PlaceViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout row;
        TextView txtName;
        ImageView imgOpSys;

        TextView txtDesc;
        boolean imageDownloaded;


        Handler imageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                imgOpSys.setImageBitmap((Bitmap) msg.obj);
                imageDownloaded = true;

                return true;
            }
        });


        public PlaceViewHolder(@NonNull View itemView) {
            super(itemView);
            row = itemView.findViewById(R.id.row_list);
            txtName = itemView.findViewById(R.id.txtName);
            imgOpSys = itemView.findViewById(R.id.imgList);
            txtDesc = itemView.findViewById(R.id.txtDesc);
        }

        public void downloadImage(ExecutorService srv, String path){

            if(!imageDownloaded){
                AppGuideRepo repo = new AppGuideRepo();
                repo.downloadImage(srv,imageHandler,path);
            }


        }


    }


}
