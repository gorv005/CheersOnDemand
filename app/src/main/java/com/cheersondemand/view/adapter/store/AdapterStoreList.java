package com.cheersondemand.view.adapter.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.store.StoreList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by GAURAV on 6/17/2018.
 */

public class AdapterStoreList extends RecyclerView.Adapter {

    private List<StoreList> itemModels;
    private Context context;
    private int lastCheckedPosition = -1;

    public AdapterStoreList(Context context, List<StoreList> itemModels) {
        this.itemModels = itemModels;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_store, viewGroup, false);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        StoreList model = itemModels.get(position);
        initializeViews(model, holder, position);
    }


    private void initializeViews(final StoreList model, final RecyclerView.ViewHolder holder, int position) {
        ((ItemViewHolder)holder).name.setText(model.getName());
        if (model.getId() == lastCheckedPosition){
            ((ItemViewHolder)holder).radioButton.setChecked(true);
        }else{
            ((ItemViewHolder)holder).radioButton.setChecked(false);
        }
        ((ItemViewHolder)holder).radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = model.getId();
                notifyItemRangeChanged(0, itemModels.size());

            }
        });
    }

    public StoreList getSelectedItem(){
        StoreList model = itemModels.get(lastCheckedPosition);
        return model;
    }
    public int selectedPosition(){
        return lastCheckedPosition;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private TextView surnameTextView;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.radio)
        RadioButton radioButton;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
