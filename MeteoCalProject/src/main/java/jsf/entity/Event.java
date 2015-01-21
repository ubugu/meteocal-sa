/*
 * Entity class of the Event
 */
package jsf.entity;
 
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author claudio
 */
@Entity
@Table(name = "event")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Event.findDateTimeInTheMiddle", query="SELECT e FROM Event e LEFT JOIN Participant p ON E.id = p.participantPK.event WHERE (((e.date = :startSqlDate) AND (( (e.startingTime <= :startSqlTime) AND (e.endingTime >= :startSqlTime) ) OR ( (e.startingTime <= :endSqlTime) AND (e.endingTime >= :endSqlTime) ) OR ( (e.startingTime >= :startSqlTime) AND (e.endingTime <= :endSqlTime) ) OR ( (e.startingTime <= :startSqlTime) AND (e.endingTime >= :endSqlTime) ))) AND ( (e.calendar.id = :calendarId) OR ( (p.participantPK.user = :username) AND (p.participantPK.event = e.id) AND (p.participant = 'YES') ) ))"),
    @NamedQuery(name = "Event.findAll", query = "SELECT e FROM Event e"),
    @NamedQuery(name = "Event.findNullWeather", query = "SELECT E FROM Event e WHERE e.city IS NOT NULL AND e.weatherID IS NULL"),
    @NamedQuery(name = "Event.findByCalendar", query = "SELECT e FROM Event e WHERE e.calendar = :calendar"),
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull(message="Title required")
    @Size(min = 1, max = 40)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @NotNull(message="Date required")
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull(message="Starting Time required")
    @Column(name = "startingTime")
    @Temporal(TemporalType.TIME)
    private Date startingTime;
    @Basic(optional = false)
    @NotNull(message="Ending Time required")
    @Column(name = "endingTime")
    @Temporal(TemporalType.TIME)
    private Date endingTime;
    @Basic(optional = false)
    @NotNull(message="Location required")
    @Size(min = 1, max = 40)
    @Column(name = "location")
    private String location;
    @Basic(optional = false)
    @Size(max = 40)
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
    private List<Notification> notificationList;
    @JoinColumn(name = "calendar", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Calendar calendar;
    @JoinColumn(name = "weatherID", referencedColumnName = "ID")
    @ManyToOne
    private Weather weatherID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event1")
    private List<Participant> participantList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventID")
    private List<Badconditions> badconditionsList;

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
    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
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
    public List<Participant> getParticipantList() {
        return participantList;
    }

    public void setParticipantList(List<Participant> participantList) {
        this.participantList = participantList;
    }

    @XmlTransient
    public List<Badconditions> getBadconditionsList() {
        return badconditionsList;
    }

    public void setBadconditionsList(List<Badconditions> badconditionsList) {
        this.badconditionsList = badconditionsList;
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
        return "jsf.entity.Event[ id=" + id + " ]";
    }
    
}
