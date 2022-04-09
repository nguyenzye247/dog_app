package eu.tutorial.dogappdb.model;

import com.google.gson.annotations.SerializedName;

public class Height {
    @SerializedName("imperial")
    private String hImperial;
    @SerializedName("metric")
    private String hMetric;

    public Height(String hImperial, String hMetric) {
        this.hImperial = hImperial;
        this.hMetric = hMetric;
    }

    public String getImperial() {
        return hImperial;
    }

    public void setImperial(String hImperial) {
        this.hImperial = hImperial;
    }

    public String getMetric() {
        return hMetric;
    }

    public void setMetric(String hMetric) {
        this.hMetric = hMetric;
    }
}
