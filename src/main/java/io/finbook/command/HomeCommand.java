package io.finbook.command;

import io.finbook.responses.CustomResponse;
import io.finbook.responses.ResponseStructure;
import io.finbook.sparkcontroller.ResponseCreator;
import io.finbook.util.Path;

import java.util.HashMap;

public class HomeCommand {

    public static ResponseCreator index(boolean isLogged) {
        if (isLogged){
            HashMap<String, Object> data = new HashMap<>();
            data.put("logged", true);
            return CustomResponse.ok(
                    new ResponseStructure(data, Path.Template.HOME_INDEX)
            );
        }else {
            return CustomResponse.ok(
                    new ResponseStructure(null, Path.Template.HOME_INDEX)
            );
        }
    }

}
