package com.goalddae.service;

import com.goalddae.dto.weather.GetWeatherDTO;
import com.goalddae.dto.weather.WeatherDTO;
import com.goalddae.exception.NotFoundWeatherException;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class WeatherServiceImpl implements WeatherService {
    @Value("${weather.secretKey}")
    private String secretKey;

    private final static String API_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";

    @Override
    public WeatherDTO getWeatherApi(GetWeatherDTO getWeatherDTO, Map<String, String> xyMap) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(API_URL);
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String baseDate = simpleDateFormat.format(nowDate);
        int nowHour = LocalDateTime.now().getHour();
        String baseTime = getBaseTime(nowHour);
        String fcstTime = getFcstTime(nowHour);

        urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + secretKey);
        urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(xyMap.get("y"), "UTF-8")); //경도
        urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(xyMap.get("x"), "UTF-8")); //위도
        urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(baseDate, "UTF-8")); /* 조회하고싶은 날짜*/
        urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(baseTime, "UTF-8")); /* 조회하고싶은 시간 AM 02시부터 3시간 단위 */
        urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("json", "UTF-8"));    /* 타입 */
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("72", "UTF-8"));

        URL url = new URL(urlBuilder.toString());

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            System.out.println("정상작동");
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            System.out.println("에러 : " + conn.getResponseCode());
            throw new NotFoundWeatherException("날씨를 가져오는데 실패했습니다.");
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }

        rd.close();
        conn.disconnect();

        JSONArray itemArr = jsonParseWeatherResponse(sb.toString());

        WeatherDTO weatherDTO = saveWeather(itemArr, fcstTime, getWeatherDTO.getCity());

        return weatherDTO;
    }

    public String getBaseTime(int nowHour) {
        String baseTime;
        int nowMinute = LocalDateTime.now().getMinute();
        int hour = 0;

        if(nowMinute < 45){
            hour = nowHour - 1;
        }else{
            hour = nowHour;
        }

        if(hour < 10){
            baseTime = "0" + hour +"30";
        }else{
            baseTime = hour + "30";
        }

        return baseTime;
    }

    public String getFcstTime(int nowHour){
        int nextNowHour = nowHour + 1;
        String fcstTime;

        if(nextNowHour < 10){
            fcstTime = "0" + nextNowHour +"00";
        }else{
            fcstTime = nextNowHour + "00";
        }

        return fcstTime;
    }

    public JSONArray jsonParseWeatherResponse(String repsonse){
        try{
            JSONParser parser = new JSONParser();
            JSONObject object = (JSONObject) parser.parse(repsonse);
            JSONObject response = (JSONObject) object.get("response");
            JSONObject body = (JSONObject) response.get("body");
            JSONObject items = (JSONObject) body.get("items");
            JSONArray itemArr = (JSONArray) items.get("item");

            return itemArr;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public WeatherDTO saveWeather(JSONArray itemArr, String fcstTime, String city){
        WeatherDTO weatherDTO = new WeatherDTO();

        for (int i = 0; i < itemArr.size(); i++) {
            JSONObject item = (JSONObject) itemArr.get(i);
            String category = (String) item.get("category");
            if(item.get("fcstTime").equals(fcstTime)){
                if(category.equals("T1H")){
                    weatherDTO.setTemperature((String)item.get("fcstValue"));
                }else if(category.equals("RN1")){
                    weatherDTO.setPrecipitation((String)item.get("fcstValue"));
                }else if(category.equals("SKY")){
                    weatherDTO.setSky((String)item.get("fcstValue"));
                }
//                else if(category.equals("VEC")){
//                    weatherDTO.setWindDirection((String)item.get("fcstValue"));
//                }else if(category.equals("WSD")){
//                    weatherDTO.setWindSpeed((String)item.get("fcstValue"));
//                }
            }
        }

        weatherDTO.setCity(city);
        weatherDTO.setFcstTime(fcstTime);

        return weatherDTO;
    }
}
