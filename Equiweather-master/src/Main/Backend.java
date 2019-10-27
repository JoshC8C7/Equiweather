package Main;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// actual implementation of the BackendInterface
public class Backend implements BackendInterface {

    private static final String API_URL = "https://api.openweathermap.org/data/2.5/forecast?appid=2c890b7f3abeffac3c6c26bf7040cf03&id=";

    // methods that sends the API call for a certain location and returns the JSON response in a String
    private static String apiCall(int locationCode) {
        try {
            URL url = new URL(API_URL + locationCode);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            int resp = con.getResponseCode();
            if (resp == HttpURLConnection.HTTP_OK) { // Call was successful

                // read the response
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                StringBuilder json = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    json.append(line);
                }
                in.close();

                // return result
                //System.out.println(json.toString());
                return json.toString();
            } else {
                System.out.println("API call failed");
            }
        } catch (IOException e){
            System.out.println("Failed to read the API call");
        }
        // code that reaches this point has failed to make the call
        return "";
    }

    // method used for testing
    public static void main(String[] args){
        Backend backend = new Backend();
        List<WeatherItem> list = backend.getWeather();

        for(WeatherItem it : list){
            System.out.println(it.getTime() + " - " + it.getCondition() + " - " + it.getTemperature() + " - " + it.getWind() + " - " + it.getRug() + " - " + it.isSafe());
        }
    }

    // builds up a WeatherItem from a JSON object passed as parameter
    private static WeatherItem1 jsonToItem(JSONObject obj){
        int wind_speed = (int) obj.getJSONObject("wind").getDouble("speed");
        int temperature = (int) obj.getJSONObject("main").getDouble("temp") - 273; // Kelvin to Celsius convertion
        String condition = obj.getJSONArray("weather").getJSONObject(0).getString("main");

        // convertion from unix timestamp to hour of the day
        long unixSeconds = obj.getLong("dt");
        Date date = new java.util.Date(unixSeconds*1000L); // convert seconds to milliseconds
        int time = date.getHours();
        if(time < 6) time += 24;

        return new WeatherItem1(time, wind_speed, temperature, condition);
    }


    @Override
    public List<WeatherItem> getWeather() {
        // make API call to get the json
        String resp = apiCall(Settings.getInstance().getLocation());

        // divide the json in different items and get WeatherItem for each
        JSONObject obj = new JSONObject(resp);
        JSONArray arr = obj.getJSONArray("list");
        List<WeatherItem1> allitems = new ArrayList<>();
        for(int i = 0; i<arr.length(); i++){
            allitems.add(jsonToItem(arr.getJSONObject(i)));
        }

        // filter the list of all items for only the elements of the specified day
        // and interpolate between results (which come in 3 hours interval) in order to get hourly interval
        List<WeatherItem> inter = new ArrayList<>();
        int el = 0;
        int start = 6; // first hour of the day displayed
        if(Settings.getInstance().getDay() == Settings.TODAY){
            Date date = new Date();
            start = date.getHours(); // get current hour
        } else {
            // consume the list until the correct date
            for(int i = 0; i<Settings.getInstance().getDay(); i++){
                while(allitems.get(el).time <= 8) el++;
                while(allitems.get(el).time > 8) el++;
            }
        }

        for(int h = start; h < 24; h++){
            // create an item for the hour h by interpolating the two closest items given by the API call
            if(h >= allitems.get(el+1).time) el++;
            WeatherItem1 cur = allitems.get(el);
            WeatherItem1 next = allitems.get(el+1);
            int wind_speed = Math.max(0, cur.wind_speed + (h-cur.time)*(next.wind_speed - cur.wind_speed)/3);
            int temperature = cur.temperature + (h-cur.time)*(next.temperature - cur.temperature)/3;
            String condition = cur.condition;
            inter.add(new WeatherItem1(h,wind_speed,temperature,condition));
        }

        // Overnight item: calculated by doing an average of the two items of the night given by the API
        WeatherItem1 cur = allitems.get(el+1);
        WeatherItem1 next = allitems.get(el+2);
        int wind_speed = (cur.wind_speed + next.wind_speed)/2;
        int temperature = (cur.temperature + next.temperature)/2;
        String condition = cur.condition;
        inter.add(new WeatherItem1(24,wind_speed,temperature,condition));

        return inter;
    }
}
