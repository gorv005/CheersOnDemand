package com.cheersondemand.view.address;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.address.Address;
import com.cheersondemand.util.C;
import com.cheersondemand.util.ImageLoader.ImageLoader;
import com.cheersondemand.view.ActivityContainer;

import java.util.List;

/**
 * Created by AB on 6/7/2018.
 */

public class AdapterAddress extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<Address> horizontalList;
    Context context;
    ImageLoader imageLoader;
public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvName,tvSubAddress;
    View llEdit,llDelete;
    public ItemViewHolder(View view) {
        super(view);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvSubAddress = (TextView) view.findViewById(R.id.tvSubAddress);
        llEdit = (View) view.findViewById(R.id.llEdit);
        llDelete = (View) view.findViewById(R.id.llDelete);

    }
}

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        View rlAddNew;
        public FooterViewHolder(View view) {
            super(view);
            rlAddNew = (View) view.findViewById(R.id.rlAddNew);
        }
    }
    public AdapterAddress(List<Address> horizontalList, Activity context) {
        this.horizontalList = horizontalList;

        this.context=context;
        imageLoader=new ImageLoader(context);

    }

    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType==TYPE_ITEM) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_address_profile, parent, false);

        return new ItemViewHolder(itemView);
    }
    else if (viewType == TYPE_FOOTER) {
        //Inflating footer view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_new_address, parent, false);
        return new FooterViewHolder(itemView);
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

            itemViewHolder.tvName.setText(horizontalList.get(position).getName());
            itemViewHolder.tvSubAddress.setText(horizontalList.get(position).getFlatNo()+" "+horizontalList.get(position).getAddressFirst()
            +" "+horizontalList.get(position).getAddressSecond()+ " "+horizontalList.get(position).getZipCode());

            itemViewHolder.llEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ActivityContainer.class);
                    Bundle bundle=new Bundle();
                    bundle.putBoolean(C.IS_EDIT,true);
                    bundle.putSerializable(C.ADDRESS,horizontalList.get(position));

                    intent.putExtra(C.BUNDLE,bundle);
                    intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_ADD_ADDRESS);
                    context.startActivity(intent);
                }
            });
            itemViewHolder.llDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                //    Toast.makeText(context, itemViewHolder.tvBrandName.getText().toString(), Toast.LENGTH_SHORT).show();
                    ((ActivityContainer)context).removeAddress(horizontalList.get(position),position);
                }
            });
        }
        else if (holder instanceof FooterViewHolder) {
            final FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.rlAddNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ActivityContainer.class);
                    Bundle bundle=new Bundle();
                    bundle.putBoolean(C.IS_EDIT,false);
                    intent.putExtra(C.BUNDLE,bundle);
                    intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_ADD_ADDRESS);
                    context.startActivity(intent);
                 //   Toast.makeText(context, "More", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
    @Override
    public int getItemViewType(int position) {
        if (position == horizontalList.size() ) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }
    @Override
    public int getItemCount() {
        return horizontalList.size()+1;
    }

}