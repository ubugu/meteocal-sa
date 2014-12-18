/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity.bean;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Walter
 */
@Embeddable
public class ParticipantPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "user")
    private String user;
    @Basic(optional = false)
    @NotNull
    @Column(name = "event")
    private int event;

    public ParticipantPK() {
    }

    public ParticipantPK(String user, int event) {
        this.user = user;
        this.event = event;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (user != null ? user.hashCode() : 0);
        hash += (int) event;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ParticipantPK)) {
            return false;
        }
        ParticipantPK other = (ParticipantPK) object;
        if ((this.user == null && other.user != null) || (this.user != null && !this.user.equals(other.user))) {
            return false;
        }
        if (this.event != other.event) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.bean.ParticipantPK[ user=" + user + ", event=" + event + " ]";
    }
    
}
