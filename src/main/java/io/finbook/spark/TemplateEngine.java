package io.finbook.spark;

import io.finbook.http.StandardResponse;
import org.apache.velocity.Template;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class TemplateEngine {

    protected VelocityTemplateEngine instance;
    protected Template templateCompiler = null;
    protected String templatesFolder = "template";
    protected String templatesExtension = ".vm";
    protected StringWriter writer = new StringWriter();

    public TemplateEngine() {
        instance = new VelocityTemplateEngine();
    }

    public String getTemplateURL(String template){
        return templatesFolder + File.separatorChar + template + templatesExtension;
    }
    public String render (StandardResponse response){
        Map model = response.getData() == null ? new HashMap<>() : response.getData();
        return instance.render(new ModelAndView(model, getTemplateURL(response.getView())));
    }

}
