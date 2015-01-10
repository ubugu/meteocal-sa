/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author claudio
 */
@Entity
@Table(name = "badconditions")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Badconditions.findAll", query = "SELECT b FROM Badconditions b"),
    @NamedQuery(name = "Badconditions.findById", query = "SELECT b FROM Badconditions b WHERE b.id = :id"),
    @NamedQuery(name = "Badconditions.findByLayer", query = "SELECT b FROM Badconditions b WHERE b.layer = :layer"),
    @NamedQuery(name = "Badconditions.findByPrecipitations", query = "SELECT b FROM Badconditions b WHERE b.precipitations = :precipitations"),
    @NamedQuery(name = "Badconditions.findByEvent", query = "SELECT b FROM Badconditions b WHERE b.eventID = :event"),
    @NamedQuery(name = "Badconditions.findByTemperature", query = "SELECT b FROM Badconditions b WHERE b.temperature = :temperature")})
public class Badconditions implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "layer")
    private String layer;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precipitations")
    private Float precipitations;
    @Column(name = "temperature")
    private Float temperature;
    @JoinColumn(name = "eventID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Event eventID;

    public Badconditions() {
    }

    public Badconditions(Integer id) {
        this.id = id;
    }

    public Badconditions(Integer id, String layer) {
        this.id = id;
        this.layer = layer;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLayer() {
        return layer;
    }

    public void setLayer(String layer) {
        this.layer = layer;
    }

    public Float getPrecipitations() {
        return precipitations;
    }

    public void setPrecipitations(Float precipitations) {
        this.precipitations = precipitations;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Event getEventID() {
        return eventID;
    }

    public void setEventID(Event eventID) {
        this.eventID = eventID;
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
        if (!(object instanceof Badconditions)) {
            return false;
        }
        Badconditions other = (Badconditions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jsf.entity.Badconditions[ id=" + id + " ]";
    }
    
}
