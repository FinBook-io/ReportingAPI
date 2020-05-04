package io.finbook.chart;

import com.google.gson.JsonObject;
import org.json.JSONArray;
import org.json.JSONObject;

public class ChartData {

    private JSONArray labels;
    private JSONArray datasets;

    public ChartData() {
        labels = new JSONArray();
        datasets = new JSONArray();
    }

    public JSONArray getLabels() {
        return labels;
    }

    public void setLabels(JSONArray labels) {
        this.labels = labels;
    }

    public JSONArray getDatasets() {
        return datasets;
    }

    public void setDatasets(JSONArray datasets) {
        this.datasets = datasets;
    }

    public void addOneLabel(String label){
        labels.put(label);
    }

    public void addDataset(String label, String backgroundColor, JSONArray data){
        JSONObject dataset = new JSONObject();
        dataset.put("label", label);
        dataset.put("backgroundColor", backgroundColor);
        dataset.put("data", data);
        datasets.put(dataset);
    }

    public JSONObject toJSON() {
        JSONObject objectToJson = new JSONObject();
        objectToJson.put("labels", getLabels());
        objectToJson.put("datasets", getDatasets());
        return objectToJson;
    }

}
