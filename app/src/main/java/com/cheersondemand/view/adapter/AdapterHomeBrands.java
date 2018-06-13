package com.cheersondemand.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cheersondemand.R;
import com.cheersondemand.model.Categories;
import com.cheersondemand.util.ImageLoader.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by GAURAV on 6/7/2018.
 */

public class AdapterHomeBrands extends RecyclerView.Adapter<RecyclerView.ViewHolder > {
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_ITEM = 0;
private List<Categories> horizontalList;
    Context context;
    ImageLoader imageLoader;
public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvBrandName;
    public CircleImageView ivProductImage;
    public ItemViewHolder(View view) {
        super(view);
        tvBrandName = (TextView) view.findViewById(R.id.tvBrandName);
        ivProductImage = (CircleImageView) view.findViewById(R.id.ivProductImage);
    }
}

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        CircleImageView ivMore;
        public FooterViewHolder(View view) {
            super(view);
            ivMore = (CircleImageView) view.findViewById(R.id.ivProductMore);
        }
    }
    public AdapterHomeBrands(List<Categories> horizontalList,Activity context) {
        this.horizontalList = horizontalList;
        this.context=context;
        imageLoader=new ImageLoader(context);

    }

    @Override
    public RecyclerView.ViewHolder  onCreateViewHolder(ViewGroup parent, int viewType) {
    if(viewType==TYPE_ITEM) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_round_item, parent, false);

        return new ItemViewHolder(itemView);
    }
    else if (viewType == TYPE_FOOTER) {
        //Inflating footer view
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_round_item_footer, parent, false);
        return new FooterViewHolder(itemView);
    }
    else return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder  holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            itemViewHolder.tvBrandName.setText(horizontalList.get(position).getName());
            imageLoader.DisplayImage(horizontalList.get(position).getImage(),((ItemViewHolder) holder).ivProductImage);
            itemViewHolder.tvBrandName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, itemViewHolder.tvBrandName.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (holder instanceof FooterViewHolder) {
            final FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.ivMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "More", Toast.LENGTH_SHORT).show();

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
