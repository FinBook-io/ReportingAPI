package io.finbook.file;

import io.finbook.responses.MyResponse;
import io.finbook.responses.StandardResponse;
import io.finbook.sparkcontroller.ResponseCreator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;

public class XMLCommand {

	public XMLCommand() {

	}

	public static ResponseCreator init() {

		String xmlFile = "PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+DQo8Y2ZkaTpWb3VjaGVyIENvbmNlcHQ9Ik1vcnRnYWdlIGZvciBjb21wYW55LiIgQ3VycmVuY3k9ImV1cm8iIERhdGU9IjIwMjAtMDUtMzBUMTQ6NDA6NDUiIExvY2F0aW9uPSIzNzE4NCINCiAgICAgICAgICAgICAgU3ViVG90YWw9IjEwMDAuMCIgVGF4UmF0ZT0iMC4wMSIgVG90YWw9IjEwMTAuMCIgVHlwZT0iaW5jb21lIiBVVUlEPSI0MDAwODExIiBVc2U9IkkwMyI+DQogICAgPGNmZGk6SXNzdWVyIElzc3Vlck5hbWU9IkJpb2xpZmUgR3J1cCIgSXNzdWVyUkZDPSIyMDAwMDAwIi8+DQogICAgPGNmZGk6UmVjZWl2ZXIgUmVjZWl2ZXJOYW1lPSJCYW5rIiBSZWNlaXZlclJGQz0iOTk5OTk5Ii8+DQo8L2NmZGk6Vm91Y2hlcj4=";

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("C:/src/reporting/src/main/resources/public/finbook/files/testingXML.xml");
			fos.write(Base64.getDecoder().decode(xmlFile));
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return MyResponse.ok(
				new StandardResponse(null, "home/index")
		);
	}

}
