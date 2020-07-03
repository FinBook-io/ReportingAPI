package io.finbook.command;

import io.finbook.responses.MyResponse;
import io.finbook.responses.StandardResponse;
import io.finbook.sparkcontroller.ResponseCreator;
import io.finbook.util.Path;

import java.util.HashMap;

public class HomeCommand {

    public static ResponseCreator index(boolean isLogged) {
        if (isLogged){
            HashMap<String, Object> data = new HashMap<>();
            data.put("logged", true);
            return MyResponse.ok(
                    new StandardResponse(data, Path.Template.HOME_INDEX)
            );
        }else {
            return MyResponse.ok(
                    new StandardResponse(null, Path.Template.HOME_INDEX)
            );
        }
    }

}
