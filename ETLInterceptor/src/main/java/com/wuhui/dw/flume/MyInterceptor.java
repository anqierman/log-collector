package com.wuhui.dw.flume;

import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyInterceptor implements Interceptor {
    private  List<Event> results = new  ArrayList();
    private String startFlag ="\"en\":\"start\"";
    public void initialize() {

    }

    public Event intercept(Event event) {
        byte[] body = event.getBody();
        Map<String, String> headers = event.getHeaders();
        String bodyStr = new String(body,Charset.forName("utf-8"));
        boolean flage = true;
        if(bodyStr.contains(startFlag)){
             flage = ETLUtil.validStartLog(bodyStr);
            headers.put("topic","topic_start");
        }else{
            headers.put("topic","topic_event");
            flage =ETLUtil.validEventLog(bodyStr);
        }
        if(flage == false){
            return  null;
        }
        return  event;
    }

    public List<Event> intercept(List<Event> events) {
        results.clear();
        for (Event event : events) {
            Event intercept = intercept(event);
            if (intercept !=null){
               results.add(event);
            }
        }
        return results;
    }

    public void close() {

    }


    public static class Builder implements org.apache.flume.interceptor.Interceptor.Builder {


        public void configure(Context context) {

        }

        public Interceptor build() {
            return new MyInterceptor();
        }


    }
}



