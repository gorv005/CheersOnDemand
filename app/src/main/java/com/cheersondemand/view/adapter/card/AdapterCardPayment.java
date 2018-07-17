package com.cheersondemand.view.adapter.card;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.card.CardList;
import com.cheersondemand.util.ImageLoader.ImageLoader;

import java.util.List;

/**
 * Created by AB on 6/7/2018.
 */

public class AdapterCardPayment extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<CardList> horizontalList;
    Activity context;
    ImageLoader imageLoader;
public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tv_card_number,tv_member_name,tv_validity;
    public ImageView ivType;

    public ItemViewHolder(View view) {
        super(view);

        tv_card_number = (TextView) view.findViewById(R.id.tv_card_number);
        tv_member_name = (TextView) view.findViewById(R.id.tv_member_name);
        tv_validity = (TextView) view.findViewById(R.id.tv_validity);
        ivType = (ImageView) view.findViewById(R.id.ivType);

    }
}


    public AdapterCardPayment(List<CardList> horizontalList, Activity context) {
        this.horizontalList = horizontalList;
        this.context=context;
        imageLoader=new ImageLoader(context);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType==TYPE_ITEM) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_payment, parent, false);

        return new ItemViewHolder(itemView);
    }

    else
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.tv_card_number.setText( context.getString(R.string.card_number_sample)+" "+horizontalList.get(position).getLast4());
            itemViewHolder.tv_member_name.setText(horizontalList.get(position).getCardHolderName());
            itemViewHolder.tv_validity.setText(horizontalList.get(position).getExpMonth()+"/"+horizontalList.get(position).getExpYear());

        }

    }
    @Override
    public int getItemViewType(int position) {

        return TYPE_ITEM;
    }
    @Override
    public int getItemCount() {
        return horizontalList.size();
    }


}
