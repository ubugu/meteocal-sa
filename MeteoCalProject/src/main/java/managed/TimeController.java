
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.bean.ManagedBean;
import jsf.entity.Badconditions;
import jsf.entity.Event;
import jsf.entity.Notification;
import jsf.entity.Participant;
import jsf.entity.User;
import jsf.entity.Weather;
import jsf.entity.facade.BadconditionsFacade;
import jsf.entity.facade.EventFacade;
import jsf.entity.facade.NotificationFacade;
import jsf.entity.facade.ParticipantFacade;
import jsf.entity.facade.WeatherFacade;
import net.aksingh.owmjapis.DailyForecast;
import net.aksingh.owmjapis.OpenWeatherMap;
import org.joda.time.DateTime;
import org.json.JSONException;

/**
 *
 * @author Walter
 */

@ManagedBean( name = "TimeController",eager=true)
@Singleton
@Startup
public class TimeController {
    @EJB
    WeatherFacade weatherFacade;
    
    @EJB
    EventFacade eventFacade;
    
    @EJB
    BadconditionsFacade badConditionsFacade;
    
    @EJB
    NotificationFacade notificationFacade;
    
    @EJB
    ParticipantFacade participantFacade;
    
    OpenWeatherMap owm = new OpenWeatherMap("");
    
    List<Weather> allWeather;
    
    /**
     * update the weather every 5 minutes
     */
     @Schedule(minute = "*/3", hour = "*", persistent = false)
     public void UpdateWeather()  {
        DateTime time = new DateTime();
        System.out.println("Weather Update at  " + time);
        update();
        updateNull();
        checkBadConditions();
    }
    
    
     /**
      * search fon bad conditions and check the three days or one day badconditions contrain.
      */
    public void checkBadConditions() {
        List<Badconditions> badConditions = this.badConditionsFacade.findAll();
        Event associatedEvent;
        DateTime today = new DateTime();
        DateTime eventDate;
        for (Badconditions b : badConditions) {
            associatedEvent = b.getEventID();
            eventDate = new DateTime(associatedEvent.getDate());
            if ((eventDate.getDayOfYear() - today.getDayOfYear()) == 1 && eventDate.getYear() == today.getYear()) {
                oneDayAdvise(b);
            }
            
            if ((eventDate.getDayOfYear() - today.getDayOfYear()) == 3 && eventDate.getYear() == today.getYear()) {
                threeDayAdvise(b);
            }            
        }
    }
    
        
    /**
     * check conditions for one day badconditions
     * @param badConditions to check
     */
    public void oneDayAdvise(Badconditions badConditions) {
        Event associatedEvent = badConditions.getEventID();
        
       if (checkConditions(badConditions,associatedEvent.getWeatherID())) {
           List<Participant> participants = this.participantFacade.searchByEvent(associatedEvent.getId());
           for (Participant p : participants) {
               if (p.getParticipant().equals("YES")) {
                   Notification notification = new Notification();
                   notification.setDescription("The event " + associatedEvent.getTitle() + " scheduled for "
                   + associatedEvent.getDate() + " does not satisfy the event conditions set by the event creator.");
                   notification.setEventID(associatedEvent);
                   notification.setType("BADCONDITIONS");
                   notification.setUser(p.getUser1());
                   notification.setVisualized("NO");
                   
                   notification.setId(null);
                   notificationFacade.create(notification);
               }
               
           }
       }
    }
    
    /**
     * check conditions for three day badconditions
     * @param badConditions to check
     */
    public void threeDayAdvise(Badconditions badConditions) {
         Event associatedEvent = badConditions.getEventID();
         if (checkConditions(badConditions, associatedEvent.getWeatherID())) {
            User creator = associatedEvent.getCalendar().getOwner();
            String days = availableDay(associatedEvent.getCity(), badConditions);
            String[] daysString = days.split(";");
            String description = "The event " + associatedEvent.getTitle() + " scheduled for " 
                    + associatedEvent.getDate() + " does not satisfy the event conditions. Possible suitable dates are : </> ";
            for (String s : daysString) {
                description += s + " </> ";
            }
            Notification notification = new Notification();
            notification.setDescription(description);
            notification.setEventID(associatedEvent);
            notification.setType("BADCONDITIONS");
            notification.setUser(creator);
            notification.setVisualized("NO");
            
            notification.setId(null);
            notificationFacade.create(notification);
        }
    }
    
    /**
     * search fon an available day for the three day bad conditions
     * @param city of the event
     * @param badConditions of the event
     * @return a string contaianing all the available days, divided by ';'
     */
    public String availableDay(String city, Badconditions badConditions) {
        String days = "";
        DailyForecast forecast = null;
        Byte daysForecast = 13;
        DateTime oldDate = new DateTime(badConditions.getEventID().getDate());
        DateTime currentDate;
        try {
            forecast = owm.dailyForecastByCityName(city, daysForecast);
            Weather weather = new Weather();
            for (int i = 0;  i < 13; i++) {
                currentDate = new DateTime(forecast.getForecastInstance(i).getDateTime());
                if (currentDate.getYear() == oldDate.getYear() && currentDate.getMonthOfYear() == oldDate.getMonthOfYear() && currentDate.getDayOfMonth() == oldDate.getDayOfMonth()) {
                    continue;
                }
                weather.setClouds(forecast.getForecastInstance(i).getPercentageOfClouds());
                weather.setTemperature(forecast.getForecastInstance(i).getTemperatureInstance().getDayTemperature());
                int rain = (int) forecast.getForecastInstance(i).getRain();
                int snow = (int) forecast.getForecastInstance(i).getSnow();
                if (rain > snow) {
                    weather.setPrecipitations(rain);
                    weather.setPrecipitationType("RAIN");
                } else {
                    weather.setPrecipitations(snow);
                    weather.setPrecipitationType("SNOW");
                }
                
                if (!checkConditions(badConditions,weather)) {
                    DateTime date = new DateTime(forecast.getForecastInstance(i).getDateTime());
                    days += forecast.getForecastInstance(i).getDateTime().toString() + ";";
                }

            }
 
            if (days.equals("")) {
                return "No date Found";
            } else {
                return days;
            }

        } catch (IOException | JSONException| NullPointerException | IndexOutOfBoundsException ex) {
            Logger.getLogger(TimeController.class.getName()).log(Level.SEVERE, null, ex);
        } 
            
        return null;
    }
    
    
    /**
     * Check if  @param associated weather violates the bound inserted
     * @param badConditions to check 
     * @return true if badConditions are violated, otherwis false. if no weather is available, is returned false.
     */
    public boolean checkConditions(Badconditions badConditions,Weather associatedWeather) {
        
        if (associatedWeather == null) {
            return false;
        }
        
       //check bad weather for precipitations
        if (badConditions.getPrecipitations() == null) {
            if (checkPrecipitations(badConditions.getLayer(), associatedWeather, 0)) {
                return true;
            }
        } else {
            if (checkPrecipitations(badConditions.getLayer(), associatedWeather, badConditions.getPrecipitations())) {
                return true;
            }
        }
        
        //check bad weather for temperature
        if (badConditions.getTemperature() != null && badConditions.getTemperature() > associatedWeather.getTemperature()) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Check the precipitations
     * @param layer of the badconditions
     * @param associatedWeather to the event
     * @param maxValue  of the badconditions 
     * @return true if the badconditions constraint are violated. otherwise false.
     */
     public boolean checkPrecipitations(String layer, Weather associatedWeather, float maxValue) {
         if (layer.equals("snowy")
                && associatedWeather.getPrecipitationType().equals("SNOW")
                && associatedWeather.getPrecipitations() > maxValue) {
            return true;
        }
             
        if (layer.equals("rainy") 
                && (associatedWeather.getPrecipitations() > maxValue
                || (associatedWeather.getPrecipitationType().equals("SNOW")
                && associatedWeather.getPrecipitations() > 0))) {
            return true;
        }

         if (layer.equals("cloudy")) {
             if (associatedWeather.getClouds() > maxValue
                     || associatedWeather.getPrecipitations() > 0) {
                 return true;
             }
         }
 

         return false;
    }

     
     /**
      * update existing weather
      * @param oldWeather  to update
      */
    private void updateWeather(Weather oldWeather) {
        System.out.println("updating " + oldWeather.getCity() + " on date " + oldWeather.getDate().toString());
        

        DailyForecast forecast = null;

        DateTime currentDate = new DateTime();
        DateTime targetDate = new DateTime(oldWeather.getDate());
        Integer dayForecast = targetDate.getDayOfYear() - currentDate.getDayOfYear() + 1;

        if (dayForecast > 13) {
            return;
        }
        try {
            forecast = owm.dailyForecastByCityName(oldWeather.getCity(), dayForecast.byteValue());
            
            oldWeather.setClouds(forecast.getForecastInstance(dayForecast - 1).getPercentageOfClouds());
            oldWeather.setHumidity(forecast.getForecastInstance(dayForecast - 1).getHumidity());
            int rain = (int)forecast.getForecastInstance(dayForecast - 1).getRain();
            int snow = (int)forecast.getForecastInstance(dayForecast - 1).getSnow();
            if (rain > snow) {
                oldWeather.setPrecipitations(rain);
                oldWeather.setPrecipitationType("RAIN");
            } else {
                oldWeather.setPrecipitations(snow);
                oldWeather.setPrecipitationType("SNOW");
            }

            if (snow == 0 && rain == 0) {
                oldWeather.setPrecipitationType("NONE");
            }

            oldWeather.setPressure(forecast.getForecastInstance(dayForecast - 1).getPressure());
            oldWeather.setTemperature(forecast.getForecastInstance(dayForecast - 1).getTemperatureInstance().getDayTemperature());
            oldWeather.setMaxTemperature(forecast.getForecastInstance(dayForecast - 1).getTemperatureInstance().getMaximumTemperature());
            oldWeather.setMinTemperature(forecast.getForecastInstance(dayForecast - 1).getTemperatureInstance().getMinimumTemperature());
            oldWeather.setWind(forecast.getForecastInstance(dayForecast - 1).getWindSpeed());
            this.weatherFacade.edit(oldWeather);

        } catch (IOException | JSONException ex) {
            System.out.println("Error while contacting the weather website");
        } catch (NullPointerException | IndexOutOfBoundsException ex) {
            System.out.println("No weather found for the city searched.");
        }
    }
    
    /**
     * Updates all the weather elements that are stored in the database
     */
    public void update() {    
        allWeather = this.weatherFacade.findAll();
        for (Weather w : allWeather) {
            updateWeather(w);
        }
    }
    
    /*
    * Search for event that has (city != null) and have weather = null due to 
    */
    public void updateNull() {
        
        
        List<Event> noWeatherEvents = eventFacade.searchByNullWeather();
        for (Event e : noWeatherEvents) {
            // getting current weather data for the "London" city
            DailyForecast forecast = null;

            DateTime currentDate = new DateTime();
            DateTime targetDate = new DateTime(e.getDate());
            Integer dayForecast = targetDate.getDayOfYear() - currentDate.getDayOfYear() + 1;

            if (dayForecast > 13) {           
                continue;
            }

        //check if meteo for inserted date and city already exixst in the database
            Weather oldWeather = this.weatherFacade.searchByCityAndDate(e.getCity(), e.getDate());
            if (oldWeather != null) {
                e.setWeatherID(oldWeather);
                eventFacade.edit(e);
                continue;
            }

            Weather weather = new Weather();
            DailyForecast forescast;
            try {
                forecast = owm.dailyForecastByCityName(e.getCity(), dayForecast.byteValue());
                weather.setId(null);
                weather.setCity(forecast.getCityInstance().getCityName());
                weather.setClouds(forecast.getForecastInstance(dayForecast - 1).getPercentageOfClouds());
                weather.setDate(forecast.getForecastInstance(dayForecast - 1).getDateTime());
                weather.setHumidity(forecast.getForecastInstance(dayForecast - 1).getHumidity());
                int rain = (int) forecast.getForecastInstance(dayForecast - 1).getRain();
                int snow = (int) forecast.getForecastInstance(dayForecast - 1).getSnow();
                if (rain > snow) {
                    weather.setPrecipitations(rain);
                    weather.setPrecipitationType("RAIN");
                } else {
                    weather.setPrecipitations(snow);
                    weather.setPrecipitationType("SNOW");
                }
                
                if (snow == 0 && rain == 0) {
                    weather.setPrecipitationType("NONE");
                }
                
                weather.setPressure(forecast.getForecastInstance(dayForecast - 1).getPressure());
                weather.setTemperature(forecast.getForecastInstance(dayForecast - 1).getTemperatureInstance().getDayTemperature());
                weather.setMaxTemperature(forecast.getForecastInstance(dayForecast - 1).getTemperatureInstance().getMaximumTemperature());
                weather.setMinTemperature(forecast.getForecastInstance(dayForecast - 1).getTemperatureInstance().getMinimumTemperature());
                weather.setWind(forecast.getForecastInstance(dayForecast - 1).getWindSpeed());

                weather.setId(null);
                this.weatherFacade.create(weather);
                e.setWeatherID(weather);
                eventFacade.edit(e);
            } catch (IOException | JSONException ex) {
                System.out.println("Error while contacting the weather website");
            } catch (NullPointerException | IndexOutOfBoundsException ex) {
                System.out.println("No weather found for the city searched.");
            }

        }
    }

    
}
