package com.cheersondemand.util;

import android.content.Context;

import com.cheersondemand.model.AllProduct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by AB on 8/29/2018.
 */

public class StoreProducts {
    private Context context;
    public  static  StoreProducts storeProducts;
    HashMap<Integer,AllProduct> products = new HashMap<Integer,AllProduct> ();

    private StoreProducts(){}  //private constructor.

    public static StoreProducts getInstance(){
        if (storeProducts == null){ //if there is no instance available... create new one
            storeProducts = new StoreProducts();
        }

        return storeProducts;
    }

   public void  saveProducts(List<AllProduct> productList){
        if(productList!=null) {
            for (int i = 0; i < productList.size(); i++) {
                products.put(productList.get(i).getId(), productList.get(i));
            }
        }
    }

    public List<AllProduct> getListOfProducts(){
         List<AllProduct> list = new ArrayList<AllProduct>(products.values());
        return list;
    }

    public  AllProduct getProduct(Integer id){
       return products.get(id);
    }

    public void  addProduct(AllProduct allProduct){
        if(allProduct!=null) {
            products.put(allProduct.getId(), allProduct);
        }
    }
    public void clear()
    {
        storeProducts = null;
    }
}
