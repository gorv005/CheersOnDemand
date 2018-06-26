package com.cheersondemand.view.adapter;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.cheersondemand.R;
import com.cheersondemand.model.HomeCategoriesSectionList;

import java.util.ArrayList;
import java.util.List;


public class AdapterHomeCategoriesSections extends RecyclerView.Adapter<AdapterHomeCategoriesSections.ItemRowHolder> {

    private ArrayList<HomeCategoriesSectionList> dataList;
    private Activity mContext;
    AdapterHomeCategories adapterHomeCategories;
    public AdapterHomeCategoriesSections(Activity context, ArrayList<HomeCategoriesSectionList> dataList) {
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

        List category = dataList.get(i).getAllProducts();

        itemRowHolder.itemTitle.setText(sectionName);

         adapterHomeCategories = new AdapterHomeCategories (true,category,mContext);

        itemRowHolder.recyclerProductList.setHasFixedSize(true);
        itemRowHolder.recyclerProductList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recyclerProductList.setAdapter(adapterHomeCategories);


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

   public void notified(){
        adapterHomeCategories.notifyDataSetChanged();
    }
}
