
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managed;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.faces.bean.ManagedBean;
import javax.ws.rs.POST;
import org.joda.time.DateTime;

/**
 *
 * @author Walter
 */

@ManagedBean( name = "TimeController",eager=true)
@Singleton
@Startup
public class TimeController {
    
    @PostConstruct
    public void lookTime() {

        //the Date and time at which you want to execute
        Date date;

        date=new DateTime().plusMinutes(1).toDate();
        //Creation of the time and schedule of it
        
        Timer timer = new Timer();

        //Use this if you want to execute it repeatedly
        //1000=1 second
        
        timer.schedule(new MyTimerTask(),date,10000);

        
    } 
}
