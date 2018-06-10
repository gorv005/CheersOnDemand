package com.cheersondemand.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cheersondemand.R;
import com.cheersondemand.model.SectionDataModel;

import java.util.ArrayList;



public class AdapterHomeProductsSections extends RecyclerView.Adapter<AdapterHomeProductsSections.ItemRowHolder> {

    private ArrayList<SectionDataModel> dataList;
    private Context mContext;

    public AdapterHomeProductsSections(Context context, ArrayList<SectionDataModel> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item, null);
        ItemRowHolder mh = new ItemRowHolder(v);
        return mh;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        final String sectionName = dataList.get(i).getHeaderTitle();

        ArrayList singleSectionItems = dataList.get(i).getAllItemsInSection();

        itemRowHolder.itemTitle.setText(sectionName);

        AdapterHomeProducts adapterHomeProducts = new AdapterHomeProducts (singleSectionItems,mContext);

        itemRowHolder.recyclerProductList.setHasFixedSize(true);
        itemRowHolder.recyclerProductList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recyclerProductList.setAdapter(adapterHomeProducts);


        itemRowHolder.tvSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Toast.makeText(v.getContext(), "click event on more, "+sectionName , Toast.LENGTH_SHORT).show();



            }
        });


       /* Glide.with(mContext)
                .load(feedItem.getImageURL())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.bg)
                .into(feedListRowHolder.thumbView);*/
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        protected TextView itemTitle,tvSeeMore;

        protected RecyclerView recyclerProductList;

        public ItemRowHolder(View view) {
            super(view);

            this.itemTitle = (TextView) view.findViewById(R.id.tvTitle);
            this.tvSeeMore = (TextView) view.findViewById(R.id.tvSeeMore);

            this.recyclerProductList = (RecyclerView) view.findViewById(R.id.recyclerProductlist);


        }


    }
}
