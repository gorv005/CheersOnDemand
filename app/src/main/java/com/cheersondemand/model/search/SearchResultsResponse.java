package com.cheersondemand.model.search;

import com.cheersondemand.model.Errors;
import com.cheersondemand.model.productdescription.Meta;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by GAURAV on 7/8/2018.
 */

public class SearchResultsResponse {
    @SerializedName("success")
    @Expose
    private Boolean success;
  /*  @SerializedName("message")
    @Expose
    private Message message;*/
    @SerializedName("data")
    @Expose
    private SearchResults data;
    @SerializedName("meta")
    @Expose
    private List<Meta> meta = null;
    @SerializedName("errors")
    @Expose
    private List<Errors> errors = null;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

   /* public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }*/

    public SearchResults getData() {
        return data;
    }

    public void setData(SearchResults data) {
        this.data = data;
    }

    public List<Meta> getMeta() {
        return meta;
    }

    public void setMeta(List<Meta> meta) {
        this.meta = meta;
    }

    public List<Errors> getErrors() {
        return errors;
    }

    public void setErrors(List<Errors> errors) {
        this.errors = errors;
    }
}
