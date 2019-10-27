package Main;

// implementation of the WeatherItem class, should not be used by frontend
public class WeatherItem1 extends WeatherItem {

    //data from api
    int time;
    int wind_speed;
    int temperature;
    String condition; //condition 'main' type

    public WeatherItem1(int time, int wind_speed, int temperature, String condition) {
        this.time = time;
        this.wind_speed = wind_speed;
        this.temperature = temperature;
        this.condition = condition;
    }
    //weather types: Thunderstorm, Drizzle, Rain, Snow, Hazy, Clear, Clouds


    @Override
    public String getTime() {
        assert time >= 6;
        if(time < 13){
            return time + " am";
        } else if (time < 24){
            return (time-12) + " pm";
        } else {
            return "Overnight";
        }
    }

    @Override
    public String getWind() {
        return Integer.toString(wind_speed) + (" mph");
    }

    @Override
    public String getRug() {
        int rugtemp = temperature; //basis of effective temperature
        if (Settings.getInstance().getAge() > 18){ //Parallels horse age distribution
            //horses older than 18 need more warmth, represent by decrementing effective temperature
            rugtemp = rugtemp-2;
            if (Settings.getInstance().getAge() > 25 ){
                rugtemp = rugtemp - 3;
                //effect increased for horses above 25
            }
        }
        if (Settings.getInstance().isClipped()){ //if clipped, will be colder, decrement temp
            rugtemp = rugtemp - 5; //as indicated by BETA-UK and detailed in requirements
        }

        if (rugtemp > 15){ //I.e. clipped horse in 16 degrees, or unclipped in 11
            return "No Rug";
        }
        else if (rugtemp > 10){ //I.e. clipped horse in 11, or unclipped in 6
            return "Lightweight Rug";
        }
        else if (rugtemp > 5){
            return "Medium Rug";
        }
        else if(rugtemp > 0){
            return "Heavy Rug";
        }
        else{
            return "Heavy/Neck";
        }

    }

    @Override
    public String getTemperature() {
        return Integer.toString(temperature) +"\u00B0" + "C" ; //convert to text with degrees celsisu
    }

    @Override
    public boolean isSafe() { //returns true if safe to ride
        if ((temperature < 1) || (temperature > 30) || condition.equals("Thunderstorm") || condition.equals("Snow")){
            return false;
            //Blue Cross: Safe to ride if not freezing, lightning or snowing
        }
        else{
            return true;
        }
    }

    @Override
    public String getCondition() {
        if( condition.equals("Clear")) { //Sunny is clearer than 'clear' so convert.
            return "Sunny";
        }
        else{
            return condition;
        }
    }

}