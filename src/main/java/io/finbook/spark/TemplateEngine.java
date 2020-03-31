package io.finbook.spark;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import io.finbook.view.DataAndView;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import spark.ModelAndView;

import java.io.File;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Properties;

public class TemplateEngine {

    protected VelocityEngine instance;
    protected Template templateCompiler = null;
    protected String templatesFolder = "/template";
    protected StringWriter writer = new StringWriter();

    //ModelAndView p = new ModelAndView

    public TemplateEngine() {
        /*this.instance = new VelocityEngine();
        this.instance.setProperty("runtime.references.strict", true);
        */
        Properties properties = new Properties();
        properties.setProperty("resource.loader", "class");
        properties.setProperty(
                "class.resource.loader.class",
                "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        this.instance = new VelocityEngine(properties);
    }

    public void setTemplate(String template) {
        templateCompiler = instance.getTemplate(templatesFolder + File.separatorChar + template + getTemplateExtension());
    }

    public String getTemplateExtension() {
        return ".vm";
    }

    private VelocityContext parseJsonElementToVelocityContext (JsonElement data){
        Type type = new TypeToken<Map<String, Object>>(){}.getType();
        Map<String, Object> map = new Gson().fromJson(data, type);
        return new VelocityContext(map);
    }

    public String render (DataAndView dataAndView){
        instance.init();
        setTemplate(dataAndView.getViewName());
        // VelocityContext context = parseJsonElementToVelocityContext(dataAndView.getData());
        VelocityContext context = new VelocityContext();
        context.put("firstName", "Xiomara");
        templateCompiler.merge(context, writer);
        return writer.toString();
    }

}
