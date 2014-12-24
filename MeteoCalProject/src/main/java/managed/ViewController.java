package managed;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author claudio
 */
@ManagedBean(name = "viewController", eager = true)
@SessionScoped
public class ViewController {
    protected enum ViewType {
        MONTH_VIEW, WEEK_VIEW, DAY_VIEW, 
    }
    
    protected enum Month {
        JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    }
    

    private Month month;
    private Integer day;
    private Integer year;
    private Integer week;
    private Month sessionMonth;
    private Integer sessionDay;
    private Integer sessionYear;
    private Date date1;
    private ViewType view;
   
    public ViewController() {
        this.view = ViewType.MONTH_VIEW;
        this.month = Month.values()[Calendar.getInstance().get(Calendar.MONTH)];
        this.week = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        this.day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        this.year = Calendar.getInstance().get(Calendar.YEAR);
        this.sessionMonth = Month.values()[Calendar.getInstance().get(Calendar.MONTH)];
        this.sessionDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        this.sessionYear = Calendar.getInstance().get(Calendar.YEAR);
    }
    
    
    public void setDate1(Date date1) {
        this.date1 = date1;
    }
    
    public String getDate() {
        if (date1 != null) {
            return date1.toString();
        } else {
            return "is null";
        }
        
    }
    public Date getDate1() { 
       return date1;
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
    
    public String show(String value) {
        Integer intValue = Integer.parseInt(value);
        if (this.view.ordinal() == intValue ) {
            return "true";
        }
        return "none";
    }
    
    public void setView(String value) {
        Integer intValue = Integer.parseInt(value);
        this.view = ViewType.values()[intValue];
    }
    
    public String getMonth(boolean intValue) {
        Integer month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
        if (this.month == null) {
            this.month = Month.values()[month];
        }
        if (!intValue) {
            return this.month.toString();
        }
        Integer monthIntValue = this.month.ordinal();
        monthIntValue++;
        return monthIntValue.toString();
    }
    
    public String getYear() {
        return this.year.toString();
    }
  
    public String getWeek() {
        return this.week.toString();
     }
            
    public String getDayNumber(String value) {
        Calendar calendar = Calendar.getInstance();
        Integer gridNumber = Integer.parseInt(value);
        if (this.view == ViewType.MONTH_VIEW) {
            calendar.set(year, month.ordinal(), 1);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            calendar.add(Calendar.DAY_OF_MONTH, gridNumber - weekDay);
            Integer number = calendar.get(Calendar.DAY_OF_MONTH);
            setToCurrentDate();
            return number.toString();
        }
       
        if (this.view == ViewType.WEEK_VIEW) {
            calendar.set(year, month.ordinal(), this.day);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
            calendar.add(Calendar.DAY_OF_MONTH, gridNumber - weekDay);
            Integer number = calendar.get(Calendar.DAY_OF_MONTH);
            setToCurrentDate();
            return number.toString();
        }
        
        if (this.view == ViewType.DAY_VIEW) {
            calendar.set(year, month.ordinal(), this.day);
            
            calendar.add(Calendar.DAY_OF_MONTH, gridNumber - 2);
            Integer number = calendar.get(Calendar.DAY_OF_MONTH);
            setToCurrentDate();
            return number.toString();
        }
                
        return "error";
    }
    
    
    public String getFullDate(String value) {
        Calendar calendar = Calendar.getInstance();
        Integer gridNumber = Integer.parseInt(value);
        
         if (this.view == ViewType.DAY_VIEW) {
            calendar.set(year, month.ordinal(), this.day);
            
            calendar.add(Calendar.DAY_OF_MONTH, gridNumber - 2);
            Integer day = calendar.get(Calendar.DAY_OF_MONTH);
            Integer month = calendar.get(Calendar.MONTH);
            Integer year = calendar.get(Calendar.YEAR);
            month++;
            setToCurrentDate();
            return day.toString() + "/" + month.toString() + "/" + year.toString();
        }
                
        return "error";
    }
    
      public void nextMonth() {
        int number = this.month.ordinal();
        number++;
        if (number > 11 ) {
            number = 0;
            this.year++;
        }
        
        this.day = 1;
        this.month = Month.values()[number];
    }
      
     public void prevMonth() { 

        int number = this.month.ordinal();
        number--;
        if (number < 0 ) {
            number = 11;
            this.year--;
        }
        
        this.day = 1;
        this.month = Month.values()[number];
    }
     
       public void nextWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.year,this.month.ordinal(), this.day);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        this.month = Month.values()[calendar.get(Calendar.MONTH)];
        this.week = calendar.get(Calendar.WEEK_OF_YEAR);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.year = calendar.get(Calendar.YEAR);
    }
       public void prevWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.year,this.month.ordinal(), this.day);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        this.month = Month.values()[calendar.get(Calendar.MONTH)];
        this.week = calendar.get(Calendar.WEEK_OF_YEAR);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.year = calendar.get(Calendar.YEAR);
       }
       
       public void nextDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.year,this.month.ordinal(), this.day);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        this.month = Month.values()[calendar.get(Calendar.MONTH)];
        this.week = calendar.get(Calendar.WEEK_OF_YEAR);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.year = calendar.get(Calendar.YEAR);
    }
       public void prevDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(this.year,this.month.ordinal(), this.day);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        this.month = Month.values()[calendar.get(Calendar.MONTH)];
        this.week = calendar.get(Calendar.WEEK_OF_YEAR);
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
        this.year = calendar.get(Calendar.YEAR);
       }
       
    
    
}
