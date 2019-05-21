package com.cheersondemand.view.adapter.location;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.location.RecentLocation;
import com.cheersondemand.util.C;
import com.cheersondemand.util.SharedPreference;
import com.cheersondemand.view.ActivityContainer;

import java.util.ArrayList;
import java.util.List;


public class AdapterSavedLocations extends BaseAdapter implements Filterable{


    private final LayoutInflater mInflater;
    private Activity activity;
    private List<RecentLocation> Items;
    int pos=-1;

    private String lastCheckedPosition ;
List<RecentLocation> filterList;
    StoreListFilter filter;
    String selectedLocation;
    RecentLocation recentSelectedLocation;

    public AdapterSavedLocations(int source, Activity activity, List<RecentLocation> sideMenuItems, String loc,RecentLocation recentLocation) {
        this.activity = activity;
        this.Items = sideMenuItems;
        mInflater = LayoutInflater.from(activity);
        this.filterList=sideMenuItems;
        this.selectedLocation=loc;
        this.recentSelectedLocation=recentLocation;
        if(source== C.HOME ||source== C.FRAGMENT_PRODUCT_LISTING ) {
            if (loc != null) {
                lastCheckedPosition =loc;
            }
            else if(recentLocation!=null){
                lastCheckedPosition = recentLocation.getAddress();

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
    public RecentLocation getItem(int position) {
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
        RecentLocation model=Items.get(position);

        initializeViews(model, holder, position);

        return convertView;

    }
    public RecentLocation getSelectedItem() {
        if(pos!=-1) {
            return filterList.get(pos);
        }
        return null;
    }


    private void initializeViews(final RecentLocation model, final ItemViewHolder holder, final int position) {

        ((ItemViewHolder)holder).radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityContainer)activity).saveLocation(getItem(position));
                lastCheckedPosition = model.getAddress();
                pos=position;
                notifyDataSetChanged();
            }
        });
        ((ItemViewHolder)holder).rlStoreItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ActivityContainer)activity).saveLocation(getItem(position));
                lastCheckedPosition = model.getAddress();
                pos=position;
                notifyDataSetChanged();

            }
        });
        ((ItemViewHolder)holder).name.setText(model.getAddress());
        if (model.getAddress()!=null &&model.getAddress() .equals( lastCheckedPosition)){
            pos=position;
            ((ItemViewHolder)holder).radioButton.setChecked(true);
            ((ItemViewHolder)holder).name.setTextColor(ContextCompat.getColor(activity, R.color.profile_text_color));

        }else{
            ((ItemViewHolder)holder).radioButton.setChecked(false);
            ((ItemViewHolder)holder).name.setTextColor(ContextCompat.getColor(activity, R.color.profile_text_color));

        }
       /* ((ItemViewHolder)holder).radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        RecentLocation recentLocation=new RecentLocation();
                        recentLocation.setLatitude(horizontalList.get(position).getLatitude());
                        recentLocation.setLongitude(horizontalList.get(position).getLongitude());
                        recentLocation.setAddress(horizontalList.get(position).getAddress());

                        ((ActivityContainer)context).saveLocation(recentLocation);
                    }
                }
            });*/

        if(pos!=-1) {
            SharedPreference.getInstance(activity).setLocation(C.SELECTED_LOCATION, filterList.get(pos));
        }
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

          List<RecentLocation> filters=new ArrayList<RecentLocation>();

            //get specific items
            for(int i=0;i<filterList.size();i++)
            {
                if(filterList.get(i).getAddress().toLowerCase().contains(constraint))
                {
                    RecentLocation p=new RecentLocation( filterList.get(i).getId(),filterList.get(i).getAddress());

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

        Items=(ArrayList<RecentLocation>) results.values;
        notifyDataSetChanged();
    }

    }
}
