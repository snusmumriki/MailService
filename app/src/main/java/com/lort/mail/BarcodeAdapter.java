package com.lort.mail;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nikita on 22.06.17.
 */

public class BarcodeAdapter extends RecyclerView.Adapter<BarcodeAdapter.MyViewHolder> {

    private List<Barcode> barList;
    Barcode bar;

    private String m_Text_bar = "";
    private String m_Text_name = "";
    private String m_Text_address = "";

    public BarcodeAdapter(List<Barcode> bars) { barList = bars; }

    @Override
    public BarcodeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_barcode, parent, false);
        return new BarcodeAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final BarcodeAdapter.MyViewHolder holder, int position) {
        bar = barList.get(position);
        holder.name.setText(bar.getName());
        holder.address.setText(bar.getAddress());
        holder.bar.setText(bar.getBar());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.context, BarEditActivity.class);
                intent.putExtra("barcode", bar);
                holder.view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() { return barList.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView name;
        public TextView address;
        public TextView bar;
        public Context context;

        public  MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = (TextView) itemView.findViewById(R.id.name_card_bar);
            address = (TextView) itemView.findViewById(R.id.address_card_bar);
            bar = (TextView) itemView.findViewById(R.id.bar_card_bar);
            context = itemView.getContext();
        }
    }

}

