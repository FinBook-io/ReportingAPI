package io.finbook.chart;

import com.google.gson.JsonObject;
import org.json.JSONObject;

public abstract class Chart {

    private String type;
    private ChartData data;

    public Chart(ChartType type) {
        this.type = type.getLabel();
        data = new ChartData();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ChartData getData() {
        return data;
    }

    public void setData(ChartData data) {
        this.data = data;
    }

    public JSONObject toJSON() {
        JSONObject objectToJson = new JSONObject();
        objectToJson.put("type", getType());
        objectToJson.put("data", getData().toJSON());
        return objectToJson;
    }

}
