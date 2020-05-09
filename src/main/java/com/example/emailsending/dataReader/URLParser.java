package com.example.emailsending.dataReader;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class URLParser {

    private static final Logger logger = LoggerFactory.getLogger(URLParser.class);

    /**
     *
     * @param url
     * @return String of JSON data
     * @throws JSONException
     */
    private String sendGetRequest(String url) throws JSONException {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(15, TimeUnit.SECONDS);
        client.setReadTimeout(15, TimeUnit.SECONDS);
        Request request = new Request.Builder().url(url).build();
        logger.info("request created");
        try {
            Response response = client.newCall(request).execute();
            logger.info("request executed");
            String rawData = response.body().string();
            return rawData;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("error: " + e);
            return "Error: " + e;
        }
    }

    /**
     *
     * @param url
     * @return JSONObject with stock data in time series
     * @throws JSONException
     * @throws ParseException
     */
    public JSONObject extractData(String url) throws JSONException, ParseException {
        String rawData = sendGetRequest(url);
        logger.info("parsing raw data into Json object");
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(rawData);
        logger.info("parsed successfully");
        return jsonObject;
    }
}
