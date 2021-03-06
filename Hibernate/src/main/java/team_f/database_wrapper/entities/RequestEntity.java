package team_f.database_wrapper.entities;

import team_f.database_wrapper.enums.RequestType;
import javax.persistence.*;

@Entity
@Table(name = "Request", schema = "sem4_team2", catalog = "")
@IdClass(RequestEntityPK.class)
public class RequestEntity {
    private int eventDuty;
    private int musician;
    private RequestType requestType;
    private String description;
    private EventDutyEntity eventDutyByEventDuty;
    private PersonEntity personByMusician;

    @Id
    @Column(name = "eventDuty", nullable = false)
    public int getEventDuty() {
        return eventDuty;
    }

    public void setEventDuty(int eventDuty) {
        this.eventDuty = eventDuty;
    }

    @Id
    @Column(name = "musician", nullable = false)
    public int getMusician() {
        return musician;
    }

    public void setMusician(int musician) {
        this.musician = musician;
    }

    @Basic
    @Column(name = "requestType", nullable = false)
    @Enumerated(EnumType.STRING)
    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    @Basic
    @Column(name = "description", nullable = true, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RequestEntity that = (RequestEntity) o;

        if (eventDuty != that.eventDuty) return false;
        if (musician != that.musician) return false;
        if (requestType != null ? !requestType.equals(that.requestType) : that.requestType != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventDuty;
        result = 31 * result + musician;
        result = 31 * result + (requestType != null ? requestType.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "eventDuty", referencedColumnName = "eventDutyID", nullable = false, insertable = false, updatable = false)
    public EventDutyEntity getEventDutyByEventDuty() {
        return eventDutyByEventDuty;
    }

    public void setEventDutyByEventDuty(EventDutyEntity eventDutyByEventDuty) {
        this.eventDutyByEventDuty = eventDutyByEventDuty;
    }

    @ManyToOne
    @JoinColumn(name = "musician", referencedColumnName = "personId", nullable = false, insertable = false, updatable = false)
    public PersonEntity getPersonByMusician() {
        return personByMusician;
    }

    public void setPersonByMusician(PersonEntity personByMusician) {
        this.personByMusician = personByMusician;
    }
}
