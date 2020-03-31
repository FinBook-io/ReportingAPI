package io.finbook.view;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class DataAndView {

    private JsonElement data;
    private String viewName;

    public DataAndView(JsonElement data, String viewName) {
        super();
        this.data = data;
        this.viewName = viewName;
    }

    public JsonElement getData() {
        return data;
    }

    public String getViewName() {
        return viewName;
    }

}
