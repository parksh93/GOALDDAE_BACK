package com.goalddae.service;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Service
public class KakaoMapService {
    private static String GEOCODE_URL="http://dapi.kakao.com/v2/local/search/address.json?query=";
    @Value("${kakaoDevelop.restApiKey}")
    private String restAPIKey;

    public Map<String, String> getXY(String address) {
        address = URLEncoder.encode(address);
        Map xyMap = new HashMap<>();

        try {
            URL url = new URL(GEOCODE_URL + address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "KakaoAK " + restAPIKey);
            con.setRequestProperty("content-type", "application/json");
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);

            Charset charset = Charset.forName("UTF-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));

            StringBuffer response = new StringBuffer();
            response.append(in.readLine());

            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(response.toString());
            JSONArray documents = (JSONArray) object.get("documents");
            object = (JSONObject) documents.get(0);
            String x = (String) object.get("x");
            String y = (String) object.get("y");

            xyMap.put("x", x.substring(0, x.indexOf(".")));
            xyMap.put("y", y.substring(0, y.indexOf(".")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xyMap;
    }
}
