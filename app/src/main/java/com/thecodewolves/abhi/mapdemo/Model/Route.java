package com.thecodewolves.abhi.mapdemo.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Abhi on 22-05-2016.
 */
public class Route {
    @SerializedName("overview_polyline")
    @Expose
    public OverviewPolyline overviewPolyline;
    @SerializedName("summary")
    @Expose
    public String summary;

    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
