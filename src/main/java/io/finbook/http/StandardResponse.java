package io.finbook.http;

import com.google.gson.JsonElement;

import java.util.Map;

public class StandardResponse {

    private Map data;
    private String view;

    public StandardResponse(Map data, String view) {
        this.data = data;
        this.view = view;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
