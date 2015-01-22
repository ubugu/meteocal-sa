/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.cotroller;

import jsf.entity.Badconditions;
import jsf.entity.Weather;
import managed.TimeController;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author claudio
 */
public class TimeControllerTest {
    
    @Test
    public void PrecipitationTest() {
        
        TimeController timeController = new TimeController();
        int maxValue = 0;
        float cloud = 0 ;
        Weather weather = new Weather();
        weather.setClouds(cloud);
        weather.setPrecipitationType("NONE");
        weather.setPrecipitations(0);
        maxValue = 0;
        assertEquals(false, timeController.checkPrecipitations("rainy", weather, maxValue));
        assertEquals(false, timeController.checkPrecipitations("snowy", weather, maxValue));
        assertEquals(false, timeController.checkPrecipitations("cloudy", weather, maxValue));
        maxValue = 1;
        assertEquals(false, timeController.checkPrecipitations("rainy", weather, maxValue));
        assertEquals(false, timeController.checkPrecipitations("snowy", weather, maxValue));
        assertEquals(false, timeController.checkPrecipitations("cloudy", weather, maxValue));
        
        
        cloud = 10;
        weather.setClouds(cloud);
        weather.setPrecipitationType("NONE");
        weather.setPrecipitations(0);
        maxValue = 0;
        assertEquals(false, timeController.checkPrecipitations("rainy", weather, maxValue));
        assertEquals(false, timeController.checkPrecipitations("snowy", weather, maxValue));
        assertEquals(true, timeController.checkPrecipitations("cloudy", weather, maxValue));
        maxValue = 15;
        assertEquals(false, timeController.checkPrecipitations("rainy", weather, maxValue));
        assertEquals(false, timeController.checkPrecipitations("snowy", weather, maxValue));
        assertEquals(false, timeController.checkPrecipitations("cloudy", weather, maxValue));
        
        cloud = 10;
        weather.setClouds(cloud);
        weather.setPrecipitationType("RAIN");
        weather.setPrecipitations(5);
        maxValue = 0;
        assertEquals(true, timeController.checkPrecipitations("rainy", weather, maxValue));
        assertEquals(false, timeController.checkPrecipitations("snowy", weather, maxValue));
        assertEquals(true, timeController.checkPrecipitations("cloudy", weather, maxValue));
        maxValue = 7;
        assertEquals(false, timeController.checkPrecipitations("rainy", weather, maxValue));
        assertEquals(false, timeController.checkPrecipitations("snowy", weather, maxValue));
        assertEquals(true, timeController.checkPrecipitations("cloudy", weather, maxValue));
        
        
        cloud = 5;
        weather.setClouds(cloud);
        weather.setPrecipitationType("SNOW");
        weather.setPrecipitations(5);
        maxValue = 0;
        assertEquals(true, timeController.checkPrecipitations("rainy", weather, maxValue));
        assertEquals(true, timeController.checkPrecipitations("snowy", weather, maxValue));
        assertEquals(true, timeController.checkPrecipitations("cloudy", weather, maxValue));
        maxValue = 7;
        assertEquals(true, timeController.checkPrecipitations("rainy", weather, maxValue));
        assertEquals(false, timeController.checkPrecipitations("snowy", weather, maxValue));
        assertEquals(true, timeController.checkPrecipitations("cloudy", weather, maxValue));
        
    }
    
    @Test
    public void checkCondition() {
        TimeController timeController = new TimeController();
        float maxValue = 0;
        float cloud = 0 ;
        float temperature  = 0;
        Weather weather = new Weather();
        Badconditions badConditions = new  Badconditions();
        
        
        weather.setClouds(cloud);
        weather.setPrecipitationType("NONE");
        weather.setPrecipitations(0);
        weather.setTemperature(temperature);
        
        cloud = 10;
        temperature = 10;
        weather.setTemperature(temperature + 1);
        weather.setClouds(cloud); 
        weather.setPrecipitationType("NONE");
        weather.setPrecipitations(0);
        maxValue = 0;
        badConditions.setLayer("rainy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(false, timeController.checkConditions(badConditions, weather));
        badConditions.setLayer("snowy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(false, timeController.checkConditions(badConditions, weather));
        badConditions.setLayer("cloudy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(true, timeController.checkConditions(badConditions, weather));
        maxValue = 15;
        badConditions.setLayer("rainy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(false, timeController.checkConditions(badConditions, weather));
        badConditions.setLayer("snowy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(false, timeController.checkConditions(badConditions, weather));
        badConditions.setLayer("cloudy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(false, timeController.checkConditions(badConditions, weather));
        
        
        cloud = 0;
        temperature = 10;
        weather.setTemperature(temperature - 5);
        weather.setClouds(cloud); 
        weather.setPrecipitationType("NONE");
        weather.setPrecipitations(0);
        maxValue = 0;
        badConditions.setLayer("rainy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(true, timeController.checkConditions(badConditions, weather));
        badConditions.setLayer("snowy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(true, timeController.checkConditions(badConditions, weather));
        badConditions.setLayer("cloudy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(true, timeController.checkConditions(badConditions, weather));
        maxValue = 15;
        badConditions.setLayer("rainy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(true, timeController.checkConditions(badConditions, weather));
        badConditions.setLayer("snowy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(true, timeController.checkConditions(badConditions, weather));
        badConditions.setLayer("cloudy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(true, timeController.checkConditions(badConditions, weather));
        
        
        cloud = 0;
        temperature = 10;
        weather.setTemperature(temperature + 1);
        weather.setClouds(cloud); 
        weather.setPrecipitationType("NONE");
        weather.setPrecipitations(0);
        maxValue = 0;
        badConditions.setLayer("rainy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(false, timeController.checkConditions(badConditions, weather));
        badConditions.setLayer("snowy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(false, timeController.checkConditions(badConditions, weather));
        badConditions.setLayer("cloudy");
        badConditions.setPrecipitations(maxValue);
        badConditions.setTemperature(temperature);
        assertEquals(false, timeController.checkConditions(badConditions, weather));

       
    }
}
