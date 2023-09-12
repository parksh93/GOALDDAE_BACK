package com.goalddae.service;

import com.goalddae.Citys;
import com.goalddae.dto.weather.GetWeatherDTO;
import com.goalddae.dto.weather.WeatherDTO;
import com.goalddae.entity.User;
import com.goalddae.entity.Weather;
import com.goalddae.repository.WeatherRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
@PropertySource("classpath:application-weather.properties")
@PropertySource("classpath:application-kakaoDevelop.properties")
public class WeatherServiceImpl implements WeatherService {
    @Value("${secretKey}")
    private String secretKey;
    @Value("${restApiKey}")
    private String restAPIKey;

    private static String GEOCODE_URL="http://dapi.kakao.com/v2/local/search/address.json?query=";
    private final static String API_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";

    private WeatherRepository weatherRepository;

    @Autowired
    public WeatherServiceImpl(WeatherRepository weatherRepository){
        this.weatherRepository = weatherRepository;
    }

    @Override
//    @Scheduled(cron = "0 45 * * * *")
    public void saveWeatherApi() throws Exception {
        StringBuilder urlBuilder = new StringBuilder(API_URL);
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String baseDate = simpleDateFormat.format(nowDate);
        int nowHour = LocalDateTime.now().getHour();
        String baseTime = getBaseTime(nowHour);
        String fcstTime = getFcstTime(nowHour);

        Citys[] citys = Citys.values();

        for (Citys city : citys) {
            Map<String, String> xyMap = getXY(city.getCity());
            System.out.println("지역 : " + city.getCity());
            System.out.println("baseDate : " + baseDate);
            System.out.println("baseTime : " + baseTime);
            System.out.println("fcstTime : " + fcstTime);

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
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            rd.close();
            conn.disconnect();

            System.out.println(sb.toString());
            JSONArray itemArr = jsonParseWeatherResponse(sb.toString());

            saveWeather(itemArr, fcstTime, city.getCity());
        }

        String prevFcstTime = getFcstTime(nowHour - 1);
        weatherRepository.deleteByFcstTime(prevFcstTime);
    }

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
            System.out.println("x : " + xyMap.get("x"));
            System.out.println("y : " + xyMap.get("y"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xyMap;
    }

    public String getBaseTime(int nowHour) {
        String baseTime;
//        int nowMinute = LocalDateTime.now().getMinute();
//        int prevHour = 0;

//        if(nowMinute < 45){
//            prevHour = nowHour - 1;
//        }else{
//            prevHour = nowHour;
//        }

        nowHour -= 1;

        if(nowHour < 10){
            baseTime = "0" + nowHour +"30";
        }else{
            baseTime = nowHour + "30";
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

    public void saveWeather(JSONArray itemArr, String fcstTime, String city){
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
                }else if(category.equals("VEC")){
                    weatherDTO.setWindDirection((String)item.get("fcstValue"));
                }else if(category.equals("WSD")){
                    weatherDTO.setWindSpeed((String)item.get("fcstValue"));
                }
            }
        }

        weatherDTO.setCity(city);
        weatherDTO.setFcstTime(fcstTime);
        Weather weather = weatherDTO.toEntity();
        weatherRepository.save(weather);
    }

    @Override
    public List<Weather> findMyCityWeather(GetWeatherDTO getWeatherDTO) {
        return weatherRepository.findByCity(getWeatherDTO.getCity());
    }
}
