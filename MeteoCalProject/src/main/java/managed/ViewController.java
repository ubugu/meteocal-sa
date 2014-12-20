package managed;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import java.util.Calendar;

/**
 *
 * @author claudio
 */
@ManagedBean(name = "viewController", eager = true)
@SessionScoped
public class ViewController {
    protected enum ViewType {
        MONTH_VIEW, WEEK_VIED, DAY_VIEW, 
    }
    
    protected enum Month {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    }
    
    Month month;
    Integer day;
    Integer year;
    Month sessionMonth;
    Integer sessionDay;
    Integer sessionYear;

    private ViewType view;
   
    public ViewController() {
        System.out.println("ciao sono partito");
        this.month = Month.values()[Calendar.getInstance().get(Calendar.MONTH)];
        this.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        this.year = Calendar.getInstance().get(Calendar.YEAR);
        this.sessionMonth = Month.values()[Calendar.getInstance().get(Calendar.MONTH)];
        this.sessionDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        this.sessionYear = Calendar.getInstance().get(Calendar.YEAR);
    }
 
    public String isSlotEnabled(String value) {
         Calendar calendar = Calendar.getInstance();
        Integer gridNumber = Integer.parseInt(value);
        calendar.set(year, month.ordinal(), 1);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_MONTH, gridNumber-weekDay);
        Integer number = calendar.get(Calendar.MONTH);
        if (this.month.ordinal() != number) {
            setToCurrentDate();
            return "#999999";
        }
        setToCurrentDate();
        return "#FFFFFF";
    }
    public void setToCurrentDate() {
        Calendar.getInstance().set(sessionYear, sessionMonth.ordinal(), sessionDay);
    }
    
    public String getMonth() {
        Integer month =  java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        if (this.month == null) {
            this.month = Month.values()[month];
        }
        return this.month.toString();
    }
  
    public String getDayNumber(String value) {
        Calendar calendar = Calendar.getInstance();
        Integer gridNumber = Integer.parseInt(value);
        calendar.set(year, month.ordinal(), 1);
        int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_MONTH, gridNumber-weekDay);
        Integer number = calendar.get(Calendar.DAY_OF_MONTH);
        setToCurrentDate();
        return number.toString();
    }
    
    public void setNextMonth() {
        int number = this.month.ordinal();
        number++;
        if (number > 12 ) number = 0;
        this.month = Month.values()[number];
        return;
    }
    
     public void prevMonth(ActionEvent actionEvent) {
        int number = this.month.ordinal();
        number--;
        if (number < 0 ) number = 11;
        this.month = Month.values()[number];
        return;
    }
    
}
