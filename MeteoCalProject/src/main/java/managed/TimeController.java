
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.POST;
import jsf.entity.Weather;
import jsf.entity.facade.WeatherFacade;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.joda.time.DateTime;
import org.json.JSONException;

/**
 *
 * @author Walter
 */
/*
@ManagedBean( name = "TimeController",eager=true)
@Singleton
@Startup*/
public class TimeController {
    
    @PostConstruct
    public void lookTime() {


        System.out.println("WEATHER UPDATE");
        
        
            
       //     update();
         
            
            
        
        

    } 
    
       
        @EJB
    WeatherFacade weatherFacade;

    private void updateWeather(Weather oldWeather) {
        System.out.println("updateing " + oldWeather.getCity() + " on date " + oldWeather.getDate().toString());
        OpenWeatherMap owm = new OpenWeatherMap("");

        DailyForecast forecast = null;

        DateTime currentDate = new DateTime(java.util.Calendar.getInstance().getTime());
        DateTime targetDate = new DateTime(oldWeather.getDate());
        Integer dayForecast = targetDate.getDayOfYear() - currentDate.getDayOfYear() + 1;

        if (dayForecast > 13) {
            return;
        }

        Weather weather = new Weather();
        try {
            forecast = owm.dailyForecastByCityName(oldWeather.getCity(), dayForecast.byteValue());
            weather.setId(this.weatherFacade.getMaxNotificationID() + 1);
            weather.setCity(oldWeather.getCity());
            weather.setClouds(forecast.getForecastInstance(dayForecast - 1).getPercentageOfClouds());
            weather.setDate(oldWeather.getDate());
            weather.setHumidity(forecast.getForecastInstance(dayForecast - 1).getHumidity());
            weather.setPrecipitations((int) forecast.getForecastInstance(dayForecast - 1).getRain());
            weather.setPressure(forecast.getForecastInstance(dayForecast - 1).getPressure());
            weather.setTemperature(forecast.getForecastInstance(dayForecast - 1).getTemperatureInstance().getDayTemperature());
            weather.setWind(forecast.getForecastInstance(dayForecast - 1).getWindSpeed());
            this.weatherFacade.edit(weather);

        } catch (IOException ex) {
            System.out.println("ioExeneeip");
        } catch (JSONException ex) {
            System.out.println("JSON EXCEPTIon");
        }
    }
    
    public void update() {
         
        List<Weather> allWeather = this.weatherFacade.findAll();

        for (Weather w : allWeather) {
            updateWeather(w);
        }
    }
    
}
