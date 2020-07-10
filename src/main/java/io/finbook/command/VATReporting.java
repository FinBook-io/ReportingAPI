package io.finbook.command;

import io.finbook.responses.CustomResponse;
import io.finbook.responses.ResponseStructure;
import io.finbook.sparkcontroller.ResponseCreator;
import io.finbook.util.Path;

public class VATReporting {

	public static ResponseCreator index() {
		return CustomResponse.ok(
				new ResponseStructure(null, Path.Template.ADMIN_VAT_RETURNS_INDEX)
		);
	}

}
