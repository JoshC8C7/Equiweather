package Main;

import java.util.List;

// interface of the backend behaviour the frontend should interact with
public interface BackendInterface {

    List<WeatherItem> getWeather();

}
