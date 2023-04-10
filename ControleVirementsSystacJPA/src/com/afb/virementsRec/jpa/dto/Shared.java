package com.afb.virementsRec.jpa.dto;

import java.io.IOException;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jackson.io.JsonStringEncoder;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Shared {
	
	private static final int TIMEOUT = 100;
	private static final ObjectMapper mapper = new ObjectMapper();
	private static JsonStringEncoder jsonEncoder;
	
	
	public static CloseableHttpClient getClosableHttpClient() {
		
		RequestConfig config = RequestConfig.custom()
		  .setConnectTimeout(TIMEOUT * 20000)
		  .setConnectionRequestTimeout(TIMEOUT * 20000)
		  .setSocketTimeout(TIMEOUT * 20000).build();
		CloseableHttpClient client = 
		  HttpClientBuilder.create().setDefaultRequestConfig(config).build();
		
		return client;
	}
	
	public static boolean isJSONValid(String jsonInString ) {
	    try {
	       mapper.readTree(jsonInString);
	       return true;
	    } catch (IOException e) {
	       return false;
	    }
	}
	
	private static String encodeJson(String jsonString) {
		return new String(jsonEncoder.quoteAsString(jsonString));
	}
	
	public static String mapToJsonString(Object obj) throws JsonGenerationException, JsonMappingException, IOException, JSONException{
		     ObjectMapper mapper = new ObjectMapper();
			String map = null;
			try {
			     map = mapper.writeValueAsString(obj);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return map;
	}
	
	public static String removeWhitespaces(String json) {
	    boolean quoted = false;

	    StringBuilder builder = new StringBuilder();

	    int len = json.length();
	    for (int i = 0; i < len; i++) {
	        char c = json.charAt(i);
	        if (c == '\"')
	            quoted = !quoted;

	        if (quoted || !Character.isWhitespace(c))
	            builder.append(c);
	    }

	    return builder.toString();
	}
	
	
	public static String mapToString(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		String o = mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(obj.toString(), String.class);
		return o;
	}
	
	public static bkeve mapToBkeve(JSONObject obj) throws JsonParseException, JsonMappingException, IOException, JSONException {
		bkeve o = mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(obj.toString(), bkeve.class);
		return o;
	}
	
	public static <T> T mapToObject(JSONObject obj, Class<T> contentClass) throws JsonParseException, JsonMappingException, IOException, JSONException {
		T o = (T) mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY).readValue(obj.toString(), contentClass);
		return o;
	}
	
}
