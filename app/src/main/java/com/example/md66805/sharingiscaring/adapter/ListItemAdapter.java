package com.example.md66805.sharingiscaring.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.md66805.sharingiscaring.R;
import com.example.md66805.sharingiscaring.domain.ItemDetails;

import java.util.List;

public class ListItemAdapter extends RecyclerView.Adapter<ListItemAdapter.ViewHolder> {

    private List<ItemDetails> items;
    private String racfId;
    private Context context;
    private View view;

    public ListItemAdapter(List<ItemDetails> items, String racfId, Context context, View view) {
        this.items = items;
        this.racfId = racfId;
        this.context = context;
        this.view = view;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_details, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {
        final ItemDetails listItem = items.get(position);
        viewHolder.deviceName.setText(listItem.getModel());
        viewHolder.domain.setText(listItem.getDomain());
        viewHolder.name.setText(listItem.getRacfId());

        if(listItem.getRacfId().equalsIgnoreCase("Admin")) {
            viewHolder.checkIn.setEnabled(true);
            viewHolder.checkIn.setText("Check In");
        } else if(listItem.getRacfId().equalsIgnoreCase(racfId)){
            viewHolder.checkIn.setEnabled(true);
            viewHolder.checkIn.setText("Check Out");
        } else {
            viewHolder.checkIn.setText("Not available");
            viewHolder.checkIn.setEnabled(false);
        }

        viewHolder.checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listItem.getRacfId().equalsIgnoreCase("Admin")) {
                    viewHolder.updateId =racfId;
                } else if(listItem.getRacfId().equalsIgnoreCase(racfId)){
                    viewHolder.updateId = "Admin";
                }
                sendPutRequest(listItem, viewHolder, viewHolder.updateId, listItem.getSerialNumber());
            }
        });
    }

    private void sendPutRequest(final ItemDetails listItem,final ViewHolder viewHolder,final String updateId, String serialNumber){
        String url = "https://salescenterdevl2.tal.deere.com/version?serialNumber="+serialNumber+"&userId="+updateId;
        StringRequest putRequest = new StringRequest(Request.Method.PUT,
                url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        if(!updateId.equalsIgnoreCase("Admin")){
                            viewHolder.checkIn.setText("CHECK OUT");
                            Snackbar.make(view,"Checked In Successfully!",Snackbar.LENGTH_SHORT).show();
                        } else {
                            viewHolder.checkIn.setText("CHECK IN");
                            Snackbar.make(view,"Checked Out Successfully!",Snackbar.LENGTH_SHORT).show();
                        }
                        viewHolder.name.setText(updateId);
                        listItem.setRacfId(updateId);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Snackbar.make(view,"Error"+ error.toString(),Snackbar.LENGTH_SHORT).show();
                    }
                }
        );
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(putRequest);
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
        public String updateId;

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
