/*
 * Entity class of the User
 */
package jsf.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author claudio
 */
@Entity
@Table(name = "weather")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Weather.findAll", query = "SELECT w FROM Weather w"),
    @NamedQuery(name = "Weather.findByCity", query = "SELECT w FROM Weather w WHERE w.city = :city"),
    @NamedQuery(name = "Weather.findByDate", query = "SELECT w FROM Weather w WHERE w.date = :date"),
    @NamedQuery(name = "Weather.findByDateAndCity", query = "SELECT w FROM Weather w WHERE w.date = :date AND w.city = :city"),
    @NamedQuery(name = "Weather.findByPrecipitations", query = "SELECT w FROM Weather w WHERE w.precipitations = :precipitations"),
    @NamedQuery(name = "Weather.findByTemperature", query = "SELECT w FROM Weather w WHERE w.temperature = :temperature"),
    @NamedQuery(name = "Weather.findByWind", query = "SELECT w FROM Weather w WHERE w.wind = :wind"),
    @NamedQuery(name = "Weather.findByPressure", query = "SELECT w FROM Weather w WHERE w.pressure = :pressure"),
    @NamedQuery(name = "Weather.findByHumidity", query = "SELECT w FROM Weather w WHERE w.humidity = :humidity"),
    @NamedQuery(name = "Weather.findByClouds", query = "SELECT w FROM Weather w WHERE w.clouds = :clouds"),
    @NamedQuery(name = "Weather.findById", query = "SELECT w FROM Weather w WHERE w.id = :id")})
public class Weather implements Serializable {
    @Column(name = "precipitations")
    private Integer precipitations;
    @Column(name = "minTemperature")
    private Float minTemperature;
    @Column(name = "maxTemperature")
    private Float maxTemperature;

    @Size(max = 10)
    @Column(name = "precipitationType")
    private String precipitationType;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
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
    @OneToMany(mappedBy = "weatherID")
    private List<Event> eventList;

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


    public float getTemperature() {
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
    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
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
        return "jsf.entity.Weather[ id=" + id + " ]";
    }

    public String getPrecipitationType() {
        return precipitationType;
    }

    public void setPrecipitationType(String precipitationType) {
        this.precipitationType = precipitationType;
    }

    public Integer getPrecipitations() {
        return precipitations;
    }

    public void setPrecipitations(Integer precipitations) {
        this.precipitations = precipitations;
    }

    public Float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Float minTemperature) {
        this.minTemperature = minTemperature;
    }

    public Float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

}
