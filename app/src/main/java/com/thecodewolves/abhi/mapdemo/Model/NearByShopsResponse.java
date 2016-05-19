package com.thecodewolves.abhi.mapdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Abhi on 17-05-2016.
 */
public class NearByShopsResponse {
    @SerializedName("results")
    @Expose
    private List<Shop> results;

    @SerializedName("status")
    @Expose
    private String status;

    public List<Shop> getResults() {
        return results;
    }

    public void setResults(List<Shop> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
