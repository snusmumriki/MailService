package com.lort.mail;

import android.app.Activity;
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

    private List<Form> forms;
    private int lastIndex;

    public FormAdapter(List<Form> forms) {
        this.forms = forms;
    }

    public List<Form> getForms() {
        return forms;
    }

    public void setForms(List<Form> forms) {
        this.forms = forms;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    @Override
    public FormAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_barcode, parent, false);
        return new FormAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final FormAdapter.MyViewHolder holder, int position) {
        Activity activity = (Activity) holder.view.getContext();
        Form form = forms.get(position);
        holder.name.setText(form.getName());
        holder.address.setText(form.getAddress());
        holder.bar.setText(form.getBar());
        holder.view.setOnClickListener(v -> activity.startActivityForResult(
                new Intent(activity, FormEditActivity.class).putExtra("form", form),
                TaskActivity.FORM_EDIT));
    }

    @Override
    public int getItemCount() {
        return forms.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public TextView name;
        public TextView address;
        public TextView bar;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            name = (TextView) itemView.findViewById(R.id.name_card_bar);
            address = (TextView) itemView.findViewById(R.id.address_card_bar);
            bar = (TextView) itemView.findViewById(R.id.bar_card_bar);
        }
    }
}

