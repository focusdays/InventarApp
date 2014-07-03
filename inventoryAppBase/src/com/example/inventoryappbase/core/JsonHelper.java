package com.example.inventoryappbase.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHelper {

	public JsonHelper() {
		super();
	}
	public JSONObject getJSON(String s) throws JSONException {
		return new JSONObject(s);
	}
	public JSONArray evalArrayExpression(JSONObject json, String  path) throws JSONException {
		return (JSONArray)this.evalExpression(json, path);
	}
	public JSONObject evalObjectExpression(JSONObject json, String  path) throws JSONException {
		return (JSONObject)this.evalExpression(json, path);
	}

	public Object evalExpression(JSONObject json, String  path) throws JSONException {
		String[] paths = path.split("[\\.]");
		Object elem = json;
		Pattern pattern = Pattern.compile("([a-zA-Z_][a-zA-Z0-9_]*)(?:\\[([0-9]+|\\\".+\\\")\\])?");
		for (String p : paths) {
			Matcher matcher = pattern.matcher(p);
			if (!matcher.matches()) {
				return null;
			} else if (matcher.groupCount() == 2) {
				String attr = matcher.group(1);
				String indexStr = matcher.group(2);
				if (indexStr == null) {
					/* access attribut only */
					elem = ((JSONObject)elem).opt(attr);
				} else if (indexStr.startsWith("\"")) {
					/* access index attribut mapped attribute */
					String indexAttr = indexStr.substring(1, indexStr.length()-2);
					elem = ((JSONObject)elem).getJSONObject(attr);
					elem = ((JSONObject)elem).opt(indexAttr);
				} else {
					/* access index attribut */
					int index = Integer.parseInt(indexStr);
					elem = ((JSONObject)elem).getJSONArray(attr).opt(index);
				}
			} else {
				String indexStr = matcher.group(2);
			}
		}
		if (elem instanceof String) {
			elem = StringEscapeUtils.unescapeHtml4(elem.toString());
		}
		return elem;
	}
}
