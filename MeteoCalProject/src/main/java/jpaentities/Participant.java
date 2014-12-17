/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpaentities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
 * @author Walter
 */
@Entity
@Table(name = "participant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Participant.findAll", query = "SELECT p FROM Participant p"),
    @NamedQuery(name = "Participant.findByUser", query = "SELECT p FROM Participant p WHERE p.participantPK.user = :user"),
    @NamedQuery(name = "Participant.findByEvent", query = "SELECT p FROM Participant p WHERE p.participantPK.event = :event"),
    @NamedQuery(name = "Participant.findByOrganiser", query = "SELECT p FROM Participant p WHERE p.organiser = :organiser"),
    @NamedQuery(name = "Participant.findByParticipant", query = "SELECT p FROM Participant p WHERE p.participant = :participant")})
public class Participant implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ParticipantPK participantPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "organiser")
    private String organiser;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "participant")
    private String participant;
    @JoinColumn(name = "user", referencedColumnName = "username", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user1;
    @JoinColumn(name = "event", referencedColumnName = "ID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Event event1;

    public Participant() {
    }

    public Participant(ParticipantPK participantPK) {
        this.participantPK = participantPK;
    }

    public Participant(ParticipantPK participantPK, String organiser, String participant) {
        this.participantPK = participantPK;
        this.organiser = organiser;
        this.participant = participant;
    }

    public Participant(String user, int event) {
        this.participantPK = new ParticipantPK(user, event);
    }

    public ParticipantPK getParticipantPK() {
        return participantPK;
    }

    public void setParticipantPK(ParticipantPK participantPK) {
        this.participantPK = participantPK;
    }

    public String getOrganiser() {
        return organiser;
    }

    public void setOrganiser(String organiser) {
        this.organiser = organiser;
    }

    public String getParticipant() {
        return participant;
    }

    public void setParticipant(String participant) {
        this.participant = participant;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public Event getEvent1() {
        return event1;
    }

    public void setEvent1(Event event1) {
        this.event1 = event1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (participantPK != null ? participantPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Participant)) {
            return false;
        }
        Participant other = (Participant) object;
        if ((this.participantPK == null && other.participantPK != null) || (this.participantPK != null && !this.participantPK.equals(other.participantPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Participant[ participantPK=" + participantPK + " ]";
    }
    
}
