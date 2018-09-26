package com.example.tvo.testtrafficnetwork;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.Viewholder> {
    private List<Package> list;
    private Itemclick itemclick;

    public Adapter(List<Package> list, Itemclick itemclick) {
        this.list = list;
        this.itemclick = itemclick;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_layout,viewGroup,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder viewholder, final int i) {
        viewholder.mTextPackage.setText(list.get(i).toString());
        try {
            viewholder.icon.setImageDrawable(viewholder.context.getPackageManager().getApplicationIcon(list.get(i).getPackageName()));
            viewholder.mTextPackage.setText(list.get(i).getPackageName());
            viewholder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemclick.onclick(list.get(i));
                }
            });
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Viewholder extends RecyclerView.ViewHolder{
        TextView mTextPackage; ImageView icon; Context context; ConstraintLayout constraintLayout;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            mTextPackage = itemView.findViewById(R.id.txt_page);
            icon = itemView.findViewById(R.id.img_icon);
            constraintLayout = itemView.findViewById(R.id.item);
        }
    }
    interface Itemclick{
        void onclick(Package mypackage);
    }
}
