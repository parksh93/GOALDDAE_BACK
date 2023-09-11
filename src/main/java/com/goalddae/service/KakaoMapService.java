package com.goalddae.service;

import com.goalddae.entity.User;
import com.goalddae.repository.UserJPARepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import retrofit2.http.Url;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

@Service
@PropertySource("classpath:application-kakaoDevelop.properties")
public class KakaoMapService {
    private static String GEOCODE_URL="http://dapi.kakao.com/v2/local/search/address.json?query=";
    @Value("${restApiKey}")
    private String restAPIKey;

    private final UserJPARepository userJPARepository;

    @Autowired
    public KakaoMapService(UserJPARepository userJPARepository){
        this.userJPARepository = userJPARepository;
    }

    public Map<String, String> getXY(long userId) {
        Map xyMap = new HashMap<>();
        User user = new User();

        if (userId != 0) {
            user = userJPARepository.findById(userId).get();
        }

        try {
            String address = "";
            if (!user.getPreferredCity().equals("")) {
                address = URLEncoder.encode(user.getPreferredCity());
            } else {
                address = URLEncoder.encode("서울특별시");
            }

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
            System.out.println("x : " + xyMap.get("x"));
            System.out.println("y : " + xyMap.get("y"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xyMap;
    }
}
