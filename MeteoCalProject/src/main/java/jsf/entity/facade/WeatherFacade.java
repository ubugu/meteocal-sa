/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity.facade;

import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import jsf.entity.Weather;
import org.joda.time.DateTime;

/**
 *
 * @author claudio
 */
@Stateless
public class WeatherFacade extends AbstractFacade<Weather> {
    @PersistenceContext(unitName = "com.mycompany_MeteoCalProject_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public WeatherFacade() {
        super(Weather.class);
    }
    
    public Weather searchById(int id) {
        return (Weather) em.createNamedQuery("Weather.findById").setParameter("id", id).getSingleResult();
    }

    public Weather searchByCityAndDate(String city, Date date) {
        List<Weather> cityWeather = (List<Weather>) em.createNamedQuery("Weather.findByCity").setParameter("city", city).getResultList();
        DateTime insertedDate = new DateTime(date);
        DateTime searchedDate;
        for (Weather w : cityWeather ) {
            searchedDate = new DateTime(w.getDate());
            if (searchedDate.getDayOfYear() == insertedDate.getDayOfYear()
                    && searchedDate.getMonthOfYear() == insertedDate.getMonthOfYear()
                    && searchedDate.getYear() == insertedDate.getYear() ) {
                return w;
            }
        }
        
        return null;
    }
    
}