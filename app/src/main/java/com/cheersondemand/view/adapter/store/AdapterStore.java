package com.cheersondemand.view.adapter.store;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.store.StoreList;
import com.cheersondemand.util.C;

import java.util.ArrayList;
import java.util.List;


public class AdapterStore extends BaseAdapter implements Filterable{


    private final LayoutInflater mInflater;
    private Activity activity;
    private List<StoreList> Items;
    int pos=-1;

    private int lastCheckedPosition = -1;
List<StoreList> filterList;
    StoreListFilter filter;
    StoreList selectedStore;
    public AdapterStore(int source,Activity activity, List<StoreList> sideMenuItems, StoreList store) {
        this.activity = activity;
        this.Items = sideMenuItems;
        mInflater = LayoutInflater.from(activity);
        this.filterList=sideMenuItems;
        this.selectedStore=store;
        if(source== C.HOME ||source== C.FRAGMENT_PRODUCT_LISTING ) {
            if (store != null) {
                lastCheckedPosition = store.getId();
            }
        }
       /* else {
            if(sideMenuItems!=null && sideMenuItems.size()>0) {
                lastCheckedPosition = sideMenuItems.get(0).getId();
            }
        }*/
    }




    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return Items.size();
    }

    @Override
    public StoreList getItem(int position) {
        return Items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemViewHolder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_store, null);
            holder = new ItemViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.radioButton = (RadioButton) convertView.findViewById(R.id.radio);
            holder.rlStoreItem = (RelativeLayout) convertView.findViewById(R.id.rlStoreItem);

            convertView.setTag(holder);
        }
        else {
            holder = (ItemViewHolder) convertView.getTag();
        }
        StoreList model=Items.get(position);

        initializeViews(model, holder, position);

        return convertView;

    }
    public StoreList getSelectedItem() {
        if(pos!=-1) {
            return filterList.get(pos);
        }
        return null;
    }

    public StoreList getNonSelectedItem() {
        for (int i=0;i<Items.size();i++) {
          if(getSelectedItem().getId()!=Items.get(i).getId()) {

              return Items.get(i);
          }
        }
        return null;
    }
    private void initializeViews(final StoreList model, final ItemViewHolder holder, final int position) {
        ((ItemViewHolder)holder).name.setText(model.getName());
        if (model.getId() == lastCheckedPosition){
            pos=position;
            ((ItemViewHolder)holder).radioButton.setChecked(true);
            ((ItemViewHolder)holder).name.setTextColor(ContextCompat.getColor(activity, R.color.profile_text_color));

        }else{
            ((ItemViewHolder)holder).radioButton.setChecked(false);
            ((ItemViewHolder)holder).name.setTextColor(ContextCompat.getColor(activity, R.color.profile_email_color));

        }
        ((ItemViewHolder)holder).radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = model.getId();
                pos=position;
                notifyDataSetChanged();
            }
        });
        ((ItemViewHolder)holder).rlStoreItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastCheckedPosition = model.getId();
                pos=position;
                notifyDataSetChanged();

            }
        });
    }

    @Override
    public Filter getFilter() {
        if(filter == null)
        {
            filter=new StoreListFilter();
        }

        return filter;
    }

    public static class ItemViewHolder {

        private TextView name;
        private RadioButton radioButton;
        private RelativeLayout rlStoreItem;


    }

    class StoreListFilter extends Filter
    {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
        // TODO Auto-generated method stub

        FilterResults results=new FilterResults();

        if(constraint != null && constraint.length()>0)
        {
            //CONSTARINT TO UPPER
            constraint=constraint.toString().toLowerCase();

          List<StoreList> filters=new ArrayList<StoreList>();

            //get specific items
            for(int i=0;i<filterList.size();i++)
            {
                if(filterList.get(i).getName().toLowerCase().contains(constraint))
                {
                    StoreList p=new StoreList( filterList.get(i).getId(),filterList.get(i).getName());

                    filters.add(p);
                }
            }

            results.count=filters.size();
            results.values=filters;

        }else
        {
            results.count=filterList.size();
            results.values=filterList;

        }

        return results;
    }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
        // TODO Auto-generated method stub

        Items=(ArrayList<StoreList>) results.values;
        notifyDataSetChanged();
    }

    }
}
