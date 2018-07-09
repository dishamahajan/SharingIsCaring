package com.example.md66805.sharingiscaring.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.md66805.sharingiscaring.R;
import com.example.md66805.sharingiscaring.activity.DetailActivity;
import com.example.md66805.sharingiscaring.domain.ListItem;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final ListItem listItem = listItems.get(position);
        viewHolder.heading.setText(listItem.getHeading());
        viewHolder.count.setText(listItem.getCount());
        viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detail", listItem);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView heading;
        public TextView count;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            heading = itemView.findViewById(R.id.textViewHeader);
            count = itemView.findViewById(R.id.textViewCount);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
