package Main;

// abstract class defining the behaviour of the items of weather (info for a specific hour) as seen by the frontend
public abstract class WeatherItem  {

    public abstract String getTime();
    public abstract String getWind();
    public abstract String getRug();
    public abstract String getTemperature();
    public abstract boolean isSafe();
    public abstract String getCondition();
}