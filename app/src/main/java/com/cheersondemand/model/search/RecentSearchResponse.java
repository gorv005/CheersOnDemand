package com.cheersondemand.model.search;

import com.cheersondemand.model.Categories;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GAURAV on 7/8/2018.
 */

public class RecentSearchResponse {
    @SerializedName("recent_search")
    @Expose
    private List<RecentSearch> recentSearch = null;
    @SerializedName("categories")
    @Expose
    private List<Categories> categories = null;

    public List<RecentSearch> getRecentSearch() {
        return recentSearch;
    }

    public void setRecentSearch(List<RecentSearch> recentSearch) {
        this.recentSearch = recentSearch;
    }

    public List<Categories> getCategories() {
        return categories;
    }

    public void setCategories(List<Categories> categories) {
        this.categories = categories;
    }

}
