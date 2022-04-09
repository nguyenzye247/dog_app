package eu.tutorial.dogappdb.model;

import com.google.gson.annotations.SerializedName;

public class Weight {
    @SerializedName("imperial")
    private String wImperial;
    @SerializedName("metric")
    private String wMetric;

    public Weight(String imperial, String metric) {
        this.wImperial = imperial;
        this.wMetric = metric;
    }

    public String getImperial() {
        return wImperial;
    }

    public void setImperial(String imperial) {
        this.wImperial = imperial;
    }

    public String getMetric() {
        return wMetric;
    }

    public void setMetric(String metric) {
        this.wMetric = metric;
    }
}
