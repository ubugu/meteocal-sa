/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.bean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
    @NamedQuery(name = "Weather.findByCity", query = "SELECT w FROM Weather w WHERE w.city = :city"),
    @NamedQuery(name = "Weather.findByDate", query = "SELECT w FROM Weather w WHERE w.date = :date"),
    @NamedQuery(name = "Weather.findByPrecipitations", query = "SELECT w FROM Weather w WHERE w.precipitations = :precipitations"),
    @NamedQuery(name = "Weather.findByTemperature", query = "SELECT w FROM Weather w WHERE w.temperature = :temperature"),
    @NamedQuery(name = "Weather.findByWind", query = "SELECT w FROM Weather w WHERE w.wind = :wind"),
    @NamedQuery(name = "Weather.findByPressure", query = "SELECT w FROM Weather w WHERE w.pressure = :pressure"),
    @NamedQuery(name = "Weather.findByHumidity", query = "SELECT w FROM Weather w WHERE w.humidity = :humidity"),
    @NamedQuery(name = "Weather.findByClouds", query = "SELECT w FROM Weather w WHERE w.clouds = :clouds"),
    @NamedQuery(name = "Weather.findById", query = "SELECT w FROM Weather w WHERE w.id = :id")})
public class Weather implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "city")
    private String city;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
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
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @OneToMany(mappedBy = "weatherID")
    private Collection<Event> eventCollection;

    public Weather() {
    }

    public Weather(Integer id) {
        this.id = id;
    }

    public Weather(Integer id, String city, Date date) {
        this.id = id;
        this.city = city;
        this.date = date;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Weather)) {
            return false;
        }
        Weather other = (Weather) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.bean.Weather[ id=" + id + " ]";
    }
    
}
