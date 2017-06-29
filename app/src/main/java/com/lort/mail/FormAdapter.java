package com.lort.mail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by nikita on 22.06.17.
 */

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.MyViewHolder> {

    private List<Form> barList;
    Form bar;

    private String m_Text_bar = "";
    private String m_Text_name = "";
    private String m_Text_address = "";

    public FormAdapter(List<Form> bars) { barList = bars; }

    @Override
    public FormAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_barcode, parent, false);
        return new FormAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FormAdapter.MyViewHolder holder, int position) {
        bar = barList.get(position);
        holder.name.setText(bar.getName());
        holder.address.setText(bar.getAddress());
        holder.bar.setText(bar.getBar());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.context, FormEditActivity.class);
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

