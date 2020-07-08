package io.finbook.util;

import org.json.JSONObject;

public class JSONParser {

	private JSONObject jsonObject;

	public JSONParser(String json) {
		this.jsonObject = new JSONObject(json);
	}

	public byte[] getByteArray(String key) {
		String[] stringArray = splitByCommas(jsonObject.get(key).toString());
		return toByteArray(stringArray);
	}

	private String[] splitByCommas(String text) {
		return removeBrackets(text).split(",");
	}

	private String removeBrackets(String text) {
		return text.substring(1, text.length() - 1);
	}

	private byte[] toByteArray(String[] stringArray) {
		byte[] byteArray = new byte[stringArray.length];
		for (int i = 0; i < byteArray.length; i++) {
			byteArray[i] = Byte.parseByte(stringArray[i]);
		}
		return byteArray;
	}

}
