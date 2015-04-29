package com.foya.noms.util;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

public class JsonDateDeSerializer extends JsonDeserializer<Date> {
	public static final SimpleDateFormat format = new SimpleDateFormat(
			"yyyy/MM/dd");

	 @Override
	    public Date deserialize(JsonParser jp, DeserializationContext ctxt)
	            throws IOException, JsonProcessingException {
	     
	        try {
				return format.parse(jp.getText());
			} catch (ParseException e) {
			
				e.printStackTrace();
			}
	        return null;
	    }

}
