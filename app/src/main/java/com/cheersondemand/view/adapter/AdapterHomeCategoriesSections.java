package com.cheersondemand.view.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.AllProduct;
import com.cheersondemand.model.HomeCategoriesSectionList;
import com.cheersondemand.util.C;
import com.cheersondemand.util.Util;
import com.cheersondemand.view.ActivityContainer;
import com.cheersondemand.view.fragments.FragmentHome;

import java.util.ArrayList;
import java.util.List;


public class AdapterHomeCategoriesSections extends RecyclerView.Adapter<AdapterHomeCategoriesSections.ItemRowHolder> {

    private ArrayList<HomeCategoriesSectionList> dataList;
    private Activity mContext;
    List<AllProduct> allProductList;
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
    public void modifyList(){
        adapterHomeCategories.modifyList();
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {

        final String sectionName = dataList.get(i).getHeaderTitle();

        allProductList = dataList.get(i).getAllProducts();

        itemRowHolder.itemTitle.setText(sectionName);

         adapterHomeCategories = new AdapterHomeCategories (true,allProductList,mContext);

        itemRowHolder.recyclerProductList.setHasFixedSize(true);
       // itemRowHolder.recyclerProductList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        itemRowHolder.recyclerProductList.setLayoutManager(new GridLayoutManager(mContext, 2));
        itemRowHolder.recyclerProductList.addItemDecoration(new GridSpacingItemDecoration(2, Util.dpToPx(1, mContext), true));
        itemRowHolder.recyclerProductList.setItemAnimator(new DefaultItemAnimator());
        itemRowHolder.recyclerProductList.setAdapter(adapterHomeCategories);


        itemRowHolder.tvSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


           //     Toast.makeText(v.getContext(), "click event on more, "+sectionName , Toast.LENGTH_SHORT).show();

                Intent intent=new Intent(mContext,ActivityContainer.class);

                Bundle bundle=new Bundle();
                bundle.putString(C.CAT_ID,"");
                bundle.putString(C.SUB_CAT_ID,"");

                bundle.putInt(C.SOURCE,C.FRAGMENT_PRODUCTS_HOME);
                intent.putExtra(C.BUNDLE,bundle);
                intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_PRODUCT_LISTING);

                mContext.startActivity(intent);

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
        adapterHomeCategories.modifyList();
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

}
