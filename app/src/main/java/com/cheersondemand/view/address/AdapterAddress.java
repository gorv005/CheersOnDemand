package com.cheersondemand.view.address;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.cheersondemand.R;
import com.cheersondemand.model.address.Address;
import com.cheersondemand.model.location.RecentLocation;
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
    private String lastCheckedPosition = null;
    int pos;
    Activity context;
    ImageLoader imageLoader;
public class ItemViewHolder extends RecyclerView.ViewHolder {
    public TextView tvName,tvSubAddress;
    public RadioButton radioButton;

    View llEdit,llDelete,llAddressItem,llModify;
    public ItemViewHolder(View view) {
        super(view);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvSubAddress = (TextView) view.findViewById(R.id.tvSubAddress);
        llEdit = (View) view.findViewById(R.id.llEdit);
        llDelete = (View) view.findViewById(R.id.llDelete);
        radioButton = (RadioButton) view.findViewById(R.id.radio);
        llAddressItem = (View) view.findViewById(R.id.llAddressItem);
        llModify = (View) view.findViewById(R.id.llModify);

    }
}

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        View rlAddNew;
        public FooterViewHolder(View view) {
            super(view);
            rlAddNew = (View) view.findViewById(R.id.rlAddNew);
        }
    }
    public AdapterAddress(List<Address> horizontalList, Activity context,String address) {
        this.horizontalList = horizontalList;

        this.context=context;
        imageLoader=new ImageLoader(context);
        if(address!=null){
            lastCheckedPosition=address;
        }

    }

    public Address getSelectedAddress() {
        if(pos!=-1) {
            return horizontalList.get(pos);
        }
        return null;
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
            /*itemViewHolder.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

            if (horizontalList.get(position).getAddress() .equals( lastCheckedPosition)){
                pos=position;
                itemViewHolder.radioButton.setChecked(true);
                itemViewHolder.llModify.setVisibility(View.VISIBLE);
            }else{
                ((ItemViewHolder)holder).radioButton.setChecked(false);
                itemViewHolder.llModify.setVisibility(View.GONE);

            }

            itemViewHolder.tvName.setText(horizontalList.get(position).getName());
            itemViewHolder.tvSubAddress.setText(horizontalList.get(position).getFlatNo()+" "+horizontalList.get(position).getAddress()
            + " "+horizontalList.get(position).getZipCode());

            itemViewHolder.llEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.finish();
                    Intent intent=new Intent(context, ActivityContainer.class);
                    Bundle bundle=new Bundle();
                    bundle.putBoolean(C.IS_EDIT,true);
                    bundle.putBoolean(C.IS_FROM_CHECKOUT, false);
                    bundle.putBoolean(C.IS_RETRY_PAYEMNT, false);

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
                    //((ActivityContainer)context).removeAddress(horizontalList.get(position),position);
                    dialog(position);
                }
            });
            itemViewHolder.radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecentLocation recentLocation=new RecentLocation();
                    recentLocation.setLatitude(horizontalList.get(position).getLatitude());
                    recentLocation.setLongitude(horizontalList.get(position).getLongitude());
                    recentLocation.setAddress(horizontalList.get(position).getAddress());
                    ((ActivityContainer)context).saveLocation(recentLocation);
                    lastCheckedPosition = horizontalList.get(position).getAddress();
                    pos=position;
                    notifyDataSetChanged();

                }
            });
            itemViewHolder.llAddressItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RecentLocation recentLocation=new RecentLocation();
                    recentLocation.setLatitude(horizontalList.get(position).getLatitude());
                    recentLocation.setLongitude(horizontalList.get(position).getLongitude());
                    recentLocation.setAddress(horizontalList.get(position).getAddress());
                    ((ActivityContainer)context).saveLocation(recentLocation);
                    lastCheckedPosition = horizontalList.get(position).getAddress();
                    pos=position;
                    notifyDataSetChanged();
                }
            });
        }
        else if (holder instanceof FooterViewHolder) {
            final FooterViewHolder footerViewHolder = (FooterViewHolder) holder;
            footerViewHolder.rlAddNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActivityContainer)context).addNewAddress();


                   /* Intent intent=new Intent(context, ActivityContainer.class);
                    Bundle bundle=new Bundle();
                    bundle.putBoolean(C.IS_EDIT,false);
                    bundle.putBoolean(C.IS_RETRY_PAYEMNT, false);
                    bundle.putBoolean(C.IS_FROM_CHECKOUT, false);

                    intent.putExtra(C.BUNDLE,bundle);
                    intent.putExtra(C.FRAGMENT_ACTION,C.FRAGMENT_ADD_ADDRESS);
                    context.startActivity(intent);
                    */

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
    void dialog(final int position) {
        final Dialog dialog = new Dialog(context, R.style.FullHeightDialog); //this is a reference to the style above
        dialog.setContentView(R.layout.dialog); //I saved the xml file above as yesnomessage.xml
        dialog.setCancelable(true);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//to set the message
        TextView title = (TextView) dialog.findViewById(R.id.tvmessagedialogtitle);

        TextView message = (TextView) dialog.findViewById(R.id.tvmessagedialogtext);
        title.setText(context.getString(R.string.delete_address));
        message.setText(context.getString(R.string.are_you_sure_delete_address));
//add some action to the buttons
        Button yes = (Button) dialog.findViewById(R.id.bmessageDialogYes);
        yes.setText(context.getString(R.string.yes_delete));
        yes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                dialog.dismiss();
                ((ActivityContainer)context).removeAddress(horizontalList.get(position),position);


            }
        });

        Button no = (Button) dialog.findViewById(R.id.bmessageDialogNo);
        no.setText(context.getString(R.string.cancel));
        no.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
