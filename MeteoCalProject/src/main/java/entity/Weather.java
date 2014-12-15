/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Walter
 */
@Entity
@Table(name = "weather")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Weather.findAll", query = "SELECT w FROM Weather w"),
    @NamedQuery(name = "Weather.findByCity", query = "SELECT w FROM Weather w WHERE w.weatherPK.city = :city"),
    @NamedQuery(name = "Weather.findByDate", query = "SELECT w FROM Weather w WHERE w.weatherPK.date = :date"),
    @NamedQuery(name = "Weather.findByPrecipitations", query = "SELECT w FROM Weather w WHERE w.precipitations = :precipitations"),
    @NamedQuery(name = "Weather.findByTemperature", query = "SELECT w FROM Weather w WHERE w.temperature = :temperature"),
    @NamedQuery(name = "Weather.findByWind", query = "SELECT w FROM Weather w WHERE w.wind = :wind"),
    @NamedQuery(name = "Weather.findByPressure", query = "SELECT w FROM Weather w WHERE w.pressure = :pressure"),
    @NamedQuery(name = "Weather.findByHumidity", query = "SELECT w FROM Weather w WHERE w.humidity = :humidity"),
    @NamedQuery(name = "Weather.findByClouds", query = "SELECT w FROM Weather w WHERE w.clouds = :clouds")})
public class Weather implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected WeatherPK weatherPK;
    @Column(name = "precipitations")
    private Integer precipitations;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "temperature")
    private Float temperature;
    @Column(name = "wind")
    private Float wind;
    @Column(name = "pressure")
    private Float pressure;
    @Column(name = "humidity")
    private Float humidity;
    @Column(name = "clouds")
    private Float clouds;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "weather")
    private Collection<Event> eventCollection;

    public Weather() {
    }

    public Weather(WeatherPK weatherPK) {
        this.weatherPK = weatherPK;
    }

    public Weather(String city, Date date) {
        this.weatherPK = new WeatherPK(city, date);
    }

    public WeatherPK getWeatherPK() {
        return weatherPK;
    }

    public void setWeatherPK(WeatherPK weatherPK) {
        this.weatherPK = weatherPK;
    }

    public Integer getPrecipitations() {
        return precipitations;
    }

    public void setPrecipitations(Integer precipitations) {
        this.precipitations = precipitations;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getWind() {
        return wind;
    }

    public void setWind(Float wind) {
        this.wind = wind;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public Float getClouds() {
        return clouds;
    }

    public void setClouds(Float clouds) {
        this.clouds = clouds;
    }

    @XmlTransient
    public Collection<Event> getEventCollection() {
        return eventCollection;
    }

    public void setEventCollection(Collection<Event> eventCollection) {
        this.eventCollection = eventCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (weatherPK != null ? weatherPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Weather)) {
            return false;
        }
        Weather other = (Weather) object;
        if ((this.weatherPK == null && other.weatherPK != null) || (this.weatherPK != null && !this.weatherPK.equals(other.weatherPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Weather[ weatherPK=" + weatherPK + " ]";
    }
    
}
