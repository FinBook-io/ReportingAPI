package io.finbook.sparkcontroller;

import io.finbook.responses.ResponseStructure;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TemplateEngine {

    protected VelocityTemplateEngine instance;
    protected String templatesFolder = "template";
    protected String templatesExtension = ".vm";

    public TemplateEngine() {
        instance = new VelocityTemplateEngine();
    }

    public String getTemplateURL(String template){
        return templatesFolder + File.separatorChar + template + templatesExtension;
    }

    public String render (ResponseStructure response){
        Map model = response.getData() == null ? new HashMap<>() : response.getData();
        return instance.render(new ModelAndView(model, getTemplateURL(response.getView())));
    }

}


