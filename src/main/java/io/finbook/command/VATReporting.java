package io.finbook.command;

import io.finbook.responses.MyResponse;
import io.finbook.responses.StandardResponse;
import io.finbook.sparkcontroller.ResponseCreator;
import io.finbook.util.Path;

public class VATReporting {

	public static ResponseCreator index() {
		return MyResponse.ok(
				new StandardResponse(null, Path.Template.ADMIN_VAT_RETURNS_INDEX)
		);
	}

}
