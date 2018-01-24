package com.fbee.modules.basic;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.ObjectMapper;


public class JiacerMappingJacksonHttpMessageConverter extends ObjectMapper{
    public JiacerMappingJacksonHttpMessageConverter() {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //format.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.setDateFormat(format);
    }
}
