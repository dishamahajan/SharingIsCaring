package com.example.md66805.sharingiscaring.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.md66805.sharingiscaring.R;
import com.example.md66805.sharingiscaring.activity.DetailActivity;
import com.example.md66805.sharingiscaring.activity.MainActivity;
import com.example.md66805.sharingiscaring.domain.ItemDetails;
import com.example.md66805.sharingiscaring.domain.ListItem;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    private List<ItemDetails> items;
    private String racfId;
    private Context context;

    public ListItemAdapter(List<ItemDetails> items, String racfId, Context context) {
        this.items = items;
        this.racfId = racfId;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_details, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ItemDetails listItem = items.get(position);
        viewHolder.deviceName.setText(listItem.getType());
        viewHolder.domain.setText(listItem.getDomain());
        viewHolder.name.setText(listItem.getName());

        if(listItem.getName().equalsIgnoreCase("Admin")) {
            viewHolder.checkIn.setEnabled(true);
            viewHolder.checkIn.setText("Check In");
        } else if(listItem.getName().equalsIgnoreCase(racfId)){
            viewHolder.checkIn.setEnabled(true);
            viewHolder.checkIn.setText("Check Out");
        } else {
            viewHolder.checkIn.setText("Not available");
            viewHolder.checkIn.setEnabled(false);
        }

        viewHolder.checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, racfId, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView deviceName;
        public TextView domain;
        public TextView name;
        public Button checkIn;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            deviceName = itemView.findViewById(R.id.textViewNameDevice);
            domain = itemView.findViewById(R.id.textViewDomain);
            name = itemView.findViewById(R.id.textViewName);
            linearLayout = itemView.findViewById(R.id.linearLayoutDetail);
            checkIn = itemView.findViewById(R.id.buttonCheckIn);
        }
    }
}
