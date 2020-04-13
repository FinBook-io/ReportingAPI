package io.finbook.command;

import io.finbook.http.MyResponse;
import io.finbook.http.StandardResponse;
import io.finbook.sparkcontroller.ResponseCreator;

import java.util.HashMap;

public class HomeCommand {

    public static ResponseCreator index(boolean isLogged) {
        if (isLogged){
            HashMap<String, Object> data = new HashMap<>();
            data.put("logged", true);
            return MyResponse.ok(
                    new StandardResponse(data, "home/index")
            );
        }else {
            return MyResponse.ok(
                    new StandardResponse(null, "home/index")
            );
        }
    }

}
