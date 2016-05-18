package de.helfenkannjeder.come2help.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@MappedSuperclass
public class AbstractAuditable {

    @Temporal(TIMESTAMP)
    @Basic(optional = false)
    @JsonIgnore
    private Date created;

    @Temporal(TIMESTAMP)
    @Basic(optional = false)
    @JsonIgnore
    private Date updated;

    @PrePersist
    protected void prePersist() {
        created = new Date();
        updated = new Date();
    }

    @PreUpdate
    protected void preUpdate() {
        updated = new Date();
    }

    public Date getCreated() {
        return created == null ? null : (Date) created.clone();
    }

    public void setCreated(Date created) {
        this.created = created == null ? null : (Date) created.clone();
    }

    public Date getUpdated() {
        return updated == null ? null : (Date) updated.clone();
    }

    public void setUpdated(Date updated) {
        this.updated = updated == null ? null : (Date) updated.clone();
    }

    @Override
    public String toString() {
        return "AbstractAuditable [created=" + created + ", updated=" + updated + "]";
    }
}
