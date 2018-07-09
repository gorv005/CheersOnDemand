package com.cheersondemand.view.adapter.helpcenter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.HelpCenter.HelpCenterList;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.view.ActivityContainer;

import java.util.List;

/**
 * Created by AB on 6/7/2018.
 */

public class AdapterHelpCenter extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<HelpCenterList> horizontalList;
    Context context;
    ImageLoader imageLoader;
public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvMainAdress;
    RelativeLayout rlRecentSearch;
    public ItemViewHolder(View view) {
        super(view);
        tvMainAdress = (TextView) view.findViewById(R.id.tvMainAddress);
        rlRecentSearch = (RelativeLayout) view.findViewById(R.id.rlRecentSearch);

    }
}


    public AdapterHelpCenter(List<HelpCenterList> horizontalList, Activity context) {
        this.horizontalList = horizontalList;
        this.context=context;
        imageLoader=new ImageLoader(context);

    }

    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType==TYPE_ITEM) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recent_search_address, parent, false);

        return new ItemViewHolder(itemView);
    }

    else return null;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.tvMainAdress.setText(horizontalList.get(position).getName());

            itemViewHolder.rlRecentSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putSerializable(C.HELP_ITEM,horizontalList.get(position));
                    ((ActivityContainer)context).fragmnetLoader(C.FRAGMENT_HELP_CENTER_PAGES,bundle);
                }
            });
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
