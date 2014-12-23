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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
    @NamedQuery(name = "Event.findById", query = "SELECT e FROM Event e WHERE e.id = :id"),
    @NamedQuery(name = "Event.findByTitle", query = "SELECT e FROM Event e WHERE e.title = :title"),
    @NamedQuery(name = "Event.findByDate", query = "SELECT e FROM Event e WHERE e.date = :date"),
    @NamedQuery(name = "Event.findByStartingTime", query = "SELECT e FROM Event e WHERE e.startingTime = :startingTime"),
    @NamedQuery(name = "Event.findByEndingTime", query = "SELECT e FROM Event e WHERE e.endingTime = :endingTime"),
    @NamedQuery(name = "Event.findByLocation", query = "SELECT e FROM Event e WHERE e.location = :location"),
    @NamedQuery(name = "Event.findByCity", query = "SELECT e FROM Event e WHERE e.city = :city"),
    @NamedQuery(name = "Event.findByDescription", query = "SELECT e FROM Event e WHERE e.description = :description"),
    @NamedQuery(name = "Event.findByColor", query = "SELECT e FROM Event e WHERE e.color = :color"),
    @NamedQuery(name = "Event.findByPrivacy", query = "SELECT e FROM Event e WHERE e.privacy = :privacy")})
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;   
    @Basic(optional = false)
    @NotNull
    @Column(name = "startingTime")
    @Temporal(TemporalType.TIME)
    private Date startingTime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "endingTime")
    @Temporal(TemporalType.TIME)
    private Date endingTime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "city")
    private String city;
    @Size(max = 1000)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "color")
    private String color;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "privacy")
    private String privacy;
    @OneToMany(mappedBy = "eventID")
    private Collection<Notification> notificationCollection;
    @JoinColumn(name = "calendar", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Calendar calendar;
    @JoinColumn(name = "weatherID", referencedColumnName = "ID")
    @ManyToOne
    private Weather weatherID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event1")
    private Collection<Participant> participantCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventID")
    private Collection<Badconditions> badconditionsCollection;
    
    public Event() {
    }

    public Event(Integer id) {
        this.id = id;
    }

    public Event(Integer id, String title, Date date, Date startingTime, Date endingTime, String location, String city, String color, String privacy) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.startingTime = startingTime;
        this.endingTime = endingTime;
        this.location = location;
        this.city = city;
        this.color = color;
        this.privacy = privacy;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartingTime() {
        return startingTime;
    }

    public void setStartingTime(Date startingTime) {
        this.startingTime = startingTime;
    }

    public Date getEndingTime() {
        return endingTime;
    }

    public void setEndingTime(Date endingTime) {
        this.endingTime = endingTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }

    @XmlTransient
    public Collection<Notification> getNotificationCollection() {
        return notificationCollection;
    }

    public void setNotificationCollection(Collection<Notification> notificationCollection) {
        this.notificationCollection = notificationCollection;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }

    public Weather getWeatherID() {
        return weatherID;
    }

    public void setWeatherID(Weather weatherID) {
        this.weatherID = weatherID;
    }

    @XmlTransient
    public Collection<Participant> getParticipantCollection() {
        return participantCollection;
    }

    public void setParticipantCollection(Collection<Participant> participantCollection) {
        this.participantCollection = participantCollection;
    }

    @XmlTransient
    public Collection<Badconditions> getBadconditionsCollection() {
        return badconditionsCollection;
    }

    public void setBadconditionsCollection(Collection<Badconditions> badconditionsCollection) {
        this.badconditionsCollection = badconditionsCollection;
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
        if (!(object instanceof Event)) {
            return false;
        }
        Event other = (Event) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.bean.Event[ id=" + id + " ]";
    }
    
}
