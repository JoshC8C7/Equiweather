package Main;

import java.util.HashMap;
import java.util.Map;

// singleton object that stores data about the user selection
public class Settings {

    private static Settings instance;

    public static final int CAMBRIDGE_CODE = 2653940;
    public static final int LONDON_CODE = 2643743;
    public static final int OXFORD_CODE = 2640729;

    public static final int COLDEST_CITY = 1507116;

    public static final int TODAY = 0;
    public static final int TOMORROW = 1;
    public static final int DAY_AFTER_TOMORROW = 2;

    private boolean clipped;
    private int age;
    private int location;
    private int day;

    public Map<String, Integer> locToInt;
    public Map<Integer, String> intToLoc;

    //These two are only present so that we can make functions calls from one to the other
    public HomeController hc;
    public SettingController sc;

    private Settings(){
        clipped = false;
        age = 15;
        location = CAMBRIDGE_CODE;
        day = TODAY;

        //initializing some simple maps
        locToInt = new HashMap<>();
        intToLoc = new HashMap<>();
        locToInt.put("Cambridge", 2653940);
        locToInt.put("London", 2643743);
        locToInt.put("Oxford", 2640729);
        intToLoc.put(2653940, "Cambridge");
        intToLoc.put(2643743, "London");
        intToLoc.put(2640729, "Oxford");
    }

    public static Settings getInstance(){
        if(instance == null){
            instance = new Settings();
        }
        return instance;
    }

    private void setHomeController(HomeController hc) {
        this.hc = hc;
    }

    public void setSettingController(SettingController sc) {
        this.sc = sc;

    }


    public boolean isClipped() {
        return clipped;
    }

    public void setClipped(boolean clipped) {
        this.clipped = clipped;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int location) {
        this.location = location;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
